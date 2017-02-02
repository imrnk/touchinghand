package org.touchinghand.admin.registration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.touchinghand.dao.AdminRepository;

@Service("userDetailsService")
public class LoginService implements UserDetailsService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private AdminRepository adminRepository;

	@Autowired
	public LoginService(AdminRepository adminRepo) {
		this.adminRepository = adminRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		return adminRepository.findByUserName(userName);
	}

	/*
	 * public Admin login(String userName, String password){ Admin fetchedUser =
	 * adminRepository.findByUserNameAndPassword(userName, password);
	 * if(validateUser(fetchedUser, password)) return fetchedUser; return null;
	 * }
	 * 
	 * private boolean validateUser(Admin fetchedUser, String password){ return
	 * EncryptPassword.matchEncryptedWithPassedPassword(fetchedUser.getPassword(
	 * ), password, fetchedUser.getSalt()); }
	 */

}
