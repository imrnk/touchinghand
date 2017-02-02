package org.touchinghand.admin.registration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;

@Controller
@RequestMapping("/")
public class LoginController {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("user", new UserDTO());
		return "/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> login(@RequestBody UserDTO user, Model model) {
		logger.info("********** Inside login of LoginController **************");

		Admin authenticatedUser = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpStatus httpStatus = null;
		if (authenticatedUser == null) {
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<Admin>(authenticatedUser, httpStatus);
	}
	

}
