package com.spms.mvc;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
      String doubleVal="2,443.07";
        System.out.println(doubleVal.replaceAll(",",""));
        System.out.println(doubleVal);

        System.out.println(Double.parseDouble(doubleVal.replaceAll(",","")));
    }
}
