#version 330 core

layout(location = 0) in vec3 position; // Vertex positions
uniform vec4 color;                   // Uniform color

out vec4 vertexColor; // Pass the color to the fragment shader

void main() {
    gl_Position = vec4(position, 1.0); // Set the vertex position
    vertexColor = color;              // Use uniform color
}