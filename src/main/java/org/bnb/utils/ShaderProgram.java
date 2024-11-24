package org.bnb.utils;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

public class ShaderProgram {

    public int ID;

    public ShaderProgram(InputStream vertexFile, InputStream fragmentFile) {
        try {
            String vCode = LWGUtil.readFile(vertexFile);
            String fCode = LWGUtil.readFile(fragmentFile);

            int vertex, fragment;

            vertex = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
            GL20.glShaderSource(vertex, vCode);
            GL20.glCompileShader(vertex);
            checkErrors(vertex, "SHADER");

            fragment = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
            GL20.glShaderSource(fragment, fCode);
            GL20.glCompileShader(fragment);
            checkErrors(fragment, "SHADER");

            ID = GL20.glCreateProgram();
            GL20.glAttachShader(ID, vertex);
            GL20.glAttachShader(ID, fragment);
            GL20.glLinkProgram(ID);
            checkErrors(ID, "PROGRAM");

            GL20.glDeleteShader(vertex);
            GL20.glDeleteShader(fragment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use() {
        GL20.glUseProgram(ID);
    }
    public void unbind() { GL20.glUseProgram(0); }

    public void setBoolean(String uniformName, boolean value) {
        GL20.glUniform1i(GL20.glGetUniformLocation(ID, uniformName), value ? 1 : 0);
    }

    public void setInteger(String uniformName, int value) {
        GL20.glUniform1i(GL20.glGetUniformLocation(ID, uniformName), value);
    }

    public void setFloat(String uniformName, float value) {
        GL20.glUniform1f(GL20.glGetUniformLocation(ID, uniformName), value);
    }

    public void setFloat3(String uniformName, float f1, float f2, float f3) {
        GL20.glUniform3f(GL20.glGetUniformLocation(ID, uniformName), f1, f2, f3);
    }

    public void setFloat4(String uniformName, float f1, float f2, float f3, float f4) {
        GL20.glUniform4f(GL20.glGetUniformLocation(ID, uniformName), f1, f2, f3, f4);
    }

    public void setMat4(String uniformName, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(ID, uniformName), false, fb);
        }
    }

    private void checkErrors(int shader, String type) {
        if (type.equals("PROGRAM")) {
            if (!GL20.glGetProgramInfoLog(shader).isEmpty()) {
                throw new RuntimeException("Program failed!");
            }
        } else {
            if (!GL20.glGetShaderInfoLog(shader).isEmpty()) {
                throw new RuntimeException("Shader didn't load properly!");
            }
        }
    }

}
