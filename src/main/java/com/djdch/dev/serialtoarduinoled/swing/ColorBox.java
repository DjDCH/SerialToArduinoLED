package com.djdch.dev.serialtoarduinoled.swing;

import java.awt.Color;
import javax.swing.JPanel;

public class ColorBox extends JPanel {

    private Color color;

    public ColorBox() {
//        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setOpaque(true);
        setFocusable(false);

        color = Color.BLACK;

        update();
    }

    public void update() {
        setBackground(color);

        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;

        update();
    }
}
