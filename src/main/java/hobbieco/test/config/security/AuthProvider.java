package hobbieco.test.config.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="authUserDetailsService")
	private AuthUserDetailsService authUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		throw new UsernameNotFoundException("TEST LOGIN FAILED");
		
//		UsernamePasswordAuthenticationToken result = null;
//
//		String userName = request.getParameter("username");
//		String password = request.getParameter("password");
//		log.debug("request - user name : {} / passwrod : {}",userName,password);
//		Object lt = request.getAttribute("loginType");
//		if (lt != null) {
//			log.debug("login type : {}",lt.toString());
//		} else {
//			log.debug("login type is null");
//		}
//		log.debug("authentication - user name : {} / passwrod : {}",authentication.getPrincipal(),authentication.getCredentials());
//
//		if (StringUtils.isNotBlank(userName)) {
//			
//			Set<GrantedAuthority> authList = new HashSet<>();
//			
//			log.debug("role of {} is ROLE_MEMBER",userName);
//			authList.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
//			log.debug("authList : {}",authList.toString());
//
//			if (authList.size() > 0) {
//				result = new UsernamePasswordAuthenticationToken(userName, password, authList);
//			}
//		}
//		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
