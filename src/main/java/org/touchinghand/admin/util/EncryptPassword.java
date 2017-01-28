package org.touchinghand.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class EncryptPassword {

  /**
   * Generate a SHA256 Hashed Password
   * @param plainPass
   * @return
   */
  public final static String getSHA256EncryptedPass(String plainPass, byte[] salt){
      return encryptSHA256(plainPass, salt);
  }
  
  public static boolean matchEncryptedWithPassedPassword(String fetchedPassword,  String passedPassword, byte[] salt){
    if(encryptSHA256(passedPassword, salt).equals(fetchedPassword))
      return true;
    return false;
  }
  
/*  public static void main(String[] args){
    String encryptedPass = EncryptPassword.getSHA256EncryptedPass("Imran");
    System.out.println(encryptedPass);
  }
*/  
  
  private static String encryptSHA256(String plainPass, byte[] salt){
    
    String encryptedPass = null;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] bytes = md.digest(plainPass.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        encryptedPass = sb.toString();
    } 
    catch (NoSuchAlgorithmException e) 
    {
        e.printStackTrace();
    }
    return encryptedPass;
  }
  
  public static byte[] getSalt() throws NoSuchAlgorithmException{
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt;
  }
}
