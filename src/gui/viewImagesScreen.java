package gui;

import backend.Trips;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class viewImagesScreen {

    private Trips trip;
    public static final String IMAGE_FOLDER = "resources/images";

    // Theme colors
    private final Color TEAL = new Color(0, 130, 140);
    private final Color BACKGROUND = new Color(245, 248, 248);
    private final Color LIGHT_GRAY = new Color(230, 230, 230);

    public viewImagesScreen(Trips trip) {
        this.trip = trip;
        launchViewImagesPage();
    }

    public void launchViewImagesPage() {
        JFrame frame = new JFrame("View Trip Photos");
        frame.setSize(1000, 680);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top panel: title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("Photos for: " + trip.getName());
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEAL);
        topPanel.add(title, BorderLayout.WEST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel: grid of images
        int columns = 4; // thumbnails per row
        JPanel imagesPanel = new JPanel(new GridLayout(0, columns, 15, 15)); // 0 rows = dynamic
        imagesPanel.setBackground(BACKGROUND);
        imagesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add thumbnails
        for (String fileName : trip.getImageNames()) {
            File imgFile = new File(IMAGE_FOLDER, fileName); // parent and child
            if (imgFile.exists()) {
                // a wrapper arounf the image thats given a path, a url or direct image
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath()); 
                // on the wrapper you upload the image
                Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

                JLabel imgLabel = new JLabel(new ImageIcon(img));
                imgLabel.setToolTipText(fileName); // displays the file name when you hover over the image
                imgLabel.setHorizontalAlignment(JLabel.CENTER);
                imgLabel.setVerticalAlignment(JLabel.CENTER);
                imgLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
                imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                // Hover effect
                imgLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        imgLabel.setBorder(new LineBorder(TEAL, 2, true));
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        imgLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        showEnlargedImage(imgFile, imagesPanel, imgLabel);
                    }
                });

                imagesPanel.add(imgLabel);
            }
        }

        // Scroll pane for vertical scrolling
        JScrollPane scrollPane = new JScrollPane(imagesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BACKGROUND);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(TEAL);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        backBtn.addActionListener(e -> frame.dispose());

        bottomPanel.add(backBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Enlarged image modal with Delete / Back options
    public void showEnlargedImage(File imgFile, JPanel imagesPanel, JLabel imgLabel) {
        JDialog dialog = new JDialog();
        dialog.setModal(true); // user cant interact with the main screen while dialogu is open
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 150));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(TEAL, 4, true));

        // Dynamically resize image to fit screen (max 70% of screen)
        // Toolkit is a class in java.awt that provides access to system resources, like the screen, clipboard, fonts, etc.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxSize = (int) (Math.min(screenSize.width, screenSize.height) * 0.7);
        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image img = icon.getImage().getScaledInstance(maxSize, maxSize, Image.SCALE_SMOOTH);
        JLabel imgLabelLarge = new JLabel(new ImageIcon(img));
        imgLabelLarge.setHorizontalAlignment(JLabel.CENTER);
        imgLabelLarge.setVerticalAlignment(JLabel.CENTER);

        panel.add(imgLabelLarge, BorderLayout.CENTER);
        dialog.add(panel);

        // Click on enlarged image -> options
        imgLabelLarge.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String[] options = {"Delete", "Back"};
                int choice = JOptionPane.showOptionDialog(dialog,
                        "Choose an action:",
                        "Image Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[1]);

                if (choice == 0) { // Delete
                    int confirm = JOptionPane.showConfirmDialog(dialog,
                            "Are you sure you want to delete this image?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (imgFile.delete()) {
                            imagesPanel.remove(imgLabel);
                            imagesPanel.revalidate();
                            imagesPanel.repaint();
                            dialog.dispose();
                            JOptionPane.showMessageDialog(null, "Image deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete image.");
                        }
                    }
                } else { // Back
                    dialog.dispose();
                }
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null); // center on screen
        dialog.setVisible(true);
    }
}



