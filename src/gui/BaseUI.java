package gui;

import javax.swing.*;
import java.awt.*;
public abstract class BaseUI {

    protected JFrame frame;
    protected JPanel sidebar;
    protected JPanel mainContent;

    protected boolean sidebarVisible = true;
    protected final int sidebarWidth = 200;

    // TripMate theme colors
    protected final Color TEAL = new Color(0, 130, 140);
    protected final Color TEAL_LIGHT = new Color(220, 245, 245);
    protected final Color BACKGROUND = new Color(245, 248, 248);
    protected final Color TEXT_DARK = new Color(50, 50, 50);

    // Launches a base layout used by all TripMate screens
    public void launchBasePage(String windowTitle, String pageTitle) {
        //  MAIN FRAME 
        frame = new JFrame(windowTitle);
        frame.setSize(1500, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // MAIN CONTENT 
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BACKGROUND);
        frame.add(mainContent, BorderLayout.CENTER);

        // ================= HEADER =================
        JLabel toggleLabel = new JLabel("☰  " + pageTitle);
        toggleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        toggleLabel.setForeground(TEXT_DARK);
        toggleLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        toggleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TEAL));
        headerPanel.add(toggleLabel, BorderLayout.NORTH);

        mainContent.add(headerPanel, BorderLayout.NORTH);

        // ================= SIDEBAR =================
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(sidebarWidth, frame.getHeight()));
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, TEAL));

        // App name
        JLabel appName = new JLabel("TripMate");
        appName.setFont(new Font("SansSerif", Font.BOLD, 22));
        appName.setForeground(TEAL);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        sidebar.add(appName);

        // Navigation buttons
        sidebar.add(createSidebarButton("Dashboard"));
        sidebar.add(createSidebarButton("My Trips"));
        sidebar.add(createSidebarButton("Budget"));
        sidebar.add(createSidebarButton("Images"));


        frame.add(sidebar, BorderLayout.WEST);

        // // Sidebar toggle click
        // toggleLabel.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         animateSidebar();
        //     }
        // });

        // Load page-specific UI
        loadPage();

        frame.setVisible(true);
    }

    // Each screen implements its own content here
    public abstract void loadPage(); // abstract method 

    //  SIDEBAR BUTTON 
    protected JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(TEXT_DARK);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setContentAreaFilled(false);

        btn.addActionListener(e -> handleSidebarNavigation(text));
        return btn;
    }

//     // SIDEBAR ANIMATION 
//   public void animateSidebar() {

//     // This will store the final width the sidebar should reach
//     int targetWidth;

//     // Decide whether we are opening or closing the sidebar
//     // If it's currently visible → we want to hide it (width = 0)
//     // If it's hidden → we want to show it (width = sidebarWidth)
//     if (sidebarVisible) {
//         targetWidth = 0;
//     } else {
//         targetWidth = sidebarWidth;
//     }

//     // Swing Timer is used to create a smooth animation effect
//     // It triggers an event every 10 milliseconds
//     Timer timer = new Timer(10, null);

//     // Add an action listener that runs on every timer tick
//     timer.addActionListener(new ActionListener() {

//         @Override
//         public void actionPerformed(ActionEvent e) {

//             // Get the sidebar's current width
//             int currentWidth = sidebar.getWidth();

//             int step;

//             // Decide how much to change the width each time
//             // If sidebar is visible → reduce width (slide out)
//             // If sidebar is hidden → increase width (slide in)
//             if (sidebarVisible) {
//                 step = -10;
//             } else {
//                 step = 10;
//             }

//             // Calculate the new width after applying the step
//             int newWidth = currentWidth + step;

//             // Check if the animation has reached or passed the target width
//             if ((sidebarVisible && newWidth <= targetWidth) ||
//                 (!sidebarVisible && newWidth >= targetWidth)) {

//                 // Snap to the exact target width
//                 newWidth = targetWidth;

//                 // Toggle sidebar visibility state
//                 sidebarVisible = !sidebarVisible;

//                 // Stop the timer once animation is complete
//                 timer.stop();
//             }

//             // Apply the new width to the sidebar
//             sidebar.setPreferredSize(new Dimension(newWidth, sidebar.getHeight()));

//             // Revalidate the layout so Swing updates the UI
//             sidebar.revalidate();
//         }
//     });

//     // Start the animation
//     timer.start();
// }
    //  NAVIGATION 
    public void handleSidebarNavigation(String page) {
        frame.dispose();

        switch (page) {
            case "Dashboard":
              new DashboardUI().launchDashboardPage();
                break;

            case "My Trips":
               new MyTripsUI().launchMyTripsPage();
                break;

            case "Budget":
                new budgetUI().launchBudgetPage();
                break;

            case "Images":
                new imagesUI().launchImagesPage();
                break;

        }
    }
}

