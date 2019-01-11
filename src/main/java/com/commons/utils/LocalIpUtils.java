package com.commons.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 本机ip
 * @author DoubleLi
 * @time 2018年12月19日
 * 
 */
public class LocalIpUtils {

	public static String INTRANET_IP = getIntranetIp();
    public static String INTERNET_IP = getInternetIp();

    private LocalIpUtils(){}

    private static String getIntranetIp(){
        try{
        	try{
                Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;
                Enumeration<InetAddress> addrs;
                while (networks.hasMoreElements())
                {
                    addrs = networks.nextElement().getInetAddresses();
                    while (addrs.hasMoreElements())
                    {
                        ip = addrs.nextElement();
                        if (ip != null
                                && ip instanceof Inet4Address
                                && ip.isSiteLocalAddress()
                                && !ip.getHostAddress().equals(INTRANET_IP))
                        {
                            return ip.getHostAddress();
                        }
                    }
                }

                return INTRANET_IP;
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private static String getInternetIp(){
        try{
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements())
            {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(INTRANET_IP)
                            && !"127.0.0.1".equals(ip.getHostAddress()))
                    {
                        return ip.getHostAddress();
                    }
                }
            }

            return INTRANET_IP;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

	
	public static void main(String[] args) {
		System.out.println(LocalIpUtils.INTRANET_IP);
		System.out.println(LocalIpUtils.INTERNET_IP);
	}
}