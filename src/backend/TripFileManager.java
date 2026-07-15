package backend;

import java.io.*;
import java.util.ArrayList;

public class TripFileManager {

    private static final String FILE_NAME = "trips.ser"; // creating a file name 

    // creating an array list that saves our entire trip object 
    // (name, destination, start and end date, multiple restaurants, palces, activities and hotels)
    private static ArrayList<Trips> trips = new ArrayList<>(); 

    public static ArrayList<Trips>  loadTrips() { // reading from the file 

        File file = new File(FILE_NAME);

        // first run: file does not exist yet
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            trips = (ArrayList<Trips>) ois.readObject(); 
            
            for (Trips trip : trips) {
                if (trip.getTripId() == null) {
                    trip.setTripId("trip_" + System.currentTimeMillis());
                }
            }
            ois.close(); // closing the file
            return trips;

        } catch (IOException | ClassNotFoundException e) { // catching exception
            System.out.println("cannot load trips");
            return new ArrayList<>();
        }
    }

    // SAVE TRIP
    public static void saveTrip(Trips trip) { // writing into the file 

        loadTrips(); // load existing trips from the array list (calls the method from above )
        trips.add(trip); // saves the user entered trip to the arraylist using trips.add() method

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME)); 

            // writes the entire updated arrayList into the file
            oos.writeObject(trips);
            oos.close(); // closes the file

        } catch (IOException e) {
            System.out.println("cannot save trips"); // catches exception
        }
    }
   
// public static void deleteTrip(Trips trip) {
//     trips.remove(trip); // calling the delete method to the array list to remove our desired trip
//     try {
//         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
//         oos.writeObject(trips); // resaving the updated list back to the file ( trip object gets deleted from the backend)
//         oos.close();
//     } catch (IOException e) {
//         System.out.println("cannot delete trip");
//     }
// }

    // DELETE TRIP
    public static void deleteTrip(Trips trip) {

        ArrayList<Trips> trips = loadTrips();
        trips.removeIf(t -> t.getTripId().equals(trip.getTripId()));
        saveAll(trips);
    }

        // INTERNAL SAVE HELPER
    public static void saveAll(ArrayList<Trips> trips) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(trips);
        } catch (IOException e) {
            System.out.println("cannot save trips");
        }
    }

public static void updateTrip(Trips updatedTrip) {
    ArrayList<Trips> trips = loadTrips(); // load current trips
    boolean found = false;

    for (int i = 0; i < trips.size(); i++) {
        if (trips.get(i).getName().equals(updatedTrip.getName())) {
            trips.set(i, updatedTrip);
            found = true;
            break;
        }
    }

    if (!found) {
        trips.add(updatedTrip);
    }

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
        oos.writeObject(trips);
    } catch (IOException e) {
        System.out.println("cannot update trips");
    }
}

public static void editTrip(Trips updatedTrip, ArrayList<Trips> trips) {
    boolean found = false;

    // Loop through trips to find the one with the same name
    for (int i = 0; i < trips.size(); i++) {
        Trips t = trips.get(i);
        if (t.getDestination().trim().equalsIgnoreCase(updatedTrip.getDestination())) {
            // Replace the old trip with the updated one
            trips.set(i, updatedTrip);
            found = true;
            break;
        }
    }

    if (!found) {
        trips.add(updatedTrip);
    }

    // Save the updated list back to the file
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
        oos.writeObject(trips);
    } catch (IOException e) {
        System.out.println("Cannot update trips: " + e.getMessage());
        e.printStackTrace();
    }
}
}

