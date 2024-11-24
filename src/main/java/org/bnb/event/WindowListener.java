package org.bnb.event;

import java.util.ArrayList;

public interface WindowListener extends Listener {

    void onWindowResize(long window, int width, int height);

    public static class WindowResizeEvent extends Event<WindowListener> {

        private final long window;
        private final int width, height;

        public WindowResizeEvent(long windowHandle, int width, int height) {
            this.window = windowHandle;
            this.width = width;
            this.height = height;
        }

        @Override
        public void fire(ArrayList<WindowListener> listeners) {
            for (WindowListener l : listeners) l.onWindowResize(window, width, height);
        }

        @Override
        public Class<WindowListener> getListenerType() {
            return WindowListener.class;
        }
    }

}
