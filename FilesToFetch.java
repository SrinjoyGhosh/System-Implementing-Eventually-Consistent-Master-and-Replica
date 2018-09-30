/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class FilesToFetch implements Serializable{
   
    String filename;

    public FilesToFetch(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
}
