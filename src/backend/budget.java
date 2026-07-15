package backend;
import java.io.Serializable;

public class budget implements Serializable {
    private double amount;

    public budget(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
           return "" + amount;
    }

    
}
