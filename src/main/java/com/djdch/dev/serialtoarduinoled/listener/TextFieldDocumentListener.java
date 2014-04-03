package com.djdch.dev.serialtoarduinoled.listener;

import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldDocumentListener implements DocumentListener {

    private final JTextField textField;
    private final JSlider slider;

    public TextFieldDocumentListener(JTextField textField, JSlider slider) {
        this.textField = textField;
        this.slider = slider;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update(e);
    }

    private void update(DocumentEvent e) {
        try {
            int value = Integer.valueOf(textField.getText());

            if (value < 0 || value > 255) {
                throw new NumberFormatException();
            }

            if (value != slider.getValue()) {
                slider.setValue(value);
            }

            textField.setForeground(Color.BLACK);
        } catch (NumberFormatException e1) {
            textField.setForeground(Color.RED);
        }
    }
}
