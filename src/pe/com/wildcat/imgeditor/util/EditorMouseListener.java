/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import pe.com.wildcat.imgeditor.ImageInternalFrame;

/**
 *
 * @author cmontes
 */
public class EditorMouseListener extends MouseAdapter{
    
    private final ImageInternalFrame internalFrame;
    
    private List<Pixel> listPixelsFigure;
    int currentX = -1;       
    int currentY = -1;
    int sourceX = 0;
    int sourceY = 0;
    int contador = 1;
    Pixel currentPixel = null;
        
    public EditorMouseListener(ImageInternalFrame internalFrame){
        this.internalFrame = internalFrame;
    }
    
    @Override
    public void mouseClicked(MouseEvent event){
        //int x = (int) event.getX();
        //int y = (int) event.getY();
        System.out.println("X "+ (int) event.getX() + " Y " + (int) event.getY());
        int x = currentX;
        int y = currentY;
        if(x != -1 && y != -1){
        if(internalFrame.getFigure().get(y).get(x).getPixel() == Constantes.PIXEL_NEGRO){
            listPixelsFigure = new ArrayList<Pixel>();
            int numitera = 0;
            while(true && numitera<internalFrame.getBufferedImage().getWidth()*internalFrame.getBufferedImage().getHeight()){
                numitera ++;
                Pixel pixel = findNextPixel(x,y);
                if(pixel != null){
                    listPixelsFigure.add(pixel);
                    x = pixel.getX();
                    y = pixel.getY();
                }else{
                    break;
                }
            }
            if(listPixelsFigure != null && listPixelsFigure.size() > 0){
                for(int i=0; i<listPixelsFigure.size(); i++){
                    internalFrame.getBufferedImage().setRGB(listPixelsFigure.get(i).getX(), listPixelsFigure.get(i).getY(), Color.BLUE.getRGB());
                    listPixelsFigure.get(i).setPixel(Color.BLUE.getRGB());
                }
                
                XmlComponent xmlComponentLine = new XmlComponent();
                xmlComponentLine.setOrden(contador);
                xmlComponentLine.setType(Constantes.COMPONENT_TYPE_CONNECTOR);//componente 2 de tipo conector (linea amarilla)
                xmlComponentLine.setListPixel(Utils.drawLine(sourceX, sourceY, currentX, currentY));
                internalFrame.addXMLComponent(xmlComponentLine);
                
                XmlComponent xmlComponent = new XmlComponent();
                xmlComponent.setOrden(contador);
                xmlComponent.setType(Constantes.COMPONENT_TYPE_FIGURE);//componente 1 de tipo figura
                xmlComponent.setListPixel(listPixelsFigure);
                internalFrame.addXMLComponent(xmlComponent);
                
                sourceX = xmlComponent.getListPixel().get(xmlComponent.getListPixel().size()-1).getX();
                sourceY = xmlComponent.getListPixel().get(xmlComponent.getListPixel().size()-1).getY();
                System.out.println(xmlComponentLine.getListPixel());
                for(int i=0; i<xmlComponentLine.getListPixel().size(); i++){
                    internalFrame.getBufferedImage().setRGB(xmlComponentLine.getListPixel().get(i).getX(), xmlComponentLine.getListPixel().get(i).getY(), Color.YELLOW.getRGB());
                }
            }
            
            Graphics g = internalFrame.getBufferedImage().getGraphics();
            g.setColor( Color.RED );
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
            g.setFont(font);           
            g.drawString(""+(contador++), currentX, currentY);
            
            internalFrame.redibujar(internalFrame.getBufferedImage());
            
        }
        }
    }
    
    private Pixel findNextPixel(int x, int y){
        
        if(internalFrame.getFigure().get(y).get(x-1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y).get(x-1))){
                return internalFrame.getFigure().get(y).get(x-1);
            }
        }
        if(internalFrame.getFigure().get(y-1).get(x-1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y-1).get(x-1))){
                return internalFrame.getFigure().get(y-1).get(x-1);
            }
        }
        if(internalFrame.getFigure().get(y-1).get(x).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y-1).get(x))){
                return internalFrame.getFigure().get(y-1).get(x);
            }
        }        
        if(internalFrame.getFigure().get(y-1).get(x+1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y-1).get(x+1))){
                return internalFrame.getFigure().get(y-1).get(x+1);
            }
        }
        if(internalFrame.getFigure().get(y).get(x+1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y).get(x+1))){
                return internalFrame.getFigure().get(y).get(x+1);
            }
        }
        if(internalFrame.getFigure().get(y+1).get(x+1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y+1).get(x+1))){
                return internalFrame.getFigure().get(y+1).get(x+1);
            }
        }
        if(internalFrame.getFigure().get(y+1).get(x).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y+1).get(x))){
                return internalFrame.getFigure().get(y+1).get(x);
            }
        }
        if(internalFrame.getFigure().get(y+1).get(x-1).getPixel() == Constantes.PIXEL_NEGRO){
            if(!listPixelsFigure.contains(internalFrame.getFigure().get(y+1).get(x-1))){
                return internalFrame.getFigure().get(y+1).get(x-1);
            }
        }
        return null;
    }
    
    @Override 
    public void mouseMoved(MouseEvent event) {
        //this.internalFrame.validatePosition(event.getX(), event.getY());
        int minX = event.getX() - 8;
        int maxX = event.getX() + 0;
        int minY = event.getY() - 8;
        int maxY = event.getY() + 0;
        
        /*     
        if(internalFrame.getFigure().get(event.getY()).get(event.getX()).getPixel() == Constantes.PIXEL_NEGRO){
            internalFrame.getPnlImage().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }else{
            internalFrame.getPnlImage().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }*/
        //System.out.println("currentXXXX " + event.getX() + " currentYYYY " + event.getY());
        if(minX < 0) minX = 0;
        if(minY < 0) minY = 0;
        borrarEsquina();
        //if(maxX >= internalFrame.getImgHeight()) maxX = internalFrame.getImgHeight()-1;
        //if(maxY >= internalFrame.getImgWidth()) maxY = internalFrame.getImgWidth()-1;
        if(encontrarPixel(minX, maxX, minY, maxY)){
            System.out.println("currentX " + currentX + " currentY " + currentY);
            internalFrame.getPnlImage().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            //borrarEsquina();
            if(encontrarEsquina(currentX, currentY, 5)){
                System.out.println("Esquina currentX " + currentX + " currentY " + currentY);
                dibujarEsquina();
            }else{                
                currentPixel = null;
            }
            
        }else{
            internalFrame.getPnlImage().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));            
            currentX = -1;
            currentY = -1;            
            currentPixel = null;            
            
        }
        //}
    } 
    
    public boolean encontrarPixel(int minX, int maxX, int minY, int maxY){
        for(int i = minX; i <= maxX; i++){
            for(int j = minY; j <= maxY; j++){
                //validar que los rangos no excedan fuera del rango de la figura, para evitar un indexbound exception
                if(j < internalFrame.getFigure().size()){
                    if(i < internalFrame.getFigure().get(j).size()){
                        if(Constantes.PIXEL_NEGRO == internalFrame.getFigure().get(j).get(i).getPixel()){                            
                            currentX = i;
                            currentY = j;
                            //System.out.println("currentX " + currentX + " currentY " + currentY);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean encontrarEsquina(int x, int y, int radio){
        int minX = x - radio;
        int minY = y - radio;
        int maxX = x + radio;
        int maxY = y + radio;
        for(int i = minX; i < maxX; i++){
            for(int j = minY; j < maxY; j++){
                //validar que los rangos no excedan fuera del rango de la figura, para evitar un indexbound exception
                if(j>=0 && j < internalFrame.getFigure().size()){                    
                    if(i>=0 && i < internalFrame.getFigure().get(j).size()){
                        if(internalFrame.getEsquinasMap().containsKey(internalFrame.getFigure().get(j).get(i).getX()+","+internalFrame.getFigure().get(j).get(i).getY())){
                            currentX = i;
                            currentY = j;
                            currentPixel = internalFrame.getFigure().get(j).get(i);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void dibujarEsquina(){
        int radio = 1;
        if(currentPixel != null){
            Graphics g = internalFrame.getBufferedImage().getGraphics();
            g.setColor( Color.ORANGE );           
            g.drawRect(currentPixel.getX()-radio, currentPixel.getY()-radio,2*radio, 2*radio);            
            internalFrame.redibujar(internalFrame.getBufferedImage());
        }
    }
    
    public void borrarEsquina(){
        int radio = 2;
        if(currentPixel != null){
            for(int i = currentPixel.getX() - radio; i < currentPixel.getX() + radio; i++){
                for(int j = currentPixel.getY() - radio; j < currentPixel.getY() + radio; j++){
                    //validar que los rangos no excedan fuera del rango de la figura, para evitar un indexbound exception
                    if(j>=0 && j < internalFrame.getFigure().size()){
                        if(i>=0 && i < internalFrame.getFigure().get(j).size()){
                            internalFrame.getBufferedImage().setRGB(internalFrame.getFigure().get(j).get(i).getX(), internalFrame.getFigure().get(j).get(i).getY(), internalFrame.getFigure().get(j).get(i).getPixel());
                        }
                    }
                }
            }            
            internalFrame.redibujar(internalFrame.getBufferedImage());
        }
        currentPixel = null;
    }
    
}