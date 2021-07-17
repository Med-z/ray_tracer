package ziar.raytracing.tp7_raytracing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.awt.Color;

/**
 *
 * @author P. Meseure based on a Java Adaptation of a C code by B. Debouchages (M1, 2018-2019)
 */
public class JavaTga
{
    /**
     * 
     * @param fout : output file stream
     * @param n : short to write to disc in little endian
     */
    private static void writeShort(FileOutputStream fout,int n) throws IOException
    {
        fout.write(n&255);
        fout.write((n>>8)&255);
    }

    /**
     * 
     * @param filename name of final TGA file
     * @param buffer buffer that contains the image. 3 bytes per pixel ordered this way : Blue, Green, Red
     * @param width Width of the image
     * @param height Height of the image
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    private static void saveTGA(String filename, byte buffer[], int width, int height) throws IOException, UnsupportedEncodingException {

        FileOutputStream fout = new FileOutputStream(new File(filename));

        fout.write(0); // Comment size, no comment
        fout.write(0); // Colormap type: No colormap
        fout.write(2); // Image type
        writeShort(fout,0); // Origin
        writeShort(fout,0); // Length
        fout.write(0); // Depth
        writeShort(fout,0); // X origin
        writeShort(fout,0); // Y origin
        writeShort(fout,width); // Width of the image
        writeShort(fout,height); // Height of the image
        fout.write(24); // Pixel size in bits (24bpp)
        fout.write(0); // Descriptor

        /* Write the buffer */
        fout.write(buffer);

        fout.close();
    }

    
    
    
    /**
     * @param args no command line arguments
     */
    public static void main(String[] args) {


        Scene scene  = new Scene(); 
        
     
        
        scene.addObject(new Plane(new Vec3f(0, -5, 0),new Vec3f(0, 1, 0),new Color(102, 102, 102),100,0.2f));
        
        scene.addObject(new Plane(new Vec3f(0, -5, -30),new Vec3f(0, 0, 1),new Color(255, 102, 98),100,0f)); 
        
        scene.addObject(new Sphere(new Vec3f(-5, -2, -15), 3f, new Color(0, 92, 153),20f,0.2f)); 
        
        scene.addObject(new Sphere(new Vec3f(5, -2, -15), 3f, new Color(255, 153, 51),20f,0.2f)); 
        
        scene.addObject(new Sphere(new Vec3f(0, 4, -15), 2.5f, new Color(90, 15, 251),20f,0.2f)); 
        
        scene.addObject(new Sphere(new Vec3f(-2, -2, -11), 2f, new Color(56, 99, 173),20f,0.5f)); 
        
        scene.addObject(new Sphere(new Vec3f(2, -2, -11), 2f, new Color(145,42, 51),20f,0.5f)); 
        
        scene.addLight(new LightSource(new Vec3f(-25,0,4), new Color(155,155,155)));


        
     
        int w=1024;
        int h=768;
        byte buffer[]=new byte[3*w*h];
        
        for(int row = 0; row < h; row++){ // for each row of the image
            for(int col = 0; col < w; col++){ // for each column of the image
                
                int index = 3*((row*w)+col); // compute index of color for pixel (x,y) in the buffer



                Vec3f point = new Vec3f(0,0,0); 
                Vec3f vector = new Vec3f(
                    ((float)(col-w/2)/((float)h)),
                    ((float)(row-h/2)/((float)h)),
                    -scene.getFov());           
                
                //le dernier paramètre représente le nombre de recursion voulues 
                Color c = scene.findColor(point,vector,4);

                
                buffer[index] = (byte) c.getBlue();
                buffer[index + 1] = (byte) c.getGreen();
                buffer[index + 2] = (byte) c.getRed();

            }
        }
        try {
            saveTGA("imagetest.tga",buffer,w,h);
        }
        catch(Exception e)
        {
            System.err.println("TGA file not created :"+e);
        }
    }  
}

