package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import backend.TripFileManager;
import backend.Trips;

public class imagesUI extends BaseUI {

    private JPanel tripsListPanel;
    private JPanel selectedCard;
    private Trips selectedTrip;

    private JTextField searchField;

    public void launchImagesPage() {
        launchBasePage("TripMate | Images", "Images");
    }

    @Override
    public void loadPage() {

       // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("TRIP MEMORIES");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(TEAL);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Upload and manage your travel photos");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitle);
        topPanel.add(Box.createVerticalStrut(20));

       // search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(BACKGROUND);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JButton searchBtn = pillButton("Search");
        JButton viewAllBtn = pillButton("View All");

        searchBtn.addActionListener(e -> searchTrips());
        viewAllBtn.addActionListener(e -> loadTrips());

        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchBtn);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(viewAllBtn);

        topPanel.add(searchPanel);
        topPanel.add(Box.createVerticalStrut(15));

        // button panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actionPanel.setBackground(BACKGROUND);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addImagesBtn = pillButton("Add Images");

        addImagesBtn.addActionListener(e -> {
            if (selectedTrip == null) {
                JOptionPane.showMessageDialog(frame, "Please select a trip first!");
                return;
            }
            frame.dispose();
            new AddImagesScreen(selectedTrip);
        });

        actionPanel.add(addImagesBtn);
        topPanel.add(actionPanel);
        topPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(topPanel, BorderLayout.NORTH);

       // trip list panel
        tripsListPanel = new JPanel();
        tripsListPanel.setLayout(new BoxLayout(tripsListPanel, BoxLayout.Y_AXIS));
        tripsListPanel.setBackground(BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(tripsListPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainContent.add(mainPanel);

        loadTrips();
    }

    // loading all trips fro the memory
    public void loadTrips() {
        tripsListPanel.removeAll();

        ArrayList<Trips> trips = TripFileManager.loadTrips();

        if (trips.isEmpty()) {
            JLabel empty = new JLabel("No trips found. Create a trip to add memories.");
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);

            tripsListPanel.add(Box.createVerticalStrut(50));
            tripsListPanel.add(empty);
        } else {
            for (Trips trip : trips) {
                tripsListPanel.add(createTripCard(trip));
                tripsListPanel.add(Box.createVerticalStrut(15));
            }
        }

        tripsListPanel.revalidate();
        tripsListPanel.repaint();
    }

    // searching trips
    public void searchTrips() {
        String query = searchField.getText().trim().toLowerCase();
        tripsListPanel.removeAll();

        ArrayList<Trips> trips = TripFileManager.loadTrips();
        boolean found = false;

        for (Trips trip : trips) {
            if (trip.getName().toLowerCase().contains(query)
                    || trip.getDestination().toLowerCase().contains(query)) {

                tripsListPanel.add(createTripCard(trip));
                tripsListPanel.add(Box.createVerticalStrut(15));
                found = true;
            }
        }

        if (!found) {
            JLabel noResult = new JLabel("No matching trips found.");
            noResult.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noResult.setForeground(Color.GRAY);
            noResult.setAlignmentX(Component.CENTER_ALIGNMENT);

            tripsListPanel.add(Box.createVerticalStrut(40));
            tripsListPanel.add(noResult);
        }

        tripsListPanel.revalidate();
        tripsListPanel.repaint();
    }

   // creating trip cards
    public JPanel createTripCard(Trips trip) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));

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
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(dates);

        JButton viewBtn = new JButton("View Memories");
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewBtn.setBackground(TEAL);
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);
        viewBtn.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));

        viewBtn.addActionListener(e -> new viewImagesScreen(trip));


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(viewBtn);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(btnPanel, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (selectedCard != null) {
                    selectedCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(230, 230, 230)),
                            BorderFactory.createEmptyBorder(18, 22, 18, 22)
                    ));
                }

                selectedCard = card;
                selectedTrip = trip;

                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(TEAL, 2),
                        BorderFactory.createEmptyBorder(18, 22, 18, 22)
                ));
            }
        });

        return card;
    }

   // button styling
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

