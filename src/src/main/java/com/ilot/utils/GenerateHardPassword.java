package com.ilot.utils;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;
import org.passay.CharacterData;

@Component
public class GenerateHardPassword {
	public String generatePassayPassword() {
	    PasswordGenerator gen = new PasswordGenerator();
	    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(3);

	    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(3);

	    CharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(3);

	    CharacterData specialChars = new CharacterData() {
	        public String getErrorCode() {
	            return "";
	        }

	        public String getCharacters() {
	            return "!@$^*()_";
	        }
	    };
	    CharacterRule splCharRule = new CharacterRule(specialChars);
	    splCharRule.setNumberOfCharacters(3);

	    String password = gen.generatePassword(12, splCharRule, lowerCaseRule, 
	      upperCaseRule, digitRule);
	    return password;
	}
}
