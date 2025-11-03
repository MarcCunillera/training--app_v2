package com.rgb.training.app.app;

/**
 *
 * @author marccunillera
 */

public class EventPublisher {

    private EventListener listener;

    public void registerListener(EventListener listener) {
        this.listener = listener;
    }

    public void publish(Event event) {
        if (listener != null) {
            listener.onEvent(event);
        } else {
            System.out.println("No hay listener registrado. Evento ignorado.");
        }
    }
}
