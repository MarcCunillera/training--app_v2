package com.rgb.training.app.app;

/**
 *
 * @author marccunillera
 */

public class EventListener {

    public void onEvent(Event event) {
        System.out.println("Evento recibido: " + event.getMessage());
    }
}
