package com.djdch.dev.serialtoarduinoled;

import com.djdch.dev.serialtoarduinoled.serial.SerialUtil;
import com.djdch.dev.serialtoarduinoled.swing.ApplicationFrame;

public class SerialToArduinoLED {

    /**
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        ApplicationFrame application = new ApplicationFrame();
        application.setVisible(true);

//        SerialUtil.listPorts();
    }
}
