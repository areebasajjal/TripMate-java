package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import backend.*;

public class createTripUI {
    protected JFrame frame;
    protected Trips trip;

    protected final Color TEAL = new Color(0, 130, 140);
    protected final Color TEAL_LIGHT = new Color(220, 245, 245);


    protected ArrayList<JTextField> staticFields = new ArrayList<>();
    protected ArrayList<ArrayList<JTextField>> dynamicFields = new ArrayList<>();

    protected ArrayList<JPanel> dynamicPanels = new ArrayList<>();


    public void launchCreateTripUI() {
        frame = new JFrame("TripMate | Create Trip");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel (will contain everything else inside the screen)
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        mainpanel.setBackground(TEAL_LIGHT);
        mainpanel.setBorder(new EmptyBorder(15, 20, 20, 20));

        JPanel titlepanel = new JPanel();
        titlepanel.setLayout(new BorderLayout());
        titlepanel.setBackground(TEAL_LIGHT);
        titlepanel.setBorder(new EmptyBorder(15, 20, 20, 20));

        // Title
        JLabel title = new JLabel("C R E A T E  T R I P", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEAL);
        titlepanel.add(title, BorderLayout.CENTER);
        mainpanel.add(titlepanel, BorderLayout.NORTH);

        // Center panel on top of main panel with two columns
        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(1, 2, 40, 20));
        centerpanel.setOpaque(false);

        // Left Column inside the center panel : Trip Info + Activities
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setOpaque(false);

        String[] fields = { "Trip Name", "Destination", "Start Date", "End Date" }; // creating an array of labels
                                                                                    // (strings)

        // calling the method create card defined by the me (the programmer )
        leftColumn.add(createstaticCard("Trip Info", fields));

        leftColumn.add(Box.createVerticalStrut(20));

        leftColumn.add(createDynamicCard("Activities"));

        // Right Column inside the center panel: Places, Hotels, Restaurants
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setOpaque(false);

        rightColumn.add(createDynamicCard("Places to Visit"));
        rightColumn.add(Box.createVerticalStrut(20));

        rightColumn.add(createDynamicCard("Hotels"));
        rightColumn.add(Box.createVerticalStrut(20));

        rightColumn.add(createDynamicCard("Restaurants"));

        // adding left column to the center panel
        centerpanel.add(leftColumn);
        // adding right column to the center panel
        centerpanel.add(rightColumn);

        // adding center panel to the center of the main panel
        mainpanel.add(centerpanel, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setBackground(TEAL_LIGHT);

        JButton createBtn = createButton("Save Trip");
        JButton cancelBtn = createButton("Cancel");
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);

        mainpanel.add(buttonPanel, BorderLayout.SOUTH);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainpanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane.setBorder(null);

        frame.add(scrollPane);

        prefillFields();
        frame.setVisible(true);

        cancelBtn.addActionListener(e -> frame.dispose());

        createBtn.addActionListener(e -> 
          onSubmit() );

    } // method ends

    // Creating a static card for Trip Info
    public JPanel createstaticCard(String title, String[] fields) {

        // creating a panel to put the label and fields in
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 2),
                new EmptyBorder(15, 15, 15, 15)));

        form.setBackground(Color.WHITE);

        // creating the panel for our main title
        JPanel t_header = new JPanel(new BorderLayout());
        t_header.setOpaque(false);

        // creating the label to go on top of t_panel
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TEAL);

        // adding the label on the west side of our t_header panel
        t_header.add(label, BorderLayout.WEST);
        // adding t_header to our form
        form.add(t_header);
        // creating vertical spacing below the t_header panel inside the form
        form.add(Box.createVerticalStrut(10));

        for (String f : fields) { // looping through each content of the array

            // creating the panel for our sub titles
            JPanel f_header = new JPanel(new BorderLayout());
            f_header.setOpaque(false);

            JLabel fieldLabel = new JLabel(f);
            fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            f_header.add(fieldLabel, BorderLayout.WEST);

            form.add(f_header); // adding the fields header to our form

            JTextField textfield = new JTextField(); // creates a rectangular box for typing
            textfield.setMaximumSize(new Dimension(4000, 30));
            textfield.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textfield.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEAL, 1),
                    new EmptyBorder(5, 10, 5, 10)));

            staticFields.add(textfield); // adding static text fields to the arraylist
            form.add(textfield); // adding the text fields to the form
            form.add(Box.createVerticalStrut(10)); // creating vertical spacing again
        } // loop ends

        return form;
    }

    // Create dynamic list card (Activities, Hotels, etc.) (dynamic because
    // textfields are gonna increase when clicked on + button)
    // textfields are gonna decrease when clicked on - button)
    private JPanel createDynamicCard(String title) {

        // creating a form panel
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 2),
                new EmptyBorder(15, 15, 15, 15)));

        form.setBackground(Color.WHITE);

        // panel for title Header
        JPanel t_header = new JPanel(new BorderLayout());
        t_header.setOpaque(false);

        // title label ( goes on top of the panel t_header )
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TEAL);

        t_header.add(label, BorderLayout.WEST); // adding the label in the west side on top of the panel t_header

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0)); // right side of the panel
        btnPanel.setOpaque(false);
        JButton addBtn = createButton("+"); // calling custom methods for buttons
        JButton removeBtn = createButton("-");

        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);

        t_header.add(btnPanel, BorderLayout.EAST); // adding button panel to the t_header panel

        form.add(t_header); // adding t_header to the form
        form.add(Box.createVerticalStrut(10)); // creating a vertical space below the title and the buttons

        // Items Panel
        JPanel itemsPanel = new JPanel(); // will contain our contents of array list of text field
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setOpaque(false);
        dynamicPanels.add(itemsPanel);

        ArrayList<JTextField> itemslist = new ArrayList<>(); // array list for the textfields
        dynamicFields.add(itemslist);

       JTextField firstbox = new JTextField(); // creating the first text field
        firstbox.setMaximumSize(new Dimension(4000, 30));
        firstbox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        firstbox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(TEAL, 1),
                new EmptyBorder(5, 10, 5, 10)));

       itemslist.add(firstbox); // adding the first textbox to our arraylist
       itemsPanel.add(firstbox); // adding the first text box field to the items panel

        form.add(itemsPanel); // adding the items panel to the form

        // add (+) button
        addBtn.addActionListener(e -> {
            JTextField tf = new JTextField();
            tf.setMaximumSize(new Dimension(4000, 30));
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEAL, 1),
                    new EmptyBorder(5, 10, 5, 10)));
            itemslist.add(tf);
            itemsPanel.add(tf);
            itemsPanel.revalidate();
            itemsPanel.repaint();
        });

        // remove (-) button
        removeBtn.addActionListener(e -> {
            if (itemslist.size() > 1) {
                JTextField tf = itemslist.remove(itemslist.size() - 1);
                itemsPanel.remove(tf);
                itemsPanel.revalidate();
                itemsPanel.repaint();
            }
        });

        return form;
    }


    // Buttons
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(TEAL);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    // helper validation method for trip info
    public boolean staticFieldsValidation() {

        String tripName = staticFields.get(0).getText().trim();
        String destination = staticFields.get(1).getText().trim();
        String startDate = staticFields.get(2).getText().trim();
        String endDate = staticFields.get(3).getText().trim();

        if (tripName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Trip Name cannot be empty");
            return false;
        } else if (destination.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Destination cannot be empty");
            return false;
        } else if (startDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Start Date cannot be empty");
            return false;
        } else if (endDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "End Date cannot be empty");
            return false;
        }

        if (!startDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(frame, "Start Date must be in format dd/MM/yyyy");
            return false;
        }

        if (!endDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(frame, "End Date must be in format dd/MM/yyyy");
            return false;
        }

        if (isValidDate(startDate, "Start Date") && isValidDate(endDate, "End Date")) {
            if (isStartBeforeEnd(startDate, endDate)) {
                System.out.println("Dates are valid!");
            } else {
                return false;
            }
            // return true;

        }
        return true;
    }

    public boolean dynamicFieldsValidation() {

        // Loop through each dynamic card
        for (int i = 0; i < dynamicFields.size(); i++) {

            ArrayList<JTextField> fieldList = dynamicFields.get(i);

            // Loop through each text field inside that card
            for (int j = 0; j < fieldList.size(); j++) {

                String text = fieldList.get(j).getText().trim();

                // If empty → error
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Dynamic fields cannot be empty");
                    return false;
                }
            }
        }

        return true; // all dynamic fields are filled
    }

    // validation for start and enddates
    public boolean isValidDate(String date, String fieldName) {

        // Split the date by /
        String[] parts = date.split("/");

        // If user didn't enter 3 parts (dd mm yyyy)
        if (parts.length != 3) {
            JOptionPane.showMessageDialog(frame, fieldName + " must be in format dd/MM/yyyy");
            return false;
        }

        int day;
        int month;
        int year;

        // 3. Convert string to numbers safely
        try {
            day = Integer.parseInt(parts[0]); // dd
            month = Integer.parseInt(parts[1]);// mm
            year = Integer.parseInt(parts[2]); // yyyy
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, fieldName + " must contain numbers only");
            return false;
        }

        // 4. Month check
        if (month < 1 || month > 12) {
            JOptionPane.showMessageDialog(frame, fieldName + ": Month must be between January  and December");
            return false;
        }

        // 5. Day check
        if (day < 1) {
            JOptionPane.showMessageDialog(frame, fieldName + ": Enter a valid date");
            return false;
        }

        // February (month 2)
        if (month == 2) {
            if (day > 29) {
                JOptionPane.showMessageDialog(frame, fieldName + ": Enter a valid day for february");
                return false;
            }
        }
        // Months with 30 days
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                JOptionPane.showMessageDialog(frame, fieldName + ": Month has only 30 days");
                return false;
            }
        }
        // Months with 31 days
        else {
            if (day > 31) {
                JOptionPane.showMessageDialog(frame, fieldName + ": This month has only 31 days");
                return false;
            }
        }

        if (year < 0000 && (year > 0000 && year < 2000)) {
            JOptionPane.showMessageDialog(frame, fieldName + ": Enter a valid year after 2000");
            return false;
        }

        return true; // date is valid
    }

    public boolean isStartBeforeEnd(String startDate, String endDate) {

        try {
            // Split start and end dates
            String[] startParts = startDate.split("/");
            String[] endParts = endDate.split("/");

            int startDay = Integer.parseInt(startParts[0]);
            int startMonth = Integer.parseInt(startParts[1]);
            int startYear = Integer.parseInt(startParts[2]);

            int endDay = Integer.parseInt(endParts[0]);
            int endMonth = Integer.parseInt(endParts[1]);
            int endYear = Integer.parseInt(endParts[2]);

            // Compare years first
            if (startYear > endYear) {
                JOptionPane.showMessageDialog(frame, "Start date must be before End date");
                return false;
            } else if (startYear < endYear) {
                return true; // start is before end
            }

            // Years are equal, compare months
            if (startMonth > endMonth) {
                JOptionPane.showMessageDialog(frame, "Start date must be before End date");
                return false;
            } else if (startMonth < endMonth) {
                return true;
            }

            // Years and months equal, compare days
            if (startDay > endDay) {
                JOptionPane.showMessageDialog(frame, "Start date must be before End date");
                return false;
            }

            // startDay <= endDay
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Dates must contain numbers only");
            return false;
        }
    }

// returns an array list of activities
    public ArrayList<activities> getActivities(ArrayList<JTextField> fields, ArrayList<activities> existingList) {

        ArrayList<activities> updated = new ArrayList<>(); // create a new array list

        for (JTextField box : fields) { // for each loop for each tex field
            String name = box.getText().trim(); // get the activity name one by one

            if (name.isEmpty()) 
                 continue; // if empty, move out the method/

                 activities existing = findActivityByName(existingList, name); // calling manual method 

                  if (existing != null) { // if existing object is not equal to null 
                       updated.add(existing); // add it to the updated array list (not repeated )
            } else {
                    updated.add(new activities(name)); // new activity object added 
            }
        }
        return updated;
    }

    public activities findActivityByName(ArrayList<activities> list, String name) {
        for (activities a : list) {
            if (a.getActivity().equalsIgnoreCase(name)) 
                return a;
        }
        return null;
    }

// PLACES 
    public ArrayList<places> getPlaces(ArrayList<JTextField> fields, ArrayList<places> existingList) {
        ArrayList<places> updated = new ArrayList<>();
        for (JTextField box : fields) {
            String name = box.getText().trim();
            if (name.isEmpty()) continue;

            places existing = findPlaceByName(existingList, name);
            if (existing != null) {
                updated.add(existing); //keep budget
            } else {
                updated.add(new places(name)); // new place
            }
        }
        return updated;
    }
        public places findPlaceByName(ArrayList<places> list, String name) {
            for (places p : list) {
            if (p.getPlaces_names().equalsIgnoreCase(name)) 
                return p;
        }
        return null;
    }

    // hotels
    public ArrayList<hotels> getHotels(ArrayList<JTextField> fields, ArrayList<hotels> existingList) {
        ArrayList<hotels> updated = new ArrayList<>();
        for (JTextField box : fields) {
            String name = box.getText().trim();
            if (name.isEmpty()) continue;

            hotels existing = findHotelByName(existingList, name);
            if (existing != null) {
                updated.add(existing); // keep budget
            } else {
                updated.add(new hotels(name)); // new hotel
            }
        }
        return updated;
    }

    public hotels findHotelByName(ArrayList<hotels> list, String name) {
        for (hotels h : list) {
            if (h.getHotels_names().equalsIgnoreCase(name)) 
                return h;
        }
        return null;
    }

//  RESTAURANTS 
    public ArrayList<restaurants> getRestaurants(ArrayList<JTextField> fields, ArrayList<restaurants> existingList) {
        ArrayList<restaurants> updated = new ArrayList<>();
        for (JTextField box : fields) {
            String name = box.getText().trim();
            if (name.isEmpty()) continue;

            restaurants existing = findRestaurantByName(existingList, name);
            if (existing != null) {
                updated.add(existing); // keep budget
            } else {
                updated.add(new restaurants(name)); // new restaurant
            }
        }
        return updated;
    }

    public restaurants findRestaurantByName(ArrayList<restaurants> list, String name) {
        for (restaurants r : list) {
            if (r.getRes_names().equalsIgnoreCase(name)) 
                return r;
        }
        return null;
    }

    public void prefillFields() {
    // intentionally empty
    // child class is going to override it
}

public void onSubmit() {
    if (staticFieldsValidation() && dynamicFieldsValidation()) {

        // Static fields
        String name = staticFields.get(0).getText().trim();
        String destination = staticFields.get(1).getText().trim();
        String startDate = staticFields.get(2).getText().trim();
        String endDate = staticFields.get(3).getText().trim();

        // Dynamic fields — use empty lists for a new trip
        ArrayList<activities> activities = getActivities(dynamicFields.get(0), new ArrayList<>());
        ArrayList<places> places = getPlaces(dynamicFields.get(1), new ArrayList<>());
        ArrayList<hotels> hotels = getHotels(dynamicFields.get(2), new ArrayList<>());
        ArrayList<restaurants> restaurants = getRestaurants(dynamicFields.get(3), new ArrayList<>());

        // Create Trips object
        Trips trip = new Trips(
                name,
                destination,
                startDate,
                endDate,
                places,
                hotels,
                restaurants,
                activities
        );

        // Save to backend
        TripFileManager.saveTrip(trip);

        JOptionPane.showMessageDialog(frame, "Trip saved successfully!");
        frame.dispose();
        new MyTripsUI().launchMyTripsPage();
    }
}


public <T> void fillDynamicCard(int index, ArrayList<T> data) {
    JPanel panel = dynamicPanels.get(index);
    ArrayList<JTextField> fields = dynamicFields.get(index);

    // Remove all existing components
    panel.removeAll();
    fields.clear();

    // Add text fields for each item
    for (T item : data) {
        JTextField tf = new JTextField(item.toString());
        tf.setMaximumSize(new Dimension(4000, 30));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 1),
                new EmptyBorder(5, 10, 5, 10)));

        panel.add(tf);
        fields.add(tf);
    }


    panel.revalidate();
    panel.repaint();
}


}  