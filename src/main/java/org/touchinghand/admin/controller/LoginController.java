package org.touchinghand.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;

//@RestController
// @RequestMapping("/")
public class LoginController {
/*
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> login(@RequestBody UserDTO user, BindingResult result, WebRequest request) {
		logger.info("********** Inside login of LoginController **************");
		logger.info("********** principle: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		Admin authenticatedUser = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpStatus httpStatus = null;
		if (authenticatedUser == null) {
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<Admin>(authenticatedUser, httpStatus);
	}*/

}
