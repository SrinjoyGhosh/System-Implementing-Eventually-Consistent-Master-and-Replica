/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;

/**
 *
 * @author Avisri Nandi
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Avisri
 */
public class change_arraylist implements Serializable {

    int operation;
    String filename;

    public change_arraylist(int operation, String filename) {
        this.operation = operation;
        this.filename = filename;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
