package org.touchinghand.admin.registration.service;

import org.touchinghand.admin.dto.UserDTO;
import org.touchinghand.admin.model.Admin;
import org.touchinghand.exception.UserExistException;


public interface IRegistrationService {

  Admin register(UserDTO user) throws UserExistException;

}
