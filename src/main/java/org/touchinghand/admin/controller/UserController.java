package org.touchinghand.admin.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.touchinghand.admin.model.Admin;

@RestController
@RequestMapping("/")
public class UserController {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/current-user", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> login(Principal principal) {
		logger.info("********** principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
