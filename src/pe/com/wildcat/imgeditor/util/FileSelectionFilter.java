/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author cmontes
 */
public class FileSelectionFilter extends FileFilter{
     @Override
     public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            return extension.equals(Utils.xml);
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Archivos XML's";
    }

    
}
