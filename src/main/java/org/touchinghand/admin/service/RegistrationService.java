package org.touchinghand.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;
import org.touchinghand.admin.model.Authority;
import org.touchinghand.dao.AdminRepository;
import org.touchinghand.exception.UserExistException;

@Service
public class RegistrationService implements IRegistrationService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public RegistrationService(AdminRepository adminRepo) {
		this.adminRepository = adminRepo;
	}

	@Override
	public Admin register(UserDTO user) throws UserExistException {

		// first check if the user exist
		// TODO: UserExistException

		Admin admin = buildAdmin(user);

		// Call save method
		return adminRepository.save(admin);

	}

	private Admin buildAdmin(UserDTO user) {
		GrantedAuthority authority = new Authority(user.getUserName(), Authority.ADMIN_AUTHORITY);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		Admin admin = new Admin(user.getUserName(), passwordEncoder.encode(user.getPassword()), user.getFullName(),
				authorities);
		return admin;
	}

}
