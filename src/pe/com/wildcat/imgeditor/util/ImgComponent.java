/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author cmontes
 */
public class ImgComponent {
    private UUID identity;
    private String name;
    private int width;
    private int height;
    private byte[] bFile;
    //private String absolutePath;
    private List<XmlComponent> listXmlComponent = new ArrayList<XmlComponent>();
    
    public ImgComponent(){
        identity = UUID.randomUUID();
    }
    
    public ImgComponent(String name){
        this.identity = UUID.randomUUID();
        this.name = name;
        //this.absolutePath = absolutePath;
    }
    
    public ImgComponent(String name, String strIdentity){
        this.identity = UUID.fromString(strIdentity);
        this.name = name;
        //this.absolutePath = absolutePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getIdentity() {
        return identity;
    }

    public void setIdentity(UUID identity) {
        this.identity = identity;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getbFile() {
        return bFile;
    }

    public void setbFile(byte[] bFile) {
        this.bFile = bFile;
    }

    public List<XmlComponent> getListXmlComponent() {
        return listXmlComponent;
    }

    public void setListXmlComponent(List<XmlComponent> listXmlComponent) {
        this.listXmlComponent = listXmlComponent;
    }

    
    @Override
    public String toString() {
        return  name;
    }
    
}
