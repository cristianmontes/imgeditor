/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cmontes
 */
public class XmlComponent {
    private int orden;
    private int type;
    private List<Pixel> listPixel = new ArrayList<Pixel>();

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Pixel> getListPixel() {
        return listPixel;
    }

    public void setListPixel(List<Pixel> listPixel) {
        this.listPixel = listPixel;
    }
    
}
