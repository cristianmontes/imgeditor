/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.ImagePainter;
import pe.com.wildcat.imgeditor.util.Constantes;
import pe.com.wildcat.imgeditor.util.EditorMouseListener;
import pe.com.wildcat.imgeditor.util.FileWrapper;
import pe.com.wildcat.imgeditor.util.Pixel;
import pe.com.wildcat.imgeditor.util.Utils;
import pe.com.wildcat.imgeditor.util.XmlComponent;

/**
 *
 * @author cmontes
 */
public class ImageInternalFrame extends javax.swing.JPanel {

    
    //private Pixel[][] figure; 
    private List<List<Pixel>> figure;
    private BufferedImage bufferedImage;
    private String identity;
    private List<XmlComponent> listXmlComponent;
    private List<Pixel> esquinas;
    private Map<String, Pixel> esquinasMap;
    
    private int imgWidth;
    private int imgHeight;
    /**
     * Creates new form ImageInternalFrame
     */
    public ImageInternalFrame() {
        super();
        initComponents();
        setVisible(true);
        listXmlComponent = new ArrayList<XmlComponent>();
    }
    
    public void setImage3(String identity, byte[] bImage, int width, int height) throws IOException{
        //bufferedImage = ImageIO.read(new File(filename));
        this.identity = identity;
        InputStream in = new ByteArrayInputStream(bImage);
        bufferedImage = ImageIO.read(in);
        //bufferedImage = bufferedImage.getSubimage(width, width, width, width);
        
        figure = new ArrayList<List<Pixel>>();
        pixelearImagen(bufferedImage);
        esquinas = Utils.getEsquinas(bufferedImage);
        esquinasMap = new HashMap<String, Pixel>();
        for(int i=0; i<esquinas.size(); i++){
            esquinasMap.put(esquinas.get(i).getX() + "," + esquinas.get(i).getY(), esquinas.get(i));
        }
        /*for(int i=0; i<esquinas.size(); i++){
            bufferedImage.setRGB(esquinas.get(i).getX(), esquinas.get(i).getY(), Color.RED.getRGB());
        }*/
        ImagePainter image = new ImagePainter(bufferedImage);
        pnlImage.setBackgroundPainter(image);
        pnlImage.setSize(width, height);
        pnlImage.setPreferredSize(new Dimension(width, height));
        pnlImage.setMinimumSize(new Dimension(width, height));
        pnlImage.setMaximumSize(new Dimension(width, height));
        scrollImage.setPreferredSize(new Dimension(width+10, height+10));
        scrollImage.setMinimumSize(new Dimension(width+10, height+10));
        scrollImage.setMaximumSize(new Dimension(width+10, height+10));
        //this.setSize(width+20, height+35);
        EditorMouseListener mouseListener = new EditorMouseListener(this);
        pnlImage.addMouseListener(mouseListener);
        pnlImage.addMouseMotionListener(mouseListener);
        
    }
    
    public void setImage2(ByteArrayInputStream bImage, int width, int height) throws IOException{
        //bufferedImage = ImageIO.read(new File(filename));
        
        //InputStream in = new ByteArrayInputStream(bImage);
        bufferedImage = ImageIO.read(bImage);
        figure = new ArrayList<List<Pixel>>();
        pixelearImagen(bufferedImage);        
        ImagePainter image = new ImagePainter(bufferedImage);        
        pnlImage.setBackgroundPainter(image);
        pnlImage.setSize(width, height);
        this.setSize(width+20, height+35);
        EditorMouseListener mouseListener = new EditorMouseListener(this);
        pnlImage.addMouseListener(mouseListener);
        pnlImage.addMouseMotionListener(mouseListener);
        
    }
    
    public void setImage(String filename) throws IOException{
        bufferedImage = ImageIO.read(new File(filename));
        figure = new ArrayList<List<Pixel>>();
        pixelearImagen(bufferedImage);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        ImagePainter image = new ImagePainter(bufferedImage);        
        pnlImage.setBackgroundPainter(image);
        pnlImage.setSize(width, height);
        //this.setSize(width+20, height+35);
        EditorMouseListener mouseListener = new EditorMouseListener(this);
        pnlImage.addMouseListener(mouseListener);
        pnlImage.addMouseMotionListener(mouseListener);
        
    }
    
    public void redibujar(BufferedImage bufferedImage){
        ImagePainter image = new ImagePainter(bufferedImage);
        
        pnlImage.setBackgroundPainter(image);
    }
    
    private void pixelearImagen(BufferedImage image) {
        imgWidth = image.getWidth();
        imgHeight = image.getHeight();
        //System.out.println(" imgWidth " + imgWidth + " imgHeight " + imgHeight);
        
        /*PrintWriter writer = null;

        try {
            writer = new PrintWriter("D:\\pr\\filename.txt", "utf-8");            
        */

        for (int i = 0; i < imgHeight; i++) {
            figure.add(new ArrayList<Pixel>());
            for (int j = 0; j < imgWidth; j++) {
                figure.get(i).add(new Pixel(image.getRGB(j, i),j,i));
                
                /*if(Constantes.PIXEL_BLANCO != image.getRGB(j, i)){
                    writer.println(" X: " + j + " Y: " + i + " Color: " + image.getRGB(j, i));
                }*/
            }
        }
        /*
        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
           try {writer.close();} catch (Exception ex) {}
        }*/
    }
    
    public void addXMLComponent(XmlComponent xmlComponent){
        listXmlComponent.add(xmlComponent);
        FileWrapper.getInstance().updateFigure(xmlComponent, identity);
    }
    
    public void validatePosition(int x, int y){
        pnlImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    
    public List<List<Pixel>> getFigure() {
        return figure;
    }

    public void setFigure(List<List<Pixel>> figure) {
        this.figure = figure;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public JXPanel getPnlImage() {
        return pnlImage;
    }

    public void setPnlImage(JXPanel pnlImage) {
        this.pnlImage = pnlImage;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    /*
    public List<XmlComponent> getListXmlComponent() {
        return listXmlComponent;
    }*/

    public void setListXmlComponent(List<XmlComponent> listXmlComponent) {
        this.listXmlComponent = listXmlComponent;
    }

    public Map<String, Pixel> getEsquinasMap() {
        return esquinasMap;
    }

    public void setEsquinasMap(Map<String, Pixel> esquinasMap) {
        this.esquinasMap = esquinasMap;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollImage = new javax.swing.JScrollPane();
        pnlImage = new org.jdesktop.swingx.JXPanel();

        setLayout(new java.awt.GridBagLayout());

        scrollImage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollImage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollImage.setBorder(null);
        scrollImage.setMaximumSize(new java.awt.Dimension(281, 126));
        scrollImage.setPreferredSize(new java.awt.Dimension(381, 146));

        pnlImage.setMaximumSize(new java.awt.Dimension(279, 124));
        pnlImage.setMinimumSize(new java.awt.Dimension(279, 124));
        pnlImage.setPreferredSize(new java.awt.Dimension(279, 124));
        pnlImage.setLayout(null);
        scrollImage.setViewportView(pnlImage);

        add(scrollImage, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXPanel pnlImage;
    private javax.swing.JScrollPane scrollImage;
    // End of variables declaration//GEN-END:variables
}
