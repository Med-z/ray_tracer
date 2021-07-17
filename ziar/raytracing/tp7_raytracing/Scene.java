package ziar.raytracing.tp7_raytracing;

import java.util.ArrayList;
import java.awt.Color;

public class Scene {
    
    private ArrayList<Gobject> objects; 
    private ArrayList<LightSource> lights; 
    private Color ambiantColor;
    private float fov; 
    private double minLambda; 
    

    public Scene(){
        this.objects = new ArrayList(); 
        this.lights = new ArrayList(); 
        this.ambiantColor = new Color(255,255,255);
        this.fov = 0.5f;  
    }

    public void addObject(Gobject obj){
        this.objects.add(obj); 
    }
    
    public void addLight(LightSource l){
        this.lights.add(l); 
    }

    public Color findColor(Vec3f p, Vec3f v, int nbRecursivity ) {

        double minLambda = Double.MAX_VALUE;
        Gobject obj = null;
        for(Gobject o : this.objects){
            double intersection = o.getIntersection(p, v);
            if(intersection > 0.001){
                if(minLambda > intersection){
                    minLambda = intersection;
                    obj = o;
                }
            }
        }
        if(obj == null){
            return this.ambiantColor;
        }

        Color result = computeRayColor(obj,p,v,minLambda,nbRecursivity); 

        return result;

    }

    private Color computeRayColor(Gobject obj, Vec3f p, Vec3f v, double minLambda, int nbRecursivity){    
        Color result = multiplyColor(this.ambiantColor, obj.getColor());
        Vec3f vecI = (new Vec3f(v)).scale((float)minLambda);
        for(LightSource light : this.lights){
            boolean vis = true;
            Vec3f vecIS = (new Vec3f(light.getPosition()).sub(vecI));
            for(int i = 0; i < this.objects.size( ); i++){
                double inter = this.objects.get(i).getIntersection(vecI, vecIS);
                if(inter > 0.001 && inter < 1){
                    vis = false;
                }
            }
             
            if(vis){
                Color diffuse = multiplyColor( multiplyColor( light.getColor(),
                    Math.max(0,(new Vec3f(obj.getNormal(vecI).normalize())).dotProduct(vecIS.normalize()))),
                    obj.getColor());
                    
                Vec3f lDir = ((new Vec3f(light.getPosition())).sub(vecI)).normalize();
                Vec3f vDir = ((new Vec3f(p)).sub(vecI)).normalize();
                Vec3f hDir = vDir.add(lDir).normalize();

                Color specular = multiplyColor( multiplyColor( light.getColor(),
                    (float)Math.pow( hDir.dotProduct(obj.getNormal(vecI).normalize()), obj.getShininess())), 
                    obj.getColor());
                
                result = addColor(result,diffuse);
                result = addColor(result,specular);
            }
        }
        
        if (nbRecursivity > 0.001) {
            Vec3f dotNormal = new Vec3f(obj.getNormal(vecI).normalize());
            Vec3f reflectedRay = (new Vec3f(v)).sub(new Vec3f(dotNormal).scale(2 * dotNormal.dotProduct(v)));
            Color ref = multiplyColor(findColor(vecI,reflectedRay,nbRecursivity-1),obj.getReflection());
            result =  addColor(result,ref);  
        }
        return result; 
    }
      
    
    public float getFov(){
        return this.fov;
    }
    
    private Color multiplyColor(Color color1, Color color2){
        float r = ((float)color1.getRed()/255 * (float)color2.getRed()/255);
        float g = ((float)color1.getGreen()/255 * (float)color2.getGreen()/255);
        float b = ((float)color1.getBlue()/255 * (float)color2.getBlue()/255);
        return new Color(r,g,b);
    }
    
    private Color addColor(Color color1, Color color2){
        float r = ((float)color1.getRed()/255 + (float)color2.getRed()/255);
        float g = ((float)color1.getGreen()/255 + (float)color2.getGreen()/255);
        float b = ((float)color1.getBlue()/255 + (float)color2.getBlue()/255);
        if(r > 1)
            r = 1; 
        if(g > 1)
            g = 1;
        if(b > 1)
            b = 1;
        return new Color(r,g,b);
    }
    
    private Color multiplyColor(Color color1, float value){
        float r = ((float)color1.getRed()/255 * value);
        float g = ((float)color1.getGreen()/255 * value);
        float b = ((float)color1.getBlue()/255 * value);
        if(r > 1)
            r = 1;
        if(g > 1)
            g = 1;
        if(b > 1)
            b = 1;
        return new Color(r,g,b);
    }
}