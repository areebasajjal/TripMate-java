package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Trips implements Serializable {

    private static final long serialVersionUID = 1L;

    // Core trip info
    private String tripId;
    private String name;
    private String destination;
    private String start_date;
    private String end_date;
    private String status = "Upcoming";

    // Budget
    private budget totalBG;

    // Associations
    private ArrayList<places> places_to_visit = new ArrayList<>();
    private ArrayList<hotels> hotels_to_visit = new ArrayList<>();
    private ArrayList<restaurants> restaurants_to_visit = new ArrayList<>();
    private ArrayList<activities> activities_to_do = new ArrayList<>();

    // Images (file names only)
    private ArrayList<String> imageNames = new ArrayList<>();

    // Constructor
    public Trips(
            String name,
            String destination,
            String start_date,
            String end_date,
            ArrayList<places> places_to_visit,
            ArrayList<hotels> hotels_to_visit,
            ArrayList<restaurants> restaurants_to_visit,
            ArrayList<activities> activities_to_do) {

        this.tripId = "trip_" + System.currentTimeMillis(); // unique ID
        this.name = name;
        this.destination = destination;
        this.start_date = start_date;
        this.end_date = end_date;
        this.places_to_visit = places_to_visit;
        this.hotels_to_visit = hotels_to_visit;
        this.restaurants_to_visit = restaurants_to_visit;
        this.activities_to_do = activities_to_do;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public budget getBg() {
        return totalBG;
    }

    public void setBg(budget bg) {
        this.totalBG = bg;
    }

    public ArrayList<places> getPlaces_to_visit() {
        return places_to_visit;
    }

    public void setPlaces_to_visit(ArrayList<places> places_to_visit) {
        this.places_to_visit = places_to_visit;
    }

    public ArrayList<hotels> getHotels_to_visit() {
        return hotels_to_visit;
    }

    public void setHotels_to_visit(ArrayList<hotels> hotels_to_visit) {
        this.hotels_to_visit = hotels_to_visit;
    }

    public ArrayList<restaurants> getRestaurants_to_visit() {
        return restaurants_to_visit;
    }

    public void setRestaurants_to_visit(ArrayList<restaurants> restaurants_to_visit) {
        this.restaurants_to_visit = restaurants_to_visit;
    }

    public ArrayList<activities> getActivities_to_do() {
        return activities_to_do;
    }

    public void setActivities_to_do(ArrayList<activities> activities_to_do) {
        this.activities_to_do = activities_to_do;
    }

    public ArrayList<String> getImageNames() {
        return imageNames;
    }

    public void addImage(String imageName) {
        imageNames.add(imageName);
    }

    public void removeImage(String imageName) {
        imageNames.remove(imageName);
    }

    @Override
    public String toString() {
        return "Trips{" +
                "tripId='" + tripId + '\'' +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status='" + status + '\'' +
                ", images=" + imageNames +
                '}';
    }
}
