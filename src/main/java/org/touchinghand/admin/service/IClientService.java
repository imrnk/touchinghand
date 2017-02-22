package org.touchinghand.admin.service;

import java.time.LocalDate;
import java.util.List;

import org.touchinghand.client.dto.ClientDTO;
import org.touchinghand.exception.ClientCannotBeCreatedException;

public interface IClientService {

	public ClientDTO create(ClientDTO client) throws ClientCannotBeCreatedException;
	
	public List<ClientDTO> findAllClients();
	
	public List<ClientDTO> findAllActiveClients(LocalDate queryDate);
	
	public List<ClientDTO> findAllActiveClients();
	
	public ClientDTO findClientByClientId(int clientId);
	
	public ClientDTO findClientByEmail(String email);
	
	public ClientDTO findClientByPhoneNumber(String phoneNumber);
	
	public List<ClientDTO> findClientByName(String name);
	
	
}
