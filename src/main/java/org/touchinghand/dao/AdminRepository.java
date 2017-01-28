package org.touchinghand.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.touchinghand.admin.model.Admin;
import org.touchinghand.exception.UserExistException;

@Repository
public class AdminRepository {
  private final String ADMIN_AUTHORITY = "ROLE_ADMIN";
  private final static String INSERT_USER = "INSERT INTO ADMIN (USERNAME, PASSWORD, FULL_NAME) VALUES (:userName, :password, :fullName)";
  private final static String INSERT_AUTHORITY = "INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES(:userName, :ADMIN_AUTHORITY)";
  private final static String FIND_USER_BY_USERNAME = "SELECT AD.USERNAME, AD.PASSWORD, AD.FULL_NAME, AUTH.AUTHORITY"
                                                      + " FROM ADMIN AD JOIN AUTHORITIES AUTH"
                                                      + " WHERE AD.USERNAME = AUTH.USERNAME"
                                                      + " AND AD.USERNAME = ? ";  
  private final static String DOES_USER_EXIST_BY_USERNAME = "SELECT 1 FROM ADMIN AD WHERE AD.USERNAME = ?";
  private JdbcTemplate jdbc;

  @Autowired
  public AdminRepository(JdbcTemplate jdbc){
    this.jdbc = jdbc;
  }

  /**
   * Save the admin user
   * @param admin
   * @return
   */
  public Admin save(Admin admin) throws UserExistException{
    
	if(doesUserExist(admin.getUserName())) throw new UserExistException(null); 
	  
    int insertCount = jdbc.update(INSERT_USER, admin.getUserName(), admin.getPassword(), admin.getFullName());
    if(insertCount > 0){
      jdbc.update(INSERT_AUTHORITY, admin.getUserName(), ADMIN_AUTHORITY);
    }
    return findByUserName(admin.getUsername());
  }
  
  /**
   * Find if username already exists
   * @param userName
   * @return
   */
  public boolean doesUserExist(final String userName){
	  return jdbc.queryForObject(DOES_USER_EXIST_BY_USERNAME, Integer.class, userName) > 0 ? true : false;
  }
  
  /**
   * Find user by username
   * @param userName
   * @return
   */
  public Admin findByUserName(final String userName){
    
    return jdbc.query(FIND_USER_BY_USERNAME, ps -> ps.setString(1, userName) , this::extract);
  }
  
  /**
   * extract the resultset
   * @param rs
   * @return
   * @throws SQLException
   */
  private Admin extract(ResultSet rs) throws SQLException{
    Map<String, Admin> adminMap = new HashMap<>();
    Admin admin = null;
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    while(rs.next()){
      String userName = rs.getString("USERNAME");
      admin = adminMap.get(userName);
      
      String auth = rs.getString("AUTHORITY");
      GrantedAuthority simpleGrantedAuth = new SimpleGrantedAuthority(auth);
      authorities.add(simpleGrantedAuth);
      
      if(admin == null){
        admin = new Admin(userName, rs.getString("PASSWORD"), rs.getString("FULLNAME"), authorities);
      } else {
        admin.addRole(simpleGrantedAuth);
      }
    }
    return admin;
  }
}
