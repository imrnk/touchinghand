package org.touchinghand.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class Admin extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112310414567643618L;
	private long id;
	private String userName;
	private String password;
	private String fullName;

	private List<Authority> roles;

	// private List<GrantedAuthority> authorities = new
	// ArrayList<GrantedAuthority>();

	public Admin(String userName, String password, String fullName, List<GrantedAuthority> authorities) {
		super(userName, password, authorities);
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
		// this.authorities = authorities;
		buildRoleFromGrantedAuthorities(authorities);
	}

	// private void buildGrantedAuthoritiesFromRoles(List<Authority> roles){
	// authorities = roles.stream().map(r -> new
	// SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
	// }

	private void buildRoleFromGrantedAuthorities(List<GrantedAuthority> authorities) {
		roles = authorities.stream().map(ga -> new Authority(this.userName, ga.getAuthority()))
				.collect(Collectors.toList());
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public List<Authority> getRoles() {
		return roles;
	}

	public void setRoles(List<Authority> roles) {
		this.roles = roles;
	}

	public void addRole(GrantedAuthority role) {
		if (this.roles == null) {
			this.roles = new ArrayList<Authority>();
		}
		this.roles.add(new Authority(this.userName, role.getAuthority()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Admin [userName=" + userName + ", fullName=" + fullName + ", roles=" + roles + "]";
	}

}
