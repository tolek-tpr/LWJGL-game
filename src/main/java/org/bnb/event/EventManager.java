package org.bnb.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class EventManager {

    private static EventManager instance;
    private final HashMap<Class<? extends Listener>, ArrayList<? extends Listener>> listenerMap =
            new HashMap<>();

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) instance = new EventManager();
        return instance;
    }

    public <L extends Listener, E extends Event<L>> void fire(E event) {
        EventManager eventManager = EventManager.getInstance();
        if(eventManager == null)
            return;

        eventManager.fireImpl(event);
    }

    private <L extends Listener, E extends Event<L>> void fireImpl(E event) {
        try
        {
            Class<L> type = event.getListenerType();
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null || listeners.isEmpty())
                return;

            ArrayList<L> listeners2 = new ArrayList<>(listeners);
            listeners2.removeIf(Objects::isNull);

            event.fire(listeners2);

        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

    public <L extends Listener> void add(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null)
            {
                listeners = new ArrayList<>(Arrays.asList(listener));
                listenerMap.put(type, listeners);
                return;
            }

            listeners.add(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();
        }
    }

    public <L extends Listener> void remove(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners != null)
                listeners.remove(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();
        }
    }

}
