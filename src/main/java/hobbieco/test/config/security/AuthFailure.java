package hobbieco.test.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthFailure extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String errorMsg = exception.getMessage();

		if (exception instanceof BadCredentialsException) {
			log.debug("BadCredentialsException");
			errorMsg = "BadCredentialsException";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			log.debug("InternalAuthenticationServiceException");
			errorMsg = "InternalAuthenticationServiceException";
		} else if (exception instanceof DisabledException) {
			log.debug("DisabledException");
			errorMsg = "DisabledException";
		} else if (exception instanceof CredentialsExpiredException) {
			log.debug("CredentialsExpiredException");
			errorMsg = "CredentialsExpiredException";
		} else if (exception instanceof UsernameNotFoundException) {
			log.debug("UsernameNotFoundException");
			errorMsg = "UsernameNotFoundException";
		} else if (exception instanceof AccountExpiredException) {
			log.debug("AccountExpiredException");
			errorMsg = "AccountExpiredException";
		} else if (exception instanceof LockedException) {
			log.debug("LockedException");
			errorMsg = "LockedException";
		} else if (exception instanceof ProviderNotFoundException) {
			log.debug("ProviderNotFoundException");
			errorMsg = "ProviderNotFoundException";
		}
		
		request.setAttribute("loginError", errorMsg);
		//request.getRequestDispatcher("/login").forward(request, response);
		
		response.sendRedirect("/login?typ");
		
		
		
		//response.setHeader(HttpHeaders.LOCATION, "/login");
		//response.setStatus(HttpStatus.FOUND.value());
		
	}

}
