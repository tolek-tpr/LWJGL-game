package org.bnb.event;

import java.util.ArrayList;

public interface KeyboardListener extends Listener {

    void onKey(int keyCode, int scanCode, int mods);

    public static class KeyboardEvent extends Event<KeyboardListener> {

        private final int keyCode, scanCode, mods;

        public KeyboardEvent(int keyCode, int scanCode, int mods) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.mods = mods;
        }

        @Override
        public void fire(ArrayList<KeyboardListener> listeners) {
            for (KeyboardListener listener : listeners) listener.onKey(keyCode, scanCode, mods);
        }

        @Override
        public Class<KeyboardListener> getListenerType() {
            return KeyboardListener.class;
        }
    }

}
