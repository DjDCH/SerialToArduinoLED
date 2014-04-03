package com.djdch.dev.serialtoarduinoled.swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class ColorLabel extends JPanel {

    private static final int PREFERRED_WIDTH = 12;
    private static final int PREFERRED_HEIGHT = 12;

    public ColorLabel(Color color) {
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setBackground(color);
        setOpaque(true);
        setFocusable(false);
    }
}
