package hobbieco.test.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {
	
	private String username;
	private String password;
	private final Set<GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	
	private String userId;
	private String userNm;
	private String userTy;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserInfo(Map<String,String> userInfo) {
		this.username = userInfo.get("userId");
		this.password = "열쇠글";
		
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		
		this.authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		this.userId = userInfo.get("USER_ID");
		this.userNm = userInfo.get("USER_NM");
		this.userTy = userInfo.get("USER_TY");
		
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AuthUser) {
			return this.username.equals(((UserInfo) obj).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성 상태
		return this.enabled;
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
