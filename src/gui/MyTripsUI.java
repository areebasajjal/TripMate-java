package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;

import backend.*;

public class MyTripsUI extends BaseUI {

    private JPanel selectedCard = null;      // currently selected card
    private Trips selectedTrip = null;       // corresponding trip object of the selected card 
    private JPanel tripsListPanel;


    public void launchMyTripsPage() { 
       launchBasePage("TripMate | My Trips", "My Trips");
    }

    @Override
public void loadPage() {

    // Main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(BACKGROUND);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 10));

    // Top panel
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
    topPanel.setBackground(BACKGROUND);
    topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    // search field 
    JTextField searchField = new JTextField("Search trips...");
    searchField.setBackground(TEAL_LIGHT);
    searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEAL),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));

    topPanel.add(searchField); // adding search field to the top panel
    topPanel.add(Box.createVerticalStrut(15));

    // Button panel
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 0));
    buttonPanel.setBackground(BACKGROUND);
    buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

    // creating the buttons now
    JButton searchBtn = new JButton("Search Trip");
    JButton createBtn = new JButton("Create Trip");
    JButton deleteBtn = new JButton("Delete Trip");
    JButton editBtn = new JButton("Edit Trip");
    JButton viewAllBtn = new JButton("View All Trips");
    
    // styling the buttons via a method style button
    styleButton(searchBtn);
    styleButton(createBtn);
    styleButton(deleteBtn);
    styleButton(editBtn);
    styleButton(viewAllBtn);

    // 1. [action listener for create trip]
    createBtn.addActionListener(e -> {frame.dispose();
    new createTripUI().launchCreateTripUI();
});

    // 2. [action listener for delete trip]
    deleteBtn.addActionListener(e -> {

        if (selectedCard != null && selectedTrip != null) {
            TripFileManager.deleteTrip(selectedTrip);      // delete from backend
            tripsListPanel.remove(selectedCard);           // remove wrapper (card) from front end 
            tripsListPanel.revalidate();
            tripsListPanel.repaint();

            selectedCard = null; /*  detting the selected card and selected trip back to null */
            selectedTrip = null;
        } else {
            JOptionPane.showMessageDialog(frame, "Select a trip to delete first!");
        }
    });

    // 3. [action listener for search button]
    searchBtn.addActionListener(e -> { 
        // Get the search text and convert to lowercase for case-insensitive comparison
        String searchedText = searchField.getText().toLowerCase().trim();  

        // Clear the existing trip cards in the list panel
        tripsListPanel.removeAll();

        // Load trips again from the backend 
        ArrayList<Trips> trips = TripFileManager.loadTrips();

    for (Trips trip : trips) {
        // Check if the trip name contains the search query
        if (trip.getName().toLowerCase().contains(searchedText)) {

            // Wrapper panel for card + spacing
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setOpaque(false);

            JPanel card = createTripCard(trip); // inner card
            wrapper.add(card);
            wrapper.add(Box.createVerticalStrut(10)); // spacing

            tripsListPanel.add(wrapper);
        }}
       // Refresh the panel to show only the filtered trips
        tripsListPanel.revalidate();
        tripsListPanel.repaint();
      });


    // 4. [action listener for view all trips button]
    viewAllBtn.addActionListener(e -> {
    // Clear the existing trip cards in the list panel
    tripsListPanel.removeAll();

    // Load all trips again from the backend
    ArrayList<Trips> trips = TripFileManager.loadTrips();

    // Add all trips back to the tripsListPanel
    for (Trips trip : trips) {

        // Wrapper panel for card + spacing
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        // Create the trip card
        JPanel card = createTripCard(trip); // inner card
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(10)); // spacing

        // Add the wrapper to the trips list panel
        tripsListPanel.add(wrapper);
    }

    // Refresh the panel to show all trips
    tripsListPanel.revalidate();
    tripsListPanel.repaint();
});

    // 5. action lisener for  edit button
        editBtn.addActionListener(e -> {
    if (selectedTrip != null) {
        frame.dispose();
        new EditTripsUI(selectedTrip);
    } else {
        JOptionPane.showMessageDialog(frame, "Please select a trip to edit first!");
    }
});       

    // adding thr buttons to the button panel
    buttonPanel.add(searchBtn);
    buttonPanel.add(createBtn);
    buttonPanel.add(deleteBtn);
    buttonPanel.add(editBtn);
    buttonPanel.add(viewAllBtn);
    topPanel.add(buttonPanel);

    // Trips list
    tripsListPanel = new JPanel();
    tripsListPanel.setLayout(new BoxLayout(tripsListPanel, BoxLayout.Y_AXIS));
    tripsListPanel.setBackground(BACKGROUND);

    // [code bit that shows all our trips on screen as the user creates them]
    // Load trips
    ArrayList<Trips> trips = TripFileManager.loadTrips();

    for (Trips trip : trips) {

        // Wrapper panel for card + spacing
       JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        JPanel card = createTripCard(trip); // inner card
        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(10)); // spacing

        // Click listener on card, selecting wrapper
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {


                // Deselect previous
                if (selectedCard != null) {
                    JPanel prevCard = (JPanel) selectedCard.getComponent(0);
                    prevCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(TEAL, 1),
                            BorderFactory.createEmptyBorder(10, 15, 20, 15)
                    ));
                }
                selectedCard = wrapper; // store wrapper
                selectedTrip = trip;

                // Highlight selected card
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 2),
                        BorderFactory.createEmptyBorder(10, 15, 20, 15)
                ));
            }
        });

        tripsListPanel.add(wrapper);
    }

    JScrollPane scrollPane = new JScrollPane(tripsListPanel);
    scrollPane.setBorder(null);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainContent.add(mainPanel);
}

// Simple createTripCard method
public JPanel createTripCard(Trips trip) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(TEAL_LIGHT);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEAL, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
    card.setPreferredSize(new Dimension(0, 90));

    // Center panel to hold texts vertically
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(TEAL_LIGHT);
    centerPanel.setLayout(new GridLayout(2, 1)); // 2 rows: title and date

    JLabel title = new JLabel(trip.getName(), SwingConstants.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    title.setForeground(TEAL);

    JLabel date = new JLabel(trip.getStart_date() + " - " + trip.getEnd_date(), SwingConstants.CENTER);
    date.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    date.setForeground(Color.DARK_GRAY);

    centerPanel.add(title);
    centerPanel.add(date);

    // Panel to hold the "View Details" button (Right-aligned)
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Right-aligned
    rightPanel.setOpaque(false); 

    JButton detailsButton = new JButton("View Details");
    detailsButton.setFont(new Font("Arial", Font.BOLD, 12));
    detailsButton.setBackground(TEAL);
    detailsButton.setForeground(TEAL_LIGHT);
    detailsButton.setFocusPainted(false);
    detailsButton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    
    detailsButton.addActionListener(e -> {
        // Show trip details when button is clicked
        showTripDetailsDialog(trip);  
    });

    rightPanel.add(detailsButton);

    card.add(centerPanel, BorderLayout.WEST);
    card.add(rightPanel, BorderLayout.EAST);
    return card;
}
        
    public void styleButton(JButton button) {  // created a method for the same styling of the 3 main buttons of the screen 
        button.setBackground(TEAL);            // to avoid redundancy of code
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    // show trip details method
    public void showTripDetailsDialog(Trips trip) {

    JDialog dialog = new JDialog(frame, "Trip Details", true); // creates a dialogue box ontop of our frame 
    dialog.setSize(600, 600);
    dialog.setLocationRelativeTo(frame); // set its location relative to our main frame 
    dialog.setLayout(new BorderLayout());
 
    // panel for our static field text
    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBorder(new EmptyBorder(15, 20, 15, 20));
    content.setBackground(Color.WHITE);

    JLabel label = new JLabel("T R I P     I N F O R M A T I O N" );
    label.setFont(new Font("Segoe UI", Font.BOLD, 24));
    label.setForeground(TEAL);
    label.setBorder(new EmptyBorder(5, 0, 5, 0));
    content.add(label); // putting the label on top of our content 

    content.add(infoRow("Trip Name: ", trip.getName()));
    content.add(infoRow("Destination: ", trip.getDestination()));
    content.add(infoRow("Start Date: ", trip.getStart_date()));
    content.add(infoRow("End Date: ", trip.getEnd_date()));

    // Activities 
    content.add(Box.createVerticalStrut(15));
    content.add(sectionTitle("A C T I V I T I E S"));
    for (activities a : trip.getActivities_to_do()) {
        content.add(bulletItem(a.getActivity()));
    }

    // Places 
    content.add(Box.createVerticalStrut(15));
    content.add(sectionTitle("P L A C E S"));
    for (places p : trip.getPlaces_to_visit()) {
        content.add(bulletItem(p.getPlaces_names()));
    }

    // Hotels 
    content.add(Box.createVerticalStrut(15));
    content.add(sectionTitle("H O T E L S"));
    for (hotels h : trip.getHotels_to_visit()) {
        content.add(bulletItem(h.getHotels_names()));
    }

    //Restaurants 
    content.add(Box.createVerticalStrut(15));
    content.add(sectionTitle("R E S T A U R A N T S"));
    for (restaurants r : trip.getRestaurants_to_visit()) {
        content.add(bulletItem(r.getRes_names()));
    }

    JScrollPane scrollPane = new JScrollPane(content);
    scrollPane.setBorder(null);

    JButton closeBtn = new JButton("C L O S E");
    closeBtn.setPreferredSize(new Dimension(500, 30));
    closeBtn.setBackground(TEAL);
    closeBtn.setForeground(Color.WHITE);
    closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
    closeBtn.setFocusPainted(false);
    closeBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    closeBtn.addActionListener(e -> dialog.dispose());

    JPanel bottom = new JPanel();
    bottom.setBackground(Color.WHITE);
    bottom.add(closeBtn);

    dialog.add(scrollPane, BorderLayout.CENTER);
    dialog.add(bottom, BorderLayout.SOUTH);

    dialog.setVisible(true);
}

// helper method
public JLabel sectionTitle(String text) {
    JPanel labelPanel = new JPanel(new BorderLayout());
    JLabel label = new JLabel(text);
    label.setFont(new Font("Segoe UI", Font.BOLD, 18));
    label.setForeground(TEAL);
    label.setBorder(new EmptyBorder(5, 0, 5, 0));
    labelPanel.add(label, BorderLayout.EAST);
    return label;
}
// helper method
public JPanel infoRow(String text, String value) {

    JPanel row = new JPanel(new BorderLayout());
    row.setOpaque(false);

    JLabel t = new JLabel(text);
    t.setFont(new Font("SansSerif", Font.BOLD, 16));

    JLabel v = new JLabel(value);
    v.setFont(new Font("SansSerif", Font.BOLD, 16));
    v.setForeground(TEAL);

    row.add(t, BorderLayout.WEST);
    row.add(v, BorderLayout.CENTER);
    return row;
}
// helper method 
public JLabel bulletItem(String text) {
    JLabel label = new JLabel("• " + text);
    label.setFont(new Font("Segoe UI", Font.ITALIC, 19));
    return label;
}
 
}
