/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import pe.com.wildcat.imgeditor.ImageInternalFrame;

/**
 *
 * @author cmontes
 */
public class TreeComponentMouseListener extends MouseAdapter implements TreeSelectionListener {
    private JTree treeRecursos;
    private ClosableTabbedPane desktopTabPanel;
    
    public TreeComponentMouseListener(JTree treeRecursos, ClosableTabbedPane desktopTabPanel){
        this.treeRecursos =  treeRecursos;
        this.desktopTabPanel = desktopTabPanel;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int selRow = treeRecursos.getRowForLocation(e.getX(), e.getY());
        //TreePath selPath = treeRecursos.get|PathForLocation(e.getX(), e.getY());
        if(selRow != -1) {
            if(e.getClickCount() == 1) {
                //mySingleClick(selRow, selPath);
            }
            else if(e.getClickCount() == 2) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           treeRecursos.getLastSelectedPathComponent();
                if(node.getUserObject() instanceof ImgComponent){
                    try {
                        ImgComponent imgComponent = (ImgComponent)node.getUserObject();
                        if(desktopTabPanel.getListComponentes() != null && !desktopTabPanel.getListComponentes().contains(imgComponent.getIdentity())){                        
                            ImageInternalFrame imageInternalFrame = new ImageInternalFrame();
                            //imageInternalFrame.setImage(imgComponent.getAbsolutePath());
                            imageInternalFrame.setImage3(imgComponent.getIdentity().toString(), imgComponent.getbFile(), imgComponent.getWidth(), imgComponent.getHeight());
                            for(int i=0; i<imgComponent.getListXmlComponent().size(); i++){
                                for(int j=0; j<imgComponent.getListXmlComponent().get(i).getListPixel().size(); j++){
                                    imageInternalFrame.getBufferedImage().setRGB(imgComponent.getListXmlComponent().get(i).getListPixel().get(j).getX(),
                                            imgComponent.getListXmlComponent().get(i).getListPixel().get(j).getY(),imgComponent.getListXmlComponent().get(i).getListPixel().get(j).getPixel());
                                    if(Constantes.COMPONENT_TYPE_FIGURE == imgComponent.getListXmlComponent().get(i).getType()){
                                        Graphics g = imageInternalFrame.getBufferedImage().getGraphics();
                                        g.setColor( Color.RED );
                                        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
                                        g.setFont(font);           
                                        g.drawString(""+(imgComponent.getListXmlComponent().get(i).getOrden()), 
                                                imgComponent.getListXmlComponent().get(i).getListPixel().get(0).getX(), 
                                                imgComponent.getListXmlComponent().get(i).getListPixel().get(0).getY());
                                    }
                                }                                
                            }
                            /*
                            Graphics g = imageInternalFrame.getBufferedImage().getGraphics();
                            g.setColor( Color.RED );
                            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
                            g.setFont(font);           
                            g.drawString(""+(contador++), currentX, currentY);*/
            
                            imageInternalFrame.redibujar(imageInternalFrame.getBufferedImage());
                                    
                            System.out.println("Abriendo desde el doble click " );
                            desktopTabPanel.addTab(imgComponent.getIdentity(), imgComponent.getName(), new JScrollPane(imageInternalFrame));
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(TreeComponentMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           treeRecursos.getLastSelectedPathComponent();
        if(node.getUserObject() instanceof ImgComponent){
            try {
                ImgComponent imgComponent = (ImgComponent)node.getUserObject();
                ImageInternalFrame imageInternalFrame = new ImageInternalFrame();
                //imageInternalFrame.setImage(imgComponent.getAbsolutePath());
                System.out.println("desktopTabPanel " + desktopTabPanel);
                desktopTabPanel.addTab(imgComponent.getIdentity(), imgComponent.getName(), imageInternalFrame);
            } catch (Exception ex) {
                Logger.getLogger(TreeComponentMouseListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
