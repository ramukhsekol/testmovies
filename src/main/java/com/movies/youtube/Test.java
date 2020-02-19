package com.movies.youtube;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Test {
	public static void main(String...strings) {
		Test test = new Test();
		String s = "John Doe; John Doe; John Doe; John Doe; John Doe; John Doe; John Doe; John Doe; John Doe; Peter Benjamin Parker; Mary Jane Watson-Parker; John Elvis Doe; John Evan Doe; Jane Doe; Peter Brian Parker";
		test.solution(s, "example");
	}
	public String solution(String S, String C) {
		List<String> emails = new ArrayList<String>();
		String[] names = S.split("; ");
		for (int i = 0; i < names.length; i++) {
			String[] person = names[i].split(" ");
			String firstName = person[0];
			String lastName = (person.length == 3) ? person[2] : person[1];
			
			String formattedLastName = lastName.replaceAll("-", "");
			if(formattedLastName.length()>8) {
				formattedLastName = formattedLastName.substring(0, 8);
			}
			String hostName =  (firstName + "." + formattedLastName).toLowerCase();
			List<String> duplicateemails = new ArrayList<String>();
				for(String demail : emails) {
					if(demail.contains(hostName)) {
						duplicateemails.add(demail);
					}
				}
			String email  = "";
			if(duplicateemails!=null && duplicateemails.size()>0) {
				String ddemail = duplicateemails.get(duplicateemails.size() - 1);
				 Pattern p = Pattern.compile( "[0-9]" );
				 Matcher m = p.matcher(ddemail);
				  boolean isFlag=  m.find();

				System.out.println(isFlag);
				if(!isFlag) {
					email = (firstName + "." + formattedLastName +"1" + "@" + C + ".com").toLowerCase();
				} else {
					String numberOnly= ddemail.replaceAll("[^0-9]", ""); 
					Integer number = Integer.parseInt(numberOnly) + 1;
					System.out.println(numberOnly);
					email = (firstName + "." + formattedLastName + number + "@" + C + ".com").toLowerCase();
				}
			} else {
				email = (firstName + "." + formattedLastName + "@" + C + ".com").toLowerCase();
			}
			emails.add(email);
		}
		System.out.println(emails);
		return emails.toString();
	}
}
