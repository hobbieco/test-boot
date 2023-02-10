package hobbieco.test.config.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Resource(name="authProvider")
	private AuthProvider authProvider;
	
	@Resource(name="authFailure")
	private AuthFailure authFailure;
	
	@Resource(name="authEntryPoint")
	private AuthEntryPoint authEntryPoint;
	
	@Resource(name="loginSuccessHandler")
	private LoginSuccessHandler loginSuccessHandler;
	
	@Resource(name="logoutHandler")
	private LogoutHandler logoutHandler;
	
	/**
	 * web security ignore
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/static/**","/css/**","/js/**","/image/**");
	}
	
	
	/**
	 * web security configuration
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		
		http.headers().cacheControl().disable();
		
		http.authenticationProvider(authProvider);
		
		http.formLogin().loginPage("/login").defaultSuccessUrl("/", true).loginProcessingUrl("/lgn")
			.usernameParameter("userId").passwordParameter("userPwd")
			.successHandler(loginSuccessHandler).failureHandler(authFailure).permitAll();
		http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
		
		//http.authorizeRequests().antMatchers("/api/**").access("hasRole('MEMBER') or hasRole('ADMIN')").and().httpBasic().authenticationEntryPoint(authEntryPoint);
		
		http.authorizeRequests().antMatchers("/test/**").permitAll();
		http.authorizeRequests().antMatchers("/actuator/**").permitAll();
		http.authorizeRequests().antMatchers("/**").access("@authChecker.check(request,authentication)").and().httpBasic().authenticationEntryPoint(authEntryPoint);
		
		return http.build();
	}
	
	/**
	 * CORS
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
}
