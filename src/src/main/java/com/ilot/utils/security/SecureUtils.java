package com.ilot.utils.security;


import com.ilot.utils.CacheUtils;
import com.ilot.utils.Utilities;
import com.ilot.utils.dto.UserDto;
import com.ilot.utils.opt.GeneratorAes;
import lombok.extern.java.Log;

import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;

@Log
public class SecureUtils {

	public static String ExtractDataFromRsa(String data) {
		String decryptedRSA = "";
		try {
			decryptedRSA = ManageRsa.decryptRsa(data);
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return decryptedRSA;
	}

	public static String ExtractDataFromAes(String data) {
		return ExtractDataFromAes(data, ParamKey.KEY_AES);
	}
		
	public static String ExtractDataFromAes(String data, String keyAes) {
		String decryptedAes = "";
		try {		
			decryptedAes =  ManageAes.decryptAes(data, keyAes);
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return decryptedAes;
	}
	
	public String EncryptResponse(String data) {
		return EncryptResponse(data, ParamKey.KEY_AES);
	}

	public static String EncryptResponse(String responseValue, String keyAes) {
		String data = "";
		try {
		
			data = "{\"item\":\"" + ManageAes.encryptAes(responseValue, keyAes) + "\"}";	
			
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return data;
	}
	public static String EncryptResponseGeneratorAes(String responseValue, String keyAes) {
		String data = "";
		try {
			
			data = "{\"item\":\"" + GeneratorAes.encrypt(responseValue, keyAes) + "\"}";
			
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return data;
	}
	public static String ExtractDataFromGeneratorAes(String data, String keyAes) {
		String decryptedAes = "";
		try {		
			decryptedAes =  GeneratorAes.decrypt(data, keyAes);
		} catch (Exception e) {
			log.warning(e.getMessage());
		}
		return decryptedAes;
	}

	/*public static Map<String, Object>  getUserSession(String sessionUser, CacheUtils redisCacheUtils) {
		return redisCacheUtils.getExistingUser(RedisConstant.SESSION_ALIAS + sessionUser);
	}*/
	
	public static UserDto getUserByUserSession(String sessionUser, Map<String, Object> existingUserSession, CacheUtils redisCacheUtils) {
		UserDto user = new UserDto();
		for (Entry<String, Object> existUser : existingUserSession.entrySet()) {
			if(Utilities.notBlank(existUser.getKey()) && existUser.getKey().equalsIgnoreCase("id")) {
				user.setId(Integer.valueOf(existUser.getValue().toString()));	
			}
			if(Utilities.notBlank(existUser.getKey()) && existUser.getKey().equalsIgnoreCase("key")) {
				user.setKey(existUser.getValue().toString());						 
			}
			if(Utilities.notBlank(existUser.getKey()) && existUser.getKey().equalsIgnoreCase("token")) {
				user.setToken(existUser.getValue().toString());						 
			}
			
			if(Utilities.notBlank(existUser.getKey()) && existUser.getKey().equalsIgnoreCase("dateExpiration")) {
				user.setDateExpiration(existUser.getValue().toString());				
			}
		}
		
		try {
			if(Utilities.isNotBlank(user.getDateExpiration()) && Utilities.dateDebutPlusGrandQueDateFin(Utilities.getCurrentLongDateString(),user.getDateExpiration())) {
				user = null;
			}else {
				Utilities.saveUserSession(user,redisCacheUtils);
			}	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
