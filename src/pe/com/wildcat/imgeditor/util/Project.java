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
public class Project {
    //private UUID identity;
    private String name;
    private String parent;
    private List<ImgComponent> images;
    //private int number
    
    public Project(){
        //identity = UUID.randomUUID();
        name= "Untitled1";
        images = new ArrayList<ImgComponent>();
    }
    
    public Project(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImgComponent> getImages() {
        return images;
    }

    public void setImages(List<ImgComponent> images) {
        this.images = images;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
