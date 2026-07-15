package backend;

import java.io.Serializable;

public class hotels implements Serializable{

     private String hotels_names;
     private budget bg;

    public hotels(String hotels_names) {
        this.hotels_names = hotels_names;
    }


    public String getHotels_names() {
        return this.hotels_names;
    }

    public void setHotels_names(String hotels_names) {
        this.hotels_names = hotels_names;
    }
     
        public budget getBg() {
        return this.bg;
    }

    public void setBg(budget bg) {
        this.bg = bg;
    }

    @Override
    public String toString() {
        return getHotels_names();
    }


    public double getAmount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAmount'");
    }

    
}
