package com.djdch.dev.serialtoarduinoled.listener;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.djdch.dev.serialtoarduinoled.swing.ApplicationFrame;

public class SliderChangeListener implements ChangeListener {

    private final JSlider slider;
    private final JTextField textField;
    private final ApplicationFrame app;

    public SliderChangeListener(JSlider slider, JTextField textField, ApplicationFrame app) {
        this.slider = slider;
        this.textField = textField;
        this.app = app;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = -1;

        try {
            value = Integer.valueOf(textField.getText());
        } catch (NumberFormatException e1) {
        }

        if (value != slider.getValue()) {
            textField.setText(String.valueOf(slider.getValue()));
            app.update();
        }
    }
}
