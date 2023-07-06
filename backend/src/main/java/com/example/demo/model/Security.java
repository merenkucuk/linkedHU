package com.example.demo.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Security {
	
	
    public static String HashPassword(String password, String salt) {
    	String generatedPassword = "";
	    
    	try {
		
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Add password bytes to digest
            md.update(salt.getBytes());
            
            // Get the hash's bytes
            byte[] bytes = md.digest(password.getBytes());
            
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            
            // Get complete hashed password in hex format
            generatedPassword = sb.toString()+salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
	}
    
    public static String HashPassword(String password) throws NoSuchProviderException {
    	String generatedPassword = "";
	    
    	try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
		
            String salt=SaltCreate();
		
            // Add password bytes to digest
            md.update(salt.getBytes());
            
            // Get the hash's bytes
            byte[] bytes = md.digest(password.getBytes());
            
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            
            // Get complete hashed password in hex format
            generatedPassword = sb.toString()+salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
	}
	public static String SaltCreate() throws NoSuchAlgorithmException, NoSuchProviderException
    {
    	SecureRandom sr = new SecureRandom();
		
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
		
        return salt.toString();
    }
	public static boolean CheckPassword(String password, String dbPassword) {
		String salt = dbPassword.substring(64); 
		
		if (HashPassword(password,salt).equals(dbPassword)) {
			return true;
		}
		return false;					
	}
		
}
