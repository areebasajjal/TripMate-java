package backend;

import java.io.Serializable;

public class places implements Serializable{

    private String places_names;
    private budget bg;


    public places(String places_names) {
        this.places_names = places_names;
    }

    public String getPlaces_names() {
        return this.places_names;
    }

    public void setPlaces_names(String places_names) {
        this.places_names = places_names;
    }

    public budget getBg() {
        return this.bg;
    }

    public void setBg(budget bg) {
        this.bg = bg;
    }

    @Override
    public String toString() {
        return getPlaces_names();
    }
    
}
