package com.djdch.dev.serialtoarduinoled.serial;

import java.io.ByteArrayOutputStream;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialLink {

    private String portName;
    private SerialPort serialPort;
    private ByteArrayOutputStream buffer;

    private boolean connected;

    public SerialLink() {
        connected = false;
        portName = null;
        serialPort = null;

        buffer = new ByteArrayOutputStream();
    }

    public void connect() {
        if (portName == null) {
            throw new IllegalArgumentException("PortName is null.");
        }

        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//            serialPort.writeBytes(new byte[] {-128, 0, 0});

            connected = true;
        } catch (SerialPortException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void disconnect() {
        if (serialPort != null) {
            try {
                serialPort.writeBytes(new byte[] {0, 0, 0});
                serialPort.closePort();

                connected = false;
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(int val) {
        buffer.write(val);
    }

    public void flush() {
        try {
            serialPort.writeBytes(buffer.toByteArray());
            buffer.reset();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
