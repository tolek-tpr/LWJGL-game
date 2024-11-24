package org.bnb.render;

import org.lwjgl.opengl.GL30;

public enum RenderType {

    LINES,
    TRIANGLES,
    QUADS;

    public static int getGlFunc(RenderType type) {
        switch (type) {
            case LINES -> { return GL30.GL_LINES; }
            case TRIANGLES -> { return GL30.GL_TRIANGLES; }
            case QUADS -> { return GL30.GL_QUADS; }
        }
        return GL30.GL_LINES;
    }

}
