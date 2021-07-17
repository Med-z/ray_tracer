package ziar.raytracing.tp7_raytracing;


import java.awt.Color;

public class LightSource {
    private Vec3f position;
    private Color ambiant;
    private Color diffuse;
    private Color specular;
    private Color color; 
    
    public LightSource(Vec3f pos, Color color){
        this.position = pos; 
        this.color = color; 
    }
    
    public LightSource(Vec3f pos, Color amb,Color diff,
    Color spec){
        this.position = pos;
        this.ambiant = amb;
        this.diffuse = diff;
        this.specular = spec;
    }

    public Vec3f getPosition() {
        return position;
    }


    public void setPosition(Vec3f position) {
        this.position = position;
    }

    public Color getAmbiant() {
        return ambiant;
    }

    public void setAmbiant(Color ambiant) {
        this.ambiant = ambiant;
    }


    public Color getDiffuse() {
        return diffuse;
    }


    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }


    public Color getSpecular() {
        return specular;
    }


    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
    




}