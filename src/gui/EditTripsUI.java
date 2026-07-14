package gui;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import backend.*;

public class EditTripsUI extends createTripUI {

    private Trips trip;

    public EditTripsUI(Trips trip) {
        this.trip = trip;
        launchCreateTripUI();
    }

    @Override
    public void prefillFields() {
        staticFields.get(0).setText(trip.getName());
        staticFields.get(1).setText(trip.getDestination());
        staticFields.get(2).setText(trip.getStart_date());
        staticFields.get(3).setText(trip.getEnd_date());

        fillDynamicCard(0, trip.getActivities_to_do());
        fillDynamicCard(1, trip.getPlaces_to_visit());
        fillDynamicCard(2, trip.getHotels_to_visit());
        fillDynamicCard(3, trip.getRestaurants_to_visit());
    }

@Override
public void onSubmit() {
    // Static fields
    trip.setName(staticFields.get(0).getText().trim());
    trip.setDestination(staticFields.get(1).getText().trim());
    trip.setStart_date(staticFields.get(2).getText().trim());
    trip.setEnd_date(staticFields.get(3).getText().trim());

    // Dynamic fields
    trip.setHotels_to_visit(getHotels(dynamicFields.get(2), trip.getHotels_to_visit()));
    trip.setRestaurants_to_visit(getRestaurants(dynamicFields.get(3), trip.getRestaurants_to_visit()));
    trip.setPlaces_to_visit(getPlaces(dynamicFields.get(1), trip.getPlaces_to_visit()));
    trip.setActivities_to_do(getActivities(dynamicFields.get(0), trip.getActivities_to_do()));


    // Update backend
    ArrayList<Trips> trips = TripFileManager.loadTrips();
    TripFileManager.editTrip(trip, trips);

    JOptionPane.showMessageDialog(frame, "Trip updated successfully!");
    frame.dispose();
    new MyTripsUI().launchMyTripsPage();
}

}
