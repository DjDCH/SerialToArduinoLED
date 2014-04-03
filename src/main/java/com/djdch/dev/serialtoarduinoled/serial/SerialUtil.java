package com.djdch.dev.serialtoarduinoled.serial;

import jssc.SerialPortList;

public class SerialUtil {

    public static String[] getPorts() {
        return SerialPortList.getPortNames();
    }

    public static void listPorts() {
        String[] portNames = getPorts();
        for (int i = 0; i < portNames.length; i++) {
            System.out.println(portNames[i]);
        }
    }
}
