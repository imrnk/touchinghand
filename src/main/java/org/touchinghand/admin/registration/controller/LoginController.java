package org.touchinghand.admin.registration.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;
import org.touchinghand.admin.registration.service.LoginService;

@RestController
@RequestMapping("/")
public class LoginController {

	public Logger logger = Logger.getLogger(LoginController.class);

	private UserDetailsService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody UserDTO user, Model model) {
		logger.info("********** Inside Login **************");
		Admin fetchedUser = loginService.login(user.getUserName(), user.getPassword());
		model.addAttribute("loggedInUserId", fetchedUser.getId());
		return "dashboard";
	}

}
