package org.bnb;

import org.bnb.event.EventImpl;
import org.bnb.events.Keyboard;
import org.bnb.window.Window;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Events
        ArrayList<EventImpl> events = new ArrayList<>();

        events.add(new Keyboard());

        events.forEach(EventImpl::onEnable);

        // Client start
        LWGClient client = LWGClient.getInstance();
        client.getWindow().run();
    }

}