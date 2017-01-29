package org.touchinghand.admin.registration.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;
import org.touchinghand.admin.registration.service.IRegistrationService;
import org.touchinghand.exception.UserExistException;

@Controller
@RequestMapping("/")
public class RegistrationController {

  public Logger logger = Logger.getLogger(RegistrationController.class);

  private IRegistrationService registrationService;

  @Autowired
  public RegistrationController(IRegistrationService service) {
    this.registrationService = service;
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String showRegistrationPage(Model model){
    model.addAttribute("newuser", new UserDTO());
    logger.debug("Coming to showRegistrationPage ****************************");
    return "register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@ModelAttribute("user") UserDTO user, BindingResult result,
      WebRequest request, Errors error, Model model) {
      
    //TODO: Handle Error Condition
	logger.debug("*************** in register() of " + logger.getClass());
	logger.debug("User DTO:   " + user.getUserName() + " " + user.getPassword());
    Admin registered = null;
	try {
		registered = registrationService.register(user);
	} catch (UserExistException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      if(registered != null){
        model.addAttribute("registered", registered);
        return "/dashboard";
      }
      return "/registration-failure";
  }

}
