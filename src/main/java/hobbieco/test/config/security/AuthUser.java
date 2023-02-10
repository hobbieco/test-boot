package hobbieco.test.config.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthUser extends UsernamePasswordAuthenticationToken {
	
	private String userId;
	private String userNm;
	private String userTy;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
		super(username, password, authorities);
		
		this.userId = userInfo.getUserId();
		this.userNm = userInfo.getUserNm();
		this.userTy = userInfo.getUserTy();
		
		
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserNm() {
		return this.userNm;
	}
	
	public String getUserTy() {
		return this.userTy;
	}
}
