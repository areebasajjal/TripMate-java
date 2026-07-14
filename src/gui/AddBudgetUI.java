package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import backend.TripFileManager;
import backend.Trips;
import backend.budget;

public class AddBudgetUI {

    protected JFrame frame;
    protected Trips trip;

    protected JTextField totalBudgetField = new JTextField();

    protected ArrayList<JTextField> hotelFields = new ArrayList<>();
    protected ArrayList<JTextField> placeFields = new ArrayList<>();
    protected ArrayList<JTextField> restaurantFields = new ArrayList<>();
    protected ArrayList<JTextField> activityFields = new ArrayList<>();

    // color scheme
    private final Color TEAL = new Color(0, 130, 140);
    private final Color BACKGROUND = new Color(245, 245, 245);
    private final Color BORDER = new Color(220, 220, 220);

    // layout constants
    protected final Dimension LABEL_SIZE = new Dimension(280, 32);
    protected final Dimension FIELD_SIZE = new Dimension(160, 32);

    public AddBudgetUI(Trips trip) {
        this.trip = trip;
        launchAddBudget();
    }

    public void launchAddBudget() {
        // main frame
        frame = new JFrame("TripMate | Add Budget");
        frame.setSize(650, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(20, 30, 20, 30));

        // HEADER
        JLabel title = new JLabel("Trip Budget — " + trip.getName());
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEAL);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        root.add(title, BorderLayout.NORTH);

        // CONTENT
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);

        content.add(createRow("Total Trip Budget", totalBudgetField));
        content.add(Box.createVerticalStrut(30));

        addCategory(content, "Hotels", trip.getHotels_to_visit(), hotelFields);
        addCategory(content, "Places", trip.getPlaces_to_visit(), placeFields);
        addCategory(content, "Restaurants", trip.getRestaurants_to_visit(), restaurantFields);
        addCategory(content, "Activities", trip.getActivities_to_do(), activityFields);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        root.add(scroll, BorderLayout.CENTER);

        // FOOTER
        JButton saveBtn = new JButton("Save Budget");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        saveBtn.setBackground(TEAL);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(new EmptyBorder(10, 30, 10, 30));
        saveBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(e -> saveBudget());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(new EmptyBorder(10, 30, 10, 30));
        footer.setBackground(BACKGROUND);
        footer.add(saveBtn);

        root.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    public JPanel createRow(String text, JTextField field) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(BACKGROUND);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        row.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TEAL);
        label.setPreferredSize(LABEL_SIZE);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setPreferredSize(FIELD_SIZE);
        field.setBorder(BorderFactory.createLineBorder(TEAL));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.EAST);

        return row;
    }

    public <T> void addCategory(JPanel parent, String title, ArrayList<T> items, ArrayList<JTextField> fields) {

        if (items == null || items.isEmpty()) 
            return;

        JLabel header = new JLabel(title);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(TEAL);
        header.setBorder(new EmptyBorder(20, 0, 15, 0));
        
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setBackground(BACKGROUND);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        headerRow.add(header, BorderLayout.WEST);

        parent.add(headerRow);


        for (T item : items) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    new EmptyBorder(6, 12, 6, 12)
            ));

            JLabel name = new JLabel(item.toString());
            name.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            name.setPreferredSize(LABEL_SIZE);

            JTextField field = new JTextField();
            field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            field.setPreferredSize(FIELD_SIZE);
            field.setBorder(BorderFactory.createLineBorder(TEAL));

            fields.add(field);

            row.add(name, BorderLayout.WEST);
            row.add(field, BorderLayout.EAST);

            parent.add(row);
            parent.add(Box.createVerticalStrut(10));
        }
    }

    public void saveBudget() {
        try {
            double totalBudget = Double.parseDouble(totalBudgetField.getText().trim());

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

            if (trip.getBg() == null) {
                    trip.setBg(new budget(0));
                }
            trip.getBg().setAmount(totalBudget);
            
                        
            for (int i = 0; i < hotelFields.size(); i++) {
                double value = parse(hotelFields.get(i));
                trip.getHotels_to_visit().get(i).setBg(new budget(value));
            }


            for (int i = 0; i < placeFields.size(); i++) {
                double value = parse(placeFields.get(i));
                trip.getPlaces_to_visit().get(i).setBg(new budget(value));
            }


            for (int i = 0; i < restaurantFields.size(); i++) {
                double value = parse(restaurantFields.get(i));
                trip.getRestaurants_to_visit().get(i).setBg(new budget(value));
            }


            for (int i = 0; i < activityFields.size(); i++) {
                double value = parse(activityFields.get(i));
                trip.getActivities_to_do().get(i).setBg(new budget(value));
            }

            TripFileManager.updateTrip(trip);

            JOptionPane.showMessageDialog(
                    frame,
                    "Budget saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("budget added successfully");
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

    // Helper method to safely parse a JTextField into a double
    public  double parse(JTextField field) {
    String text = field.getText().trim();
    if (text.isEmpty()) return 0.0;
    return Double.parseDouble(text);
    }
    public double sum(ArrayList<JTextField> fields) {
        double total = 0;
        for (JTextField field : fields) {
            if (!field.getText().trim().isEmpty()) {
                total += Double.parseDouble(field.getText().trim());
            }
        }
        return total;
    }

    public void prefillFields() {
    // intentionally empty
    // child class is going to override it
}
}

