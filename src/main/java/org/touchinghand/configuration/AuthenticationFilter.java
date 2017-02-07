package org.touchinghand.configuration;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.touchinghand.admin.dto.UserDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean postOnly;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("***************");
		
		logger.info("***************");
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			authRequest = this.getUserNamePasswordAuthenticationToken(request);
		} catch (IOException ex) {
			LoggerFactory.getLogger(this.getClass()).error(ex.getMessage());
		}

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(HttpServletRequest request)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;
		String content = "";
		UserDTO userDTO = null;

		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}
			content = sb.toString();
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				userDTO = objectMapper.readValue(content, UserDTO.class);
			} catch (Throwable t) {
				throw new IOException(t.getMessage(), t);
			}
		} catch (IOException ex) {

			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword());

	}
}
