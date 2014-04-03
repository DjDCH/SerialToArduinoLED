package com.djdch.dev.serialtoarduinoled.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.djdch.dev.serialtoarduinoled.listener.SliderChangeListener;
import com.djdch.dev.serialtoarduinoled.listener.TextFieldDocumentListener;
import com.djdch.dev.serialtoarduinoled.serial.SerialLink;
import com.djdch.dev.serialtoarduinoled.serial.SerialUtil;

public class ApplicationFrame extends JFrame {

    public static final String APPLICATION_NAME = "SoundStreamVisualizer";

    private List<JCheckBoxMenuItem> portItems;
    private JCheckBoxMenuItem currentItemPort;
    private JMenuItem connectItem, disconnectItem;

    private List<JComponent> components;
    private JSlider rSlider, gSlider, bSlider;
    private ColorBox box;

    private SerialLink serial;

    public ApplicationFrame() {
        super(APPLICATION_NAME);
//        setSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
            @Override
            public void windowClosing(WindowEvent e) {
                if (serial.isConnected()) {
                    serial.disconnect();
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
            }
            @Override
            public void windowIconified(WindowEvent e) {
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            @Override
            public void windowActivated(WindowEvent e) {
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        portItems = new ArrayList<JCheckBoxMenuItem>();
        currentItemPort = null;
        components = new ArrayList<JComponent>();
        serial = new SerialLink();

        initMenu();
        initContent();

        pack();
        setLocationRelativeTo(null); // Centered window
    }

    public void update() {
        box.setColor(new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue()));

        serial.write(rSlider.getValue());
        serial.write(gSlider.getValue());
        serial.write(bSlider.getValue());
        serial.flush();
    }

    private void initMenu() {
        connectItem = new JMenuItem("Connect");
        connectItem.setEnabled(false);
        connectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBoxMenuItem portItem : portItems) {
                    portItem.setEnabled(false);
                }
                for (JComponent component : components) {
                    component.setEnabled(true);
                }

                connectItem.setEnabled(false);
                disconnectItem.setEnabled(true);

                serial.setPortName(currentItemPort.getText());
                serial.connect();

            }
        });

        disconnectItem = new JMenuItem("Disconnect");
        disconnectItem.setEnabled(false);
        disconnectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectItem.setEnabled(true);
                disconnectItem.setEnabled(false);

                for (JComponent component : components) {
                    component.setEnabled(false);
                }
                for (JCheckBoxMenuItem portItem : portItems) {
                    portItem.setEnabled(true);
                }

                rSlider.setValue(0);
                gSlider.setValue(0);
                bSlider.setValue(0);

                serial.disconnect();
            }
        });

        JMenu actionMenu = new JMenu("Action");
        actionMenu.add(connectItem);
        actionMenu.add(disconnectItem);

        JMenu serialPortMenu = new JMenu("Serial Port");

        for (String port : SerialUtil.getPorts()) {
            JCheckBoxMenuItem portItem = new JCheckBoxMenuItem(port);
            portItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBoxMenuItem portItem = (JCheckBoxMenuItem) e.getSource();

                    if (currentItemPort == null) {
                        currentItemPort = portItem;
                        currentItemPort.setState(true);

                        connectItem.setEnabled(true);
                    } else {
                        if (portItem == currentItemPort) {
                            currentItemPort.setState(false);
                            currentItemPort = null;

                            connectItem.setEnabled(false);
                        } else {
                            currentItemPort.setState(false);
                            currentItemPort = portItem;
                            currentItemPort.setState(true);
                        }
                    }
                }
            });

            serialPortMenu.add(portItem);
            portItems.add(portItem);
        }

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(actionMenu);
        menuBar.add(serialPortMenu);

        setJMenuBar(menuBar);
    }

    private void initContent() {
        /*
         * RED
         */
        ColorLabel rLabel = new ColorLabel(Color.RED);

        rSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        rSlider.setMajorTickSpacing(51);
//        rSlider.setMinorTickSpacing(5);
        rSlider.setPaintTicks(true);
        rSlider.setPaintLabels(true);
        rSlider.setEnabled(false);
        components.add(rSlider);

        JTextField rText = new JTextField();
        rText.setColumns(3);
        rText.setText("0");
        rText.setEnabled(false);
        components.add(rText);

        rSlider.addChangeListener(new SliderChangeListener(rSlider, rText, this));
        rText.getDocument().addDocumentListener(new TextFieldDocumentListener(rText, rSlider));

        JPanel rRwo = new JPanel();
        rRwo.setLayout(new FlowLayout());
        rRwo.add(rLabel);
        rRwo.add(rSlider);
        rRwo.add(rText);

        /*
         * GREEN
         */
        ColorLabel gLabel = new ColorLabel(Color.GREEN);

        gSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        gSlider.setMajorTickSpacing(51);
        gSlider.setPaintTicks(true);
        gSlider.setPaintLabels(true);
        gSlider.setEnabled(false);
        components.add(gSlider);

        JTextField gText = new JTextField();
        gText.setColumns(3);
        gText.setText("0");
        gText.setEnabled(false);
        components.add(gText);

        gSlider.addChangeListener(new SliderChangeListener(gSlider, gText, this));
        gText.getDocument().addDocumentListener(new TextFieldDocumentListener(gText, gSlider));

        JPanel gRwo = new JPanel();
        gRwo.setLayout(new FlowLayout());
        gRwo.add(gLabel);
        gRwo.add(gSlider);
        gRwo.add(gText);

        /*
         * BLUE
         */
        ColorLabel bLabel = new ColorLabel(Color.BLUE);

        bSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        bSlider.setMajorTickSpacing(51);
        bSlider.setPaintTicks(true);
        bSlider.setPaintLabels(true);
        bSlider.setEnabled(false);
        components.add(bSlider);

        JTextField bText = new JTextField();
        bText.setColumns(3);
        bText.setText("0");
        bText.setEnabled(false);
        components.add(bText);

        bSlider.addChangeListener(new SliderChangeListener(bSlider, bText, this));
        bText.getDocument().addDocumentListener(new TextFieldDocumentListener(bText, bSlider));

        JPanel bRwo = new JPanel();
        bRwo.setLayout(new FlowLayout());
        bRwo.add(bLabel);
        bRwo.add(bSlider);
        bRwo.add(bText);

        /*
         * BOX
         */
        box = new ColorBox();

        /*
         * CONTAINER
         */
        setLayout(new GridLayout(4, 1));

        Container content = getContentPane();
        content.add(rRwo);
        content.add(gRwo);
        content.add(bRwo);
        content.add(box);
    }
}
