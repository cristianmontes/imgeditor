/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import pe.com.wildcat.imgeditor.ImageInternalFrame;

/**
 *
 * @author cmontes
 */
public class Utils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String bmp = "bmp";
    
    public final static String xml = "xml";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid.
     * @param path
     * @return  */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se puede encontrar el archivo: " + path);
            return null;
        }
    }
    
    public static byte[] bytefromString(String string) {
        String[] strings = string.replace("[", "").replace("]", "").split(", ");
        byte result[] = new byte[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Byte.parseByte(strings[i]);
        }
        return result;
    }
    
    /*
    public static void main( String []args){
        System.out.println(drawLine(0, 0, 10, 20));
        System.out.println(drawLine(10, 20, 20, 5));
        System.out.println(drawLine(10, 20, 2, 30));
        System.out.println(drawLine(10, 20, 2, 3));
    }*/
    
    public static List<Pixel> drawLine(int x1, int y1, int x2, int y2){
        float pendiente = ((float)(y2-y1)/(x2-x1));
        int xmax = 0;
        int ymax = 0;
        int xmin = 0;
        int ymin = 0; 
        if(x2>x1){
            xmax = x2;
            xmin = x1;
        }else{
            xmax = x1;
            xmin = x2;
        }
        
        if(y2>y1){
            ymax = y2;
            ymin = y1;
        }else{
            ymax = y1;
            ymin = y2;
        }
       System.out.println("pendiente: " + pendiente+" X "+ xmax + " " + xmin  + " Y " + ymax + " " + ymin );
        List<Pixel> listPixel = new ArrayList<Pixel>();
        if(pendiente <= 1){
            for(int i = xmin; i<=xmax; i++){
                int valright = Math.round(pendiente*(i - x2)) + y2; 
                for(int j = ymin; j<=ymax; j++){
                    //Se cumple la condicion por lo tanto el pixel pertenece a la recta
                    //if((j - y2) == Math.round(pendiente*(i - x2))){
                    if(j == valright){
                        listPixel.add(new Pixel(Color.YELLOW.getRGB(), i, j));                    
                        break;
                    }
                }
            }
        }else{
            for(int j = ymin; j<=ymax; j++){
                int valright = Math.round((j - y2)/pendiente) + x2; 
                for(int i = xmin; i<=xmax; i++){
                    //Se cumple la condicion por lo tanto el pixel pertenece a la recta
                    //if((j - y2) == Math.round(pendiente*(i - x2))){
                    if(i == valright){
                        listPixel.add(new Pixel(Color.YELLOW.getRGB(), i, j));                    
                        break;
                    }
                }
            }
        }
        if(x1 == listPixel.get(0).getX()){
            return listPixel;
        }else{
            Collections.reverse(listPixel);
            return listPixel;
        }
        
        
    }
    
    public static Pixel getPixel1(BufferedImage bufferedImage){
        for(int x=0; x<bufferedImage.getWidth(); x++){
            for(int y=0; y<bufferedImage.getHeight(); y++){
                if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x, y)){
                    System.out.println("X: " + x +" Y: " + y);
                    return new Pixel(bufferedImage.getRGB(x, y), x, y);
                }
            }            
        }
        return null;
    }
    
    public static Pixel getPixel4(BufferedImage bufferedImage){
        for(int x=bufferedImage.getWidth()-1; x>0; x--){
            for(int y=bufferedImage.getHeight()-1; y>0; y--){
                if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x, y)){
                    System.out.println("X: " + x +" Y: " + y);
                    return new Pixel(bufferedImage.getRGB(x, y), x, y);
                }
            }            
        }
        return null;
    }
    
    public static List<Pixel> getEsquinas(BufferedImage bufferedImage){
        List<Pixel> listPixels = new ArrayList<Pixel>();
        for(int x=1; x<bufferedImage.getWidth(); x++){
            for(int y=1; y<bufferedImage.getHeight(); y++){
                if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x, y)){
                    if(isEsquina(bufferedImage, x,y)){
                        listPixels.add(new Pixel(bufferedImage.getRGB(x, y), x, y));
                    }
                }
            }            
        }
        
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("D:\\pr\\filename.txt", "utf-8");            
        

        for (int i = 0; i < listPixels.size(); i++) {                
            writer.println(" X: " + listPixels.get(i).getX() + " Y: " + listPixels.get(i).getY() + " Color: " + listPixels.get(i).getPixel());
                
        }
        
        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
           try {writer.close();} catch (Exception ex) {}
        }
        return listPixels;
    }
    
    public static boolean isEsquina(BufferedImage bufferedImage, int x, int y){
        /*
        int pixel1 = 0;
        int pixel2 = 0;
        int pixel3 = 0;
        int pixel4 = 0;*/
        
        if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x-1, y)){            
            if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x+1, y)){
                return false;
            }          
        }
        
        if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x-1, y-1)){
            if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x+1, y+1)){
                return false;
            }
        }
        
        if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x, y-1)){
            if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x, y+1)){
                return false;
            }
        }
        
        if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x+1, y-1)){
            if(Constantes.PIXEL_NEGRO == bufferedImage.getRGB(x-1, y+1)){
               return false;
            }
        }
                
        return true;
    }
    
    public static BufferedImage binarizarImagen(BufferedImage bufferedImage){
        
        int imgWidth = bufferedImage.getWidth();
        int imgHeight = bufferedImage.getHeight();
       

        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                if(Constantes.PIXEL_BLANCO != bufferedImage.getRGB(j, i)){
                    bufferedImage.setRGB(j, i, Constantes.PIXEL_NEGRO);
                }                
            }
        }
        return bufferedImage;
    }
}
