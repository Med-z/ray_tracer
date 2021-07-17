package ziar.raytracing.tp7_raytracing;

import java.awt.Color;

public class Sphere extends Gobject {

    private float radius; 

    public Sphere( Vec3f p, float r,Color color, float shininess, float reflection){
        super(p,color,shininess,reflection); 
        this.radius = r; 
    }

    @Override
    public double getIntersection(Vec3f p,Vec3f v){

        Vec3f vec =(new Vec3f(p)).sub(getCoord());
        
        float a = v.dotProduct(v);
        float b = 2 * vec.dotProduct(v);
        float c = vec.dotProduct(vec) - (this.radius*this.radius);
        float delta = b*b - 4*a*c;
        if(delta < 0){
            return -1.0;
        }
        else if(delta == 0){ 
            return (-b - Math.sqrt(delta)) / (2.0*a);
        }
        else{
            double x1 = (-b - Math.sqrt(delta)) / (2.0*a);
            double x2 = (-b + Math.sqrt(delta)) / (2.0*a);
            if(x1 < 0 && x2 < 0)
                return -1.0;
            else if(x1 < 0 && x2 > 0 )
                return x2;
            else 
                return x1;
        }
    }

    
    @Override
    public Vec3f getNormal(Vec3f p){
        return ((new Vec3f(p)).sub(getCoord()));
    }
    
}
