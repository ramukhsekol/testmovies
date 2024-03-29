package com.movies.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class Test {

	 public static void main(String args[]) throws SocketException {
	        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
	        for (NetworkInterface netint : Collections.list(nets))
	            displayInterfaceInformation(netint);
	    }

	    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
	        System.out.printf("Display name: %s\n", netint.getDisplayName());
	        System.out.printf("Name: %s\n", netint.getName());
	        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
	        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
	        	System.out.printf("InetAddress: %s\n", inetAddress);
	        }
	        System.out.printf("\n");
	     }

}
