package backend;

import java.io.Serializable;

public class activities implements Serializable{
    
    private String activity;
    private budget bg;

    public activities(String activity) {
        this.activity = activity;
    }
    

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public budget getBg() {
        return this.bg;
    }

    public void setBg(budget bg) {
        this.bg = bg;
    }

    @Override
    public String toString() {
        return getActivity();

}
}
