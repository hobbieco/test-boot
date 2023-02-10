package hobbieco.test.config.security;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import hobbieco.test.config.security.mapper.SecurityMapperAppData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthUserDetailsService implements UserDetailsService {
	
	@Resource(name="securityMapperAppData")
	private SecurityMapperAppData data;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Map<String,String> user = data.selectUser();
//		if (user == null) {
//			throw new UsernameNotFoundException(username);
//		}
//		log.debug(user.toString());
//		
//		return new UserInfo(user);
		
		throw new UsernameNotFoundException("TEST LOGIN FAILED");
	}

}
