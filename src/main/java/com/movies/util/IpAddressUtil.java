package com.movies.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAddressUtil {

	public static String getPublicIpAddress() throws UnknownHostException {
        String systemipaddress = ""; 
        InetAddress localhost = InetAddress.getLocalHost(); 
        System.out.println("System IP Address : " + 
                      (localhost.getHostAddress()).trim()); 
        systemipaddress = (localhost.getHostAddress()).trim(); 
        
		/*
		 * try { URL url_name = new URL("http://bot.whatismyipaddress.com");
		 * BufferedReader sc = new BufferedReader(new
		 * InputStreamReader(url_name.openStream())); systemipaddress =
		 * sc.readLine().trim(); } catch (Exception e) { systemipaddress =
		 * "Cannot Execute Properly"; } System.out.println(systemipaddress);
		 */
        return systemipaddress;
	}

}
