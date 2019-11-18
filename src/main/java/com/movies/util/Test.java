package com.movies.util;

import java.net.InetAddress;

public class Test {
    public static void main(String args[]) throws Exception {
    	InetAddress localhost = InetAddress.getLocalHost(); 
        System.out.println("System IP Address : " + 
                      (localhost.getHostAddress()).trim()); 
    }
}
