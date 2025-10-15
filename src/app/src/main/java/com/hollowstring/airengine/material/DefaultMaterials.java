package com.hollowstring.airengine.material;

public class DefaultMaterials {
    public static String vertexShader = """
#version 330 core
layout (location = 0) in vec3 attribPos;
layout (location = 1) in vec2 attribUV;
layout (location = 2) in vec3 attribNormal;
out vec2 UV;
out vec3 Normal;
out vec3 FragPos;

uniform mat4 transform;
uniform mat4 cameraTransform;
uniform mat4 projection;

void main(){
    gl_Position = projection * cameraTransform * transform * vec4(attribPos, 1.0);
    UV = attribUV;
    Normal = mat3(transpose(inverse(transform))) * attribNormal;
    FragPos = vec3(transform * vec4(attribPos, 1.0));
}
    """;
    public static String fragmentShader = """
#version 330 core
out vec4 FragColor;
in vec2 UV;
in vec3 Normal;
in vec3 FragPos;
uniform sampler2D defaultTexture;
uniform float textureUVTile = 1.0f;
uniform vec3 ambientLightColor;
uniform vec3 pointLightColor;
uniform vec3 pointLightPos;
uniform vec3 camPos;
uniform float specularStrength = 1.0;
uniform int specularShineStrength = 32;

void main(){
    vec3 pointLightDir = normalize(pointLightPos - FragPos);
    vec3 diffuse = max(dot(normalize(Normal), pointLightDir), 0.0) * pointLightColor;
    vec3 camDir = normalize(camPos - FragPos);
    vec3 reflectDir = reflect(-pointLightDir, normalize(Normal));
    float shine = pow(max(dot(camDir, reflectDir), 0.0), specularShineStrength);
    vec3 specular = specularStrength * shine * pointLightColor;
    FragColor = vec4((ambientLightColor+diffuse+specular) * texture(defaultTexture, UV * textureUVTile).rgb, 1.0);
}
    """;
}
