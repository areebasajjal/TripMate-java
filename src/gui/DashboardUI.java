package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import backend.*;

public class DashboardUI extends BaseUI {

    public void launchDashboardPage() {
        launchBasePage(
                "TripMate | Dashboard",
                "Dashboard");
    }

    
    @Override
    public void loadPage() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 1. Welcome text
        JLabel welcome = new JLabel("Welcome back!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(welcome);

        mainPanel.add(Box.createVerticalStrut(20));

        // 2. Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ArrayList<Trips> trips = TripFileManager.loadTrips();

        int upcomingCount = 0;
        int ongoingCount = 0;
        int completedCount = 0;

        for (Trips t : trips) {
        String status = t.getStatus();
        if (status.equals("Upcoming")) upcomingCount++;
        else if (status.equals("Ongoing")) ongoingCount++;
        else if (status.equals("Completed")) completedCount++;
    }

        statsPanel.add(statCard("Total Trips", trips.size()));
        statsPanel.add(statCard("Upcoming Trips", upcomingCount));
        statsPanel.add(statCard("Ongoing Trips", ongoingCount));
        statsPanel.add(statCard("Completed Trips", completedCount));

        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        // 3. Trips cards
        JLabel tripsLabel = new JLabel("Your Trips");
        tripsLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tripsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(tripsLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel tripsPanel = new JPanel();
        tripsPanel.setLayout(new BoxLayout(tripsPanel, BoxLayout.Y_AXIS));
        tripsPanel.setOpaque(false);

        for (Trips trip : trips) {
            tripsPanel.add(createDashboardTripCard(trip));
            tripsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(tripsPanel);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 350));
        mainPanel.add(scrollPane);

        mainContent.add(mainPanel);
    }

    public JPanel statCard(String title, int value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(TEAL_LIGHT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(TEAL);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);

        return card;
    }

    public JPanel createDashboardTripCard(Trips trip) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(TEAL_LIGHT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Trip name
        JLabel name = new JLabel(trip.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Radio buttons
        JRadioButton upcoming = new JRadioButton("Upcoming");
        JRadioButton ongoing = new JRadioButton("Ongoing");
        JRadioButton completed = new JRadioButton("Completed");

        ButtonGroup group = new ButtonGroup();
        group.add(upcoming);
        group.add(ongoing);
        group.add(completed);

        // Set current status
        switch (trip.getStatus()) {
            case "Ongoing" -> ongoing.setSelected(true);
            case "Completed" -> completed.setSelected(true);
            default -> upcoming.setSelected(true);
        }

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false);
        radioPanel.add(upcoming);
        radioPanel.add(ongoing);
        radioPanel.add(completed);

        JButton saveBtn = new JButton("Save Status");
        styleButton(saveBtn);

        saveBtn.addActionListener(e -> {
            if (upcoming.isSelected()) trip.setStatus("Upcoming");
            else if (ongoing.isSelected()) trip.setStatus("Ongoing");
            else trip.setStatus("Completed");

            TripFileManager.updateTrip(trip);
            JOptionPane.showMessageDialog(frame, "Status updated!");
            frame.dispose();
            new DashboardUI().launchDashboardPage();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(saveBtn);

        card.add(name, BorderLayout.NORTH);
        card.add(radioPanel, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    public void styleButton(JButton btn) {
        btn.setBackground(TEAL);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }
}