package com.rgb.training.app.boundary.viewbeans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author luiscarlosgonzalez
 */
@Named
@ViewScoped
public class TestViewBean implements Serializable {
    
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextWithExtra() {
        return "Text: " + this.text;
    }
}
