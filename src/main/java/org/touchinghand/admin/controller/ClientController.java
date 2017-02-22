package org.touchinghand.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.touchinghand.admin.service.IClientService;
import org.touchinghand.client.dto.ClientDTO;
import org.touchinghand.exception.ClientCannotBeCreatedException;

@Controller
@RequestMapping("/dashboard/client")
public class ClientController {

	private IClientService clientService;

	@Autowired
	public ClientController(IClientService service) {
		this.clientService = service;
	}

	/**
	 * 
	 * @param client
	 * @return ResponseEntity<ClientDTO>
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO client) {
		
		ClientDTO createdClient = null;
		try {
			createdClient = clientService.create(client);
		} catch (ClientCannotBeCreatedException e) {

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("message", e.getMessage());
			return new ResponseEntity<ClientDTO>(createdClient, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<ClientDTO>(client, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @return ResponseEntity<List<ClientDTO>>
	 */
	@RequestMapping(value = "/active-clients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClientDTO>> activeClients() {
		
		clientService.findAllActiveClients();
		return null;
	}

}
