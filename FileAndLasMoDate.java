/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;

/**
 *
 * @author Srinjoy Ghosh
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
public class FileAndLasMoDate implements Serializable {

    String filename;
    long lastMoDate;

    public FileAndLasMoDate(String filename, long lastMoDate) {
        this.filename = filename;
        this.lastMoDate = lastMoDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getLastMoDate() {
        return lastMoDate;
    }

    public void setLastMoDate(long lastMoDate) {
        this.lastMoDate = lastMoDate;
    }

}
