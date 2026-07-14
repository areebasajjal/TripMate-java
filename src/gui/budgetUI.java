package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import backend.TripFileManager;
import backend.Trips;

public class budgetUI extends BaseUI {

    private JPanel selectedCard = null;
    private Trips selectedTrip = null;
    private JPanel tripsListPanel;

    public void launchBudgetPage() {
        launchBasePage("TripMate | Budget", "Budget");
    }

    @Override
    public void loadPage() {

        // MAIN PANEL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        // TOP PANEL 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND);

        // Title
        JLabel title = new JLabel("MANAGE YOUR BUDGET");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(TEAL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(20));

        // SEARCH PANEL 
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.X_AXIS));
        searchContainer.setBackground(BACKGROUND);
        searchContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
       
        // Search field
        JTextField searchField = new JTextField("Search trips");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchContainer.add(searchField);

        searchContainer.add(Box.createHorizontalStrut(10)); // 10px fixed space

        // Search button
        JButton searchBudgetBtn = new JButton("Search");
        searchBudgetBtn.setFocusPainted(false);
        searchBudgetBtn.setBackground(TEAL);
        searchBudgetBtn.setForeground(Color.WHITE);
        searchBudgetBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBudgetBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        searchContainer.add(searchBudgetBtn);

        // Add to top panel
        topPanel.add(searchContainer);
        topPanel.add(Box.createVerticalStrut(18));

    // search button action listener 
    searchBudgetBtn.addActionListener(e -> {
        String text = searchField.getText().trim().toLowerCase();
        tripsListPanel.removeAll(); // Clear the previous list of trips

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a search term.");
            return;
            }

        ArrayList<Trips> trips = TripFileManager.loadTrips();
        ArrayList<Trips> filteredTrips = new ArrayList<>();

        // Filtering trips by name or destination
        for (Trips trip : trips) {
            if (trip.getName().toLowerCase().contains(text) || trip.getDestination().toLowerCase().contains(text)) {
                filteredTrips.add(trip);
            }
        }

        if (filteredTrips.isEmpty()) {
            // Show message only if no trips are found
            JOptionPane.showMessageDialog(frame, "No trips found for \"" + text + "\".");
        } else {
            // Load filtered trips into the list
            for (Trips trip : filteredTrips) {
                JPanel card = createTripCard(trip);
                tripsListPanel.add(card);
                tripsListPanel.add(Box.createVerticalStrut(15));
            }
            // Refresh the panel to show only the filtered trips
            tripsListPanel.revalidate();
            tripsListPanel.repaint();
        }
    });

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actionPanel.setBackground(BACKGROUND);

        JButton addBudgetBtn = pillButton("Add Budget");
        JButton editBudgetBtn = pillButton("Edit Budget");
        JButton viewallBtn = pillButton("View All");

        actionPanel.add(addBudgetBtn);
        actionPanel.add(editBudgetBtn);
        actionPanel.add(viewallBtn);

        addBudgetBtn.addActionListener(e -> {
            if (selectedTrip != null) {
                 frame.dispose();
            new AddBudgetUI(selectedTrip);
            } else {
                 JOptionPane.showMessageDialog(frame, "Please select a trip to add budget first!");
            }});

       editBudgetBtn.addActionListener(e -> {
        if (selectedTrip != null) {
            frame.dispose();
            new editBudgetScreen(selectedTrip);
            } else {
                 JOptionPane.showMessageDialog(frame, "Please select a trip to add budget first!");
            }
       }); 

       // View All Button ActionListener
        viewallBtn.addActionListener(e -> {
              tripsListPanel.removeAll();  // Clear any previous search results
        
        // Reload all trips from the backend
        ArrayList<Trips> trips = TripFileManager.loadTrips();

        // Add each trip back to the trips list panel
        for (Trips trip : trips) {
            JPanel card = createTripCard(trip);
            tripsListPanel.add(card);
            tripsListPanel.add(Box.createVerticalStrut(15));
        }

        // Refresh the panel to show all trips
        tripsListPanel.revalidate();
        tripsListPanel.repaint();
    });


        topPanel.add(actionPanel);
        topPanel.add(Box.createVerticalStrut(20));

        //  TRIPS LIST Panel
        tripsListPanel = new JPanel();
        tripsListPanel.setLayout(new BoxLayout(tripsListPanel, BoxLayout.Y_AXIS));
        tripsListPanel.setBackground(BACKGROUND);
        tripsListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // loading trips from the backend
        ArrayList<Trips> trips = TripFileManager.loadTrips();

        for (Trips trip : trips) {
            JPanel card = createTripCard(trip);
            tripsListPanel.add(card);
            tripsListPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(tripsListPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        //  ADD TO MAIN 
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainContent.add(mainPanel);
    }

    //  TRIP CARD box
    public JPanel createTripCard(Trips trip) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Text panel (left)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel name = new JLabel(trip.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 20));
        name.setForeground(TEAL);

        JLabel dates = new JLabel(trip.getStart_date() + " – " + trip.getEnd_date());
        dates.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dates.setForeground(Color.DARK_GRAY);

        textPanel.add(name);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(dates);

        // Button panel (right)
        JButton viewBtn = new JButton("View Budget");
        viewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        viewBtn.setBackground(TEAL);
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);
        viewBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        viewBtn.addActionListener(e -> new viewBudgetScreen(trip));


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(viewBtn);

        card.add(textPanel, BorderLayout.WEST);
        card.add(btnPanel, BorderLayout.EAST);

        // Selection logic
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (selectedCard != null) {
                    selectedCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(235, 235, 235)),
                            BorderFactory.createEmptyBorder(15, 20, 15, 20)
                    ));
                }
                selectedCard = card;
                selectedTrip = trip;

                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(TEAL, 2),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }
        });

        return card;
    }

    // BUTTON STYLE 
    public JButton pillButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(TEAL);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));

        return btn;
    }
}

  


