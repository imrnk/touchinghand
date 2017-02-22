package org.touchinghand.admin.service;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.touchinghand.client.dto.ClientDTO;
import org.touchinghand.dao.ClientRepository;
import org.touchinghand.exception.ClientCannotBeCreatedException;

public class ClientService implements IClientService {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	private ClientRepository clientRepository;

	@Autowired
	public ClientService(ClientRepository repo) {
		this.clientRepository = repo;
	}

	@Override
	public ClientDTO create(ClientDTO client) throws ClientCannotBeCreatedException {
		return this.clientRepository.save(client);
	}

	@Override
	public List<ClientDTO> findAllClients() {
		return this.clientRepository.findAllClients();
	}

	@Override
	public List<ClientDTO> findAllActiveClients(LocalDate queryDate) {
		return this.clientRepository.findAllActiveClients(queryDate);
	}

	@Override
	public List<ClientDTO> findAllActiveClients() {
		return this.clientRepository.findAllActiveClients();
	}

	@Override
	public ClientDTO findClientByClientId(int clientId) {
		return this.clientRepository.findClientByClientId(clientId);
	}

	@Override
	public ClientDTO findClientByEmail(String email) {
		return this.clientRepository.findClientByEmail(email);
	}

	@Override
	public ClientDTO findClientByPhoneNumber(String phoneNumber) {
		return this.clientRepository.findClientByPhoneNumber(phoneNumber);
	}

	@Override
	public List<ClientDTO> findClientByName(String name) {
		return this.clientRepository.findClientByName(name);
	}

}
