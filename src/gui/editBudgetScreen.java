package gui;

import javax.swing.JOptionPane;
import backend.*;

public class editBudgetScreen extends AddBudgetUI {
    public editBudgetScreen(Trips trip) {
        super(trip);
        prefillFields();
    }

      @Override
    public void prefillFields() {

        // TOTAL BUDGET
        if (trip.getBg() != null) {
           totalBudgetField.setText(String.valueOf(trip.getBg().getAmount()));
        }

        // HOTELS
        for (int i = 0; i < hotelFields.size(); i++) {
            budget bg = trip.getHotels_to_visit().get(i).getBg();
            if (bg != null) {
                hotelFields.get(i).setText(String.format("%.2f", bg.getAmount()));
            }
        }

        // PLACES
        for (int i = 0; i < placeFields.size(); i++) {
            budget bg = trip.getPlaces_to_visit().get(i).getBg();
            if (bg != null) {
                placeFields.get(i).setText(String.format("%.2f", bg.getAmount()));
            }
        }

        // RESTAURANTS
        for (int i = 0; i < restaurantFields.size(); i++) {
            budget bg = trip.getRestaurants_to_visit().get(i).getBg();
            if (bg != null) {
                restaurantFields.get(i).setText(String.format("%.2f", bg.getAmount()));
            }
        }

        // ACTIVITIES
        for (int i = 0; i < activityFields.size(); i++) {
            budget bg = trip.getActivities_to_do().get(i).getBg();
            if (bg != null) {
                activityFields.get(i).setText(String.format("%.2f", bg.getAmount())
                );
            }
        }
    }

     @Override
    public void saveBudget() {
        try {
            double totalBudget = Double.parseDouble(
                totalBudgetField.getText().trim()
            );

            double allocated =
                    sum(hotelFields) +
                    sum(placeFields) +
                    sum(restaurantFields) +
                    sum(activityFields);

            if (allocated > totalBudget) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Allocated budget exceeds total trip budget.",
                        "Budget Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // ─── TOTAL TRIP BUDGET ─────────────────────────────
            if (trip.getBg() == null) {
                trip.setBg(new budget(totalBudget));
            } else {
                trip.getBg().setAmount(totalBudget);
            }

            // ─── HOTELS ───────────────────────────────────────
            for (int i = 0; i < hotelFields.size(); i++) {
                double value = parse(hotelFields.get(i));
                if (trip.getHotels_to_visit().get(i).getBg() == null) {
                    trip.getHotels_to_visit().get(i).setBg(new budget(value));
                } else {
                    trip.getHotels_to_visit().get(i).getBg().setAmount(value);
                }
            }

            // ─── PLACES ───────────────────────────────────────
            for (int i = 0; i < placeFields.size(); i++) {
                double value = parse(placeFields.get(i));
                if (trip.getPlaces_to_visit().get(i).getBg() == null) {
                    trip.getPlaces_to_visit().get(i).setBg(new budget(value));
                } else {
                    trip.getPlaces_to_visit().get(i).getBg().setAmount(value);
                }
            }

            // ─── RESTAURANTS ──────────────────────────────────
            for (int i = 0; i < restaurantFields.size(); i++) {
                double value = parse(restaurantFields.get(i));
                if (trip.getRestaurants_to_visit().get(i).getBg() == null) {
                    trip.getRestaurants_to_visit().get(i).setBg(new budget(value));
                } else {
                    trip.getRestaurants_to_visit().get(i).getBg().setAmount(value);
                }
            }

            // ─── ACTIVITIES ───────────────────────────────────
            for (int i = 0; i < activityFields.size(); i++) {
                double value = parse(activityFields.get(i));
                if (trip.getActivities_to_do().get(i).getBg() == null) {
                    trip.getActivities_to_do().get(i).setBg(new budget(value));
                } else {
                    trip.getActivities_to_do().get(i).getBg().setAmount(value);
                }
            }

            TripFileManager.updateTrip(trip);

            JOptionPane.showMessageDialog(
                    frame,
                    "Budget updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            frame.dispose();
            new budgetUI().launchBudgetPage();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter valid numeric values only.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}




