package com.spms.mvc;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Description: Test
 * Date:  2021-Nov-03
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Nov-03
 * Change Description:
 * Search Tag:
 *
 *
 *
 */
import java.util.UUID;
public class Test {
    public static void main(String args[]) throws ParseException {


        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
            byte[] macAddressBytes = networkInterface.getHardwareAddress();

            StringBuilder macAddress = new StringBuilder();
            for (int i = 0; i < macAddressBytes.length; i++) {
                macAddress.append(String.format("%02X%s", macAddressBytes[i], (i < macAddressBytes.length - 1) ? "-" : ""));
            }

            System.out.println("MAC Address: " + macAddress.toString());
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String deviceId = localHost.getHostName();
            System.out.println("Device ID: " + deviceId);
            UUID uniqueId = UUID.randomUUID();
//            8F7F5FEA-24BF-45D3-9DE4-2E191FAEE8D7
            System.out.println("Unique ID: " + uniqueId.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


//        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
//        numberFormat.setMaximumFractionDigits(2);
//
//        Double depreciationRate =  0.15;
//        Calendar cal = Calendar.getInstance();
//
//        cal.setTime(DateUtil.toDate("01-Jan-2022"));
//        int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
//        int totalNoOfDaysDif = numberOfDaysInMonth(2,2022);
//
//         System.out.println(numberFormat.format(((0.15 * depreciationRate) / numOfDays) * totalNoOfDaysDif));
    }



    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
