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

    public void connect() throws SerialPortException {
        if (portName == null) {
            throw new IllegalArgumentException("PortName is null.");
        }

        serialPort = new SerialPort(portName);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//        serialPort.writeBytes(new byte[] {-128, 0, 0});

        connected = true;
    }

    public void disconnect() throws SerialPortException {
        if (serialPort != null && connected) {
            try {
                serialPort.writeBytes(new byte[] {0, 0, 0});
                serialPort.closePort();
            } finally {
                serialPort = null;
                connected = false;
            }
        }
    }

    public void write(int val) {
        buffer.write(val);
    }

    public void flush() throws SerialPortException {
            serialPort.writeBytes(buffer.toByteArray());
            buffer.reset();
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
