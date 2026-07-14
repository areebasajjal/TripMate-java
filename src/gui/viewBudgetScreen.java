package gui;

import backend.Trips;
import backend.activities;
import backend.hotels;
import backend.places;
import backend.restaurants;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class viewBudgetScreen {

    private JFrame frame;
    private Trips trip;
    private final Color TEAL = new Color(0, 130, 140);

    public viewBudgetScreen(Trips trip) {
        this.trip = trip;
        launchViewBudget();
    }

    public void launchViewBudget() {
        // main frame
        frame = new JFrame("View Trip Budget");
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel title = new JLabel("Budget Overview — " + trip.getName());
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 130, 140));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(title, BorderLayout.NORTH); // adding title in the north of main panel

        // Content panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        // Add budgets per item bigger box
        content.add(createItemSection("Hotels", trip.getHotels_to_visit()));
        content.add(createItemSection("Places", trip.getPlaces_to_visit()));
        content.add(createItemSection("Restaurants", trip.getRestaurants_to_visit()));
        content.add(createItemSection("Activities", trip.getActivities_to_do()));

        // Budget summary
        content.add(Box.createVerticalStrut(20));
        // calc total budget 
        double totalBudget = 0.0;
        if (trip.getBg() != null) {
            totalBudget = trip.getBg().getAmount();
        } else {
            totalBudget = 0.0;
        }
        double allocatedBudget = calculateAllocatedBudget();
        double remainingBudget = totalBudget - allocatedBudget;

        content.add(createSummaryRow("Total Trip Budget", totalBudget));
        content.add(createSummaryRow("Allocated Budget", allocatedBudget));
        content.add(createSummaryRow("Remaining Budget", remainingBudget));

        // Wrap content in a scroll pane
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        // Footer
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setBackground(new Color(0, 130, 140));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> frame.dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        footer.add(closeBtn);
        mainPanel.add(footer, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Creating a BIG BOX PANEL for each category (Hotels, Places, etc.)
    public <T> JPanel createItemSection(String title, ArrayList<T> items) { 
        JPanel section = new JPanel(); 
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS)); 
        section.setBackground(Color.WHITE); 
        section.setBorder(BorderFactory.createCompoundBorder( new LineBorder(TEAL, 1), 
        BorderFactory.createEmptyBorder(5, 10, 10, 10) )); 
        
        JPanel headerRow = new JPanel(new BorderLayout()); 
        headerRow.setBackground(Color.WHITE); 
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); 
        
        JLabel header = new JLabel(title); 
        header.setFont(new Font("Segoe UI", Font.BOLD, 18)); 
        header.setForeground(TEAL); header.setBorder(new EmptyBorder(10, 0, 5, 0)); 
        headerRow.add(header, BorderLayout.WEST);
        section.add(headerRow);

        section.add(Box.createVerticalStrut(8));

    // Items
    for (T item : items) {
        double value = 0.0;

        if (item instanceof hotels) {
            hotels h = (hotels) item;
            value = h.getBg() != null ? h.getBg().getAmount() : 0.0;
        } else if (item instanceof places ) {
            places p= (places) item;
            value = p.getBg() != null ? p.getBg().getAmount() : 0.0;
        } else if (item instanceof restaurants) {
            restaurants r = (restaurants) item;
            value = r.getBg() != null ? r.getBg().getAmount() : 0.0;
        } else if (item instanceof activities) {
            activities a = (activities) item;
            value = a.getBg() != null ? a.getBg().getAmount() : 0.0;
        }

        section.add(createItemRow(item.toString(), value));
        section.add(Box.createVerticalStrut(5));
    }

    section.add(Box.createVerticalStrut(10));
    return section;
}
    // Create a single row for an item
    public JPanel createItemRow(String name, double amount) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(245, 245, 245));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel valueLabel = new JLabel(String.format("$%.2f", amount));
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(nameLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);

        return row;
    }

    // Create summary row for total/allocated/remaining budget
    public JPanel createSummaryRow(String label, double amount) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(220, 220, 220));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel valueText = new JLabel(String.format("$%.2f", amount));
        valueText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        valueText.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(labelText, BorderLayout.WEST);
        row.add(valueText, BorderLayout.EAST);

        return row;
    }

    // Calculating the total allocated budget
    public double calculateAllocatedBudget() {
        double total = 0.0;

        for (hotels hotel : trip.getHotels_to_visit()) {
            total += hotel.getBg() != null ? hotel.getBg().getAmount() : 0.0;
        }
        for (places place : trip.getPlaces_to_visit()) {
            total += place.getBg() != null ? place.getBg().getAmount() : 0.0;
        }
        for (restaurants restaurant : trip.getRestaurants_to_visit()) {
            total += restaurant.getBg() != null ? restaurant.getBg().getAmount() : 0.0;
        }
        for (activities activity : trip.getActivities_to_do()) {
            total += activity.getBg() != null ? activity.getBg().getAmount() : 0.0;
        }

        return total;
    }
}
