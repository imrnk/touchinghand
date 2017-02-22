package org.touchinghand.dao;

import static org.touchinghand.admin.util.Util.localDateTimeNow;
import static org.touchinghand.admin.util.Util.toLocalDateTime;
import static org.touchinghand.admin.util.Util.toTimeStamp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
	
	private final static String UPDATE_CLIENT = "UPDATE CLIENT SET name = ?, gender = ?, age = ?, maritalstatus = ?, profession = ?, "
			+ "education = ?, address = ?, city = ?, state = ?, pin = ?, country = ?, phonenumber1 = ?, phonenumber2 = ?, "
			+ "email = ?, reference = ?, status = ?, followupdate = ?, updated_on = ? where client_id = ?";
	
	
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
					client.getPhone(), client.getPhone2(), client.getEmail(), client.getReferenceCounsellor(), 
					client.isActive()? "Y" : "N", toTimeStamp(client.getFollowUpDate()), localDateTimeNow());
			if (insertCount < 1)
				throw new ClientCannotBeCreatedException("Client Cannot Be Created");
			return findClientByEmail(client.getEmail());

		} catch (DataAccessException e) {
			throw new ClientCannotBeCreatedException(e);
		}
	}
	
	/**
	 * Update the client table with new record. set updated_on column to current date
	 * @param client
	 * @return
	 * @throws ClientCannotBeCreatedException
	 */
	public ClientDTO update(ClientDTO client) throws ClientCannotBeCreatedException {

		try {
			int updateCount = jdbc.update(UPDATE_CLIENT.toLowerCase(), client.getName(), client.getGender(),
					client.getAge(), client.getMaritalStat(), client.getProfession(), client.getEducation(),
					client.getAddress(), client.getCity(), client.getState(), client.getPinCode(), client.getCountry(),
					client.getPhone(), client.getPhone2(), client.getEmail(), client.getReferenceCounsellor(), 
					client.isActive()? "Y" : "N", toTimeStamp(client.getFollowUpDate()), localDateTimeNow(), client.getClientId());
			if (updateCount < 1)
				throw new ClientCannotBeCreatedException("Client Cannot Be Updated");
			return findClientByClientId(client.getClientId());

		} catch (DataAccessException e) {
			throw new ClientCannotBeCreatedException(e);
		}
	}
	
	
	/**
	 * Find All Clients in the table
	 * @return
	 */
	public List<ClientDTO> findAllClients(){
		return jdbc.query(FIND_ALL_CLIENTS, this::mapRow);
	}
	
	
	/**
	 * Find all active clients within the given date
	 * @param queryDate
	 * @return
	 */
	public List<ClientDTO> findAllActiveClients(LocalDate queryDate){
		return jdbc.query(FIND_ALL_ACTIVE_CLIENTS_FOLLOWUP_BEFORE_QUERYDATE, ps -> ps.setDate(1, Date.valueOf(queryDate)), this::mapRow);
	}
	
	/**
	 * Find all active clients
	 * @return
	 */
	public List<ClientDTO> findAllActiveClients(){
		return jdbc.query(FIND_ALL_ACTIVE_CLIENTS, this::mapRow);
	}
	
	/**
	 * This will return a single client as clientId is unique for a client
	 * @param clientId
	 * @return
	 */
	public ClientDTO findClientByClientId(int clientId){
		List<ClientDTO> clients = jdbc.query(FIND_CLIENT_BY_CLIENTID, ps -> ps.setInt(1, clientId), this::mapRow);
		return clients == null || clients.size() < 1 ? null : clients.get(0);
	}
	/**
	 * This will always return a single client as email is unique for a client
	 * @param email
	 * @return
	 */
	public ClientDTO findClientByEmail(String email){
		List<ClientDTO> clients = jdbc.query(FIND_CLIENT_BY_EMAIL, ps -> ps.setString(1, email), this::mapRow);
		return clients == null || clients.size() < 1 ? null : clients.get(0);
	}
	
	/**
	 * This will always return a single client as phonenumber is unique for a client
	 * @param phoneNumber
	 * @return
	 */
	public ClientDTO findClientByPhoneNumber(String phoneNumber){
		List<ClientDTO> clients = jdbc.query(FIND_CLIENT_BY_PHONENUMBER, ps -> ps.setString(1, phoneNumber), this::mapRow);
		return clients == null || clients.size() < 1 ? null : clients.get(0); 
	}

	/**
	 * This could return multiple clients
	 * @param name
	 * @return
	 */
	public List<ClientDTO> findClientByName(String name) {

		return jdbc.query(FIND_CLIENT_BY_NAME.toLowerCase(), ps -> ps.setString(1, name), this::mapRow);
	}

	private ClientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
			Timestamp followUpDate = rs.getTimestamp("followupdate");
			client = new ClientDTO.ClientBuilder(clientId, name, phonenumber1).gender(gender).age(age)
					.maritalStat(maritalStat).profession(profession).education(education).address(address).city(city)
					.state(state).pinCode(pin).country(country).phone2(phonenumber2).email(email)
					.referenceCounsellor(reference).active(status == "Y").followUpDate(toLocalDateTime(followUpDate)).build();
		
		return client;
	}
	
}
