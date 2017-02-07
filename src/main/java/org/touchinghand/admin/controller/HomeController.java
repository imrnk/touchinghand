package org.touchinghand.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.touchinghand.admin.dto.UserDTO;

/**
 * The MVC controller at the entry point of the application
 * @author devuser
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("user", new UserDTO());
		return "/login";
	}

}
