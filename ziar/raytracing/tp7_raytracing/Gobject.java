package ziar.raytracing.tp7_raytracing;


import java.awt.Color;

public abstract class Gobject {
    
    private Vec3f coord; 
    private Color color;
    private float shininess; 
    private float reflection; 


    public Gobject(Vec3f p, Color color, float shininess, float reflection){
        this.coord = p; 
        this.color = color; 
        this.shininess = shininess; 
        this.reflection = reflection; 
    }


    public abstract  double getIntersection(Vec3f P,Vec3f v);


    public Vec3f getCoord() {
        return coord;
    }


    public void setCoord(Vec3f coord) {
        this.coord = coord;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    public abstract Vec3f getNormal( Vec3f p); 

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getReflection() {
        return reflection;
    }

    public void setReflection(float reflection) {
        this.reflection = reflection;
    }
    
    
    
    
}
