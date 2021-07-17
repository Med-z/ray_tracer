package ziar.raytracing.tp7_raytracing;

import java.awt.Color;


public class Plane extends Gobject{

    private float x;
    private Vec3f vec;  // normale du plan 
   
    public Plane(   Vec3f p,Vec3f n, Color color, float shininess, float reflection){
        super(p,color,shininess,reflection); 
        this.vec = n; 
        this.x = -1*(vec.x * p.x + vec.y * p.y + vec.z*p.z);

    }

    @Override
    public double getIntersection(Vec3f P,Vec3f v){
       float res = this.vec.dotProduct(v);
       if(res == 0){
           return -1; 
       } else {
           return (-(P.dotProduct(this.vec)) - x) / res;
       }      
    }

    @Override
    public Vec3f getNormal(Vec3f p) {
        return this.vec;
    }
    
}


