package org.touchinghand.admin.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.touchinghand.admin.dto.UserDTO;

@Controller
@RequestMapping("/")
public class LoginController {

  @RequestMapping(value={"/"}, method = RequestMethod.GET)
  public String showLogin(Model model){
	  System.out.println("********************");
	  System.out.println("********************");
	  System.out.println("********************");
	  System.out.println("********************");
	  System.out.println("********************");
	  System.out.println("********************");
    model.addAttribute("user", new UserDTO());
    return "/login";
  }

  /* @RequestMapping(value="/login", method = RequestMethod.POST)
  public String login(@ModelAttribute User user, Model model){
    System.out.println("******************* login() ****************");
    logger.warn("********** Inside Login **************");
    Admin fetchedUser = logRegService.login(user.getUserName(), user.getPassword());
    model.addAttribute("loggedInUserId", fetchedUser.getId());
    return "dashboard";
  }*/

}
