package backend;

import java.io.Serializable;

public class restaurants implements Serializable{
    private String res_names;
    private budget bg;

    public restaurants(String res_names) {
        this.res_names = res_names;
    }


    public String getRes_names() {
        return this.res_names;
    }

    public void setRes_names(String res_names) {
        this.res_names = res_names;
    }

    public budget getBg() {
        return this.bg;
    }

    public void setBg(budget bg) {
        this.bg = bg;
    }


    @Override
    public String toString() {
        return getRes_names();
    }


    
}
