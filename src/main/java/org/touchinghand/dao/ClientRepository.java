package org.touchinghand.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.touchinghand.client.dto.ClientDTO;
import org.touchinghand.exception.ClientCannotBeCreatedException;

@Repository
public class ClientRepository {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String INSERT_CLIENT = "INSERT INTO CLIENT (name, gender, age, maritalstatus, profession, "
			+ "education, address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate, created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	private final static String FIND_CLIENT_BY_CLIENTID = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where client_id = ? ";
	private final static String FIND_CLIENT_BY_NAME = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where name is like %name%";
	private final static String FIND_CLIENT_BY_PHONENUMBER = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where phonenumber1 = ?";
	private final static String FIND_CLIENT_BY_EMAIL = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where email = ?";

	private final static String FIND_ALL_ACTIVE_CLIENTS = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where status = Y";
	
	private final static String FIND_ALL_ACTIVE_CLIENTS_FOLLOWUP_BEFORE_QUERYDATE = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client where status = Y and followupdate <= ? ";
	
	private final static String FIND_ALL_CLIENTS = "SELECT client_id, name, gender, age, maritalstatus, profession, education, "
			+ "	address, city, state, pin, country, phonenumber1, phonenumber2, "
			+ "email, reference, status, followupdate " + "from client";	
	
	private JdbcTemplate jdbc;

	@Autowired
	public ClientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * Save the client and return the newly created client
	 * @param client
	 * @return
	 * @throws ClientCannotBeCreatedException
	 */
	public ClientDTO save(ClientDTO client) throws ClientCannotBeCreatedException {

		try {
			int insertCount = jdbc.update(INSERT_CLIENT.toLowerCase(), client.getName(), client.getGender(),
					client.getAge(), client.getMaritalStat(), client.getProfession(), client.getEducation(),
					client.getAddress(), client.getCity(), client.getState(), client.getPinCode(), client.getCountry(),
					client.getPhone(), client.getPhone2());
			if (insertCount < 1)
				throw new ClientCannotBeCreatedException("Client Cannot Be Created");
			return findClientByEmail(client.getEmail());

		} catch (DataAccessException e) {
			throw new ClientCannotBeCreatedException(e);
		}
	}
	
	/**
	 * Find All Clients in the table
	 * @return
	 */
	public List<ClientDTO> findAllClients(){
		return jdbc.query(FIND_ALL_CLIENTS, this::extract);
	}
	
	
	/**
	 * Find all active clients within the given date
	 * @param queryDate
	 * @return
	 */
	public List<ClientDTO> findAllActiveClients(Date queryDate){
		return jdbc.query(FIND_ALL_ACTIVE_CLIENTS_FOLLOWUP_BEFORE_QUERYDATE, ps -> ps.setDate(1, queryDate), this::extract);
	}
	
	/**
	 * Find all active clients
	 * @return
	 */
	public List<ClientDTO> findAllActiveClients(){
		return jdbc.query(FIND_ALL_ACTIVE_CLIENTS, this::extract);
	}
	
	/**
	 * This will return a single client as clientId is unique for a client
	 * @param clientId
	 * @return
	 */
	public ClientDTO findClientByClientId(int clientId){
		return jdbc.query(FIND_CLIENT_BY_CLIENTID, ps -> ps.setInt(1, clientId), this::extract).get(0);
	}
	/**
	 * This will always return a single client as email is unique for a client
	 * @param email
	 * @return
	 */
	public ClientDTO findClientByEmail(String email){
		return jdbc.query(FIND_CLIENT_BY_EMAIL, ps -> ps.setString(1, email), this::extract).get(0);
	}
	
	/**
	 * This will always return a single client as phonenumber is unique for a client
	 * @param phoneNumber
	 * @return
	 */
	public ClientDTO findClientByPhoneNumber(String phoneNumber){
		return jdbc.query(FIND_CLIENT_BY_PHONENUMBER, ps -> ps.setString(1, phoneNumber), this::extract).get(0);
	}

	/**
	 * This could return multiple clients
	 * @param name
	 * @return
	 */
	public List<ClientDTO> findClientByName(String name) {

		return jdbc.query(FIND_CLIENT_BY_NAME.toLowerCase(), ps -> ps.setString(1, name), this::extract);
	}

	private List<ClientDTO> extract(ResultSet rs) throws SQLException {
		
		List<ClientDTO> matchedClients = new ArrayList<ClientDTO>();
		
		while (rs.next()) {
			ClientDTO client = null;
			int clientId = rs.getInt("client_id");
			String name = rs.getString("name");
			String gender = rs.getString("gender");
			int age = rs.getInt("age");
			String maritalStat = rs.getString("maritalstatus");
			String profession = rs.getString("profession");
			String education = rs.getString("education");
			String address = rs.getString("address");
			String city = rs.getString("city");
			String state = rs.getString("state");
			int pin = rs.getInt("pin");
			String country = rs.getString("country");
			String phonenumber1 = rs.getString("phonenumber1");
			String phonenumber2 = rs.getString("phonenumber2");
			String email = rs.getString("email");
			String reference = rs.getString("reference");
			String status = rs.getString("status");
			Date followUpDate = rs.getDate("followupdate");
			client = new ClientDTO.ClientBuilder(clientId, name, phonenumber1).gender(gender).age(age)
					.maritalStat(maritalStat).profession(profession).education(education).address(address).city(city)
					.state(state).pinCode(pin).country(country).phone2(phonenumber2).email(email)
					.referenceCounsellor(reference).active(status == "Y").followUpDate(followUpDate).build();
			
			matchedClients.add(client);
		}
		return matchedClients;
	}

}
