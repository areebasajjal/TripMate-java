package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import backend.TripFileManager;
import backend.Trips;

public class AddImagesScreen {

    private Trips trip;
    private JFrame frame;

    // Folder to store images
    public static final String IMAGE_FOLDER = "resources/images";

    // TripMate theme colors
    private final Color TEAL = new Color(0, 130, 140);
    private final Color TEAL_LIGHT = new Color(220, 245, 245);
    private final Color BACKGROUND = new Color(245, 248, 248);
    private final Color TEXT_DARK = new Color(50, 50, 50);

    private JLabel infoLabel; // Shows uploaded images info

    public AddImagesScreen(Trips trip) {
        this.trip = trip;
        launchAddImagesPage();
    }

    public void launchAddImagesPage() {
        ensureImageFolderExists(); // Making sure folder exists

        // FRAME 
        frame = new JFrame("Add Trip Memories");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // MAIN PANEL 
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        // TOP PANEL 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND);

        // title label
        JLabel title = new JLabel("ADD TRIP MEMORIES");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(TEAL);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // subtitle label
        JLabel subtitle = new JLabel("Upload photos for: " + trip.getName());
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // adding everything to top panel
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(subtitle);
        topPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // CENTER CARD PANEL 
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(40, 30, 40, 30)
        ));

        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(infoLabel);
        card.add(Box.createVerticalStrut(30));

        // BUTTON PANEL 
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);

        JButton uploadBtn = pillButton("Upload Images");
        JButton backBtn = pillButton("Back");

        // Upload button action
        uploadBtn.addActionListener(e -> uploadImages());

        // Back button action
        backBtn.addActionListener(e -> {
            frame.dispose(); // close current frame
            new imagesUI().launchImagesPage();
        });

        btnPanel.add(uploadBtn);
        btnPanel.add(backBtn);

        card.add(btnPanel);
        mainPanel.add(card, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // button styling method
    public JButton pillButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(TEAL);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        return btn;
    }

    // UPLOAD IMAGES METHOD 
    public void uploadImages() {
    // Creates a file chooser dialog so the user can select files
    JFileChooser fileChooser = new JFileChooser();  
    // Allows selecting multiple files at once
     fileChooser.setMultiSelectionEnabled(true);

    // Sets a filter so only image files are visible in the dialog
    fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        @Override
        public boolean accept(File f) {
            // Convert file name to lowercase for easier comparison
            String name = f.getName().toLowerCase();
            // Accept folders and files ending with .jpg, .jpeg, or .png
            return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg") 
                || name.endsWith(".png");
        }

        @Override
        public String getDescription() {
            // Description shown in the file chooser filter dropdown
            return "Image Files (*.jpg, *.jpeg, *.png)";
        }
    });

    // Show the open dialog and get the user’s choice
    int option = fileChooser.showOpenDialog(frame);

    // Check if the user clicked the "Open" button
    if (option == JFileChooser.APPROVE_OPTION) {
        // Get the files the user selected
        File[] files = fileChooser.getSelectedFiles();

        // Loop through each selected file
        for (File file : files) {
            // Save the image to a folder and get the new file name
            String savedFileName = saveImageToFolder(file);
            // If saving was successful, add the image to the trip object
            if (savedFileName != null) {
                trip.addImage(savedFileName);
            }
        }

        // Update the info label to tell the user that images were uploaded
        infoLabel.setText(" Image Uploaded!");
        infoLabel.revalidate(); // Refresh the label layout
        infoLabel.repaint();    // Redraw the label

        // Save any changes made to the trip
        TripFileManager.updateTrip(trip);
    }
}

// METHOD TO SAVE AN IMAGE TO A SPECIFIC FOLDER
public String saveImageToFolder(File selectedFile) {
    try {
        // Create a new file name using the current time to avoid duplicates
        String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
        // Define the target path where the image will be saved
        Path target = Path.of(IMAGE_FOLDER, newFileName);
        // Copy the file from the source to the target location
        Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        // Return the new file name so it can be tracked
        return newFileName;
    } catch (Exception e) {
        // Show a popup message to the user about the failure
        JOptionPane.showMessageDialog(frame, "Failed to save image: " + selectedFile.getName());
        return null; // Return null to indicate failure
    }
}

// METHOD TO ENSURE THE IMAGE FOLDER EXISTS
public static void ensureImageFolderExists() {
    // Create a File object for the folder path
    File folder = new File(IMAGE_FOLDER);
    // If the folder does not exist
    if (!folder.exists()) {
        // Create the folder (including any missing parent directories)
        folder.mkdirs();
    }
}
}
