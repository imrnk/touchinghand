package org.touchinghand.admin.dto;

public class UserDTO {

  private String userName;
  private String password;
  private String matchingPassword;
  private String fullName;
  
  
  public UserDTO(){
	  
  }
  
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getMatchingPassword() {
    return matchingPassword;
  }
  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  
  
}
