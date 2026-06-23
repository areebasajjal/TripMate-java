package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Login {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private final String PASSWORD_FILE = "password.dat";
    private String currentPassword;
    private final String validUsername = "username";

    public void createLoginScreen() {
        loadPassword();

        frame = new JFrame("TripMate | Login Page ");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(0, 130, 140)); // TripMate teal
        frame.add(backgroundPanel);

       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 45, 35, 45));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(420, 340));

        
        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(0, 130, 140));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("TripMate Login");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(30));

       
        JPanel formGrid = new JPanel(new GridLayout(2, 1, 0, 18));
        formGrid.setBackground(Color.WHITE);

        
        JPanel userPanel = new JPanel(new BorderLayout(5, 6));
        userPanel.setBackground(Color.WHITE);

        
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        userLabel.setForeground(Color.GRAY);

        
        userPanel.add(userLabel, BorderLayout.NORTH);


        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 36));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

       
        userPanel.add(usernameField, BorderLayout.CENTER);

    
        JPanel passPanel = new JPanel(new BorderLayout(5, 6));
        passPanel.setBackground(Color.WHITE);

       
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        passLabel.setForeground(Color.GRAY);

       
        passPanel.add(passLabel, BorderLayout.NORTH);

        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 36));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

       
        passPanel.add(passwordField, BorderLayout.CENTER);

        
        formGrid.add(userPanel);
        formGrid.add(passPanel);

        
        mainPanel.add(formGrid);
        mainPanel.add(Box.createVerticalStrut(30));

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

     
        JButton loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 130, 140));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(320, 60));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
       
        loginButton.addActionListener(e -> handleLogin());

        
        JButton forgotButton = new JButton("Forgot Password?");
        forgotButton.setFocusPainted(false);
        forgotButton.setForeground(new Color(0, 130, 140));
        forgotButton.setContentAreaFilled(false);
        forgotButton.setBorderPainted(false);
        forgotButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotButton.setPreferredSize(new Dimension(150, 50));
        
        forgotButton.addActionListener(e -> handleForgotPassword());

       
        buttonPanel.add(loginButton, BorderLayout.WEST);
        buttonPanel.add(forgotButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel);

        backgroundPanel.add(mainPanel);

        frame.setVisible(true);
    }

    public void loadPassword() {

        try { ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PASSWORD_FILE));
            currentPassword = (String) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            currentPassword = "password"; // fallback
        }
    }

    public void savePassword(String password) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PASSWORD_FILE))) {
            oos.writeObject(password);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(validUsername) && password.equals(currentPassword)) {
            new DashboardUI().launchDashboardPage();
            frame.dispose(); // closing the screen
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleForgotPassword() {

        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        Object[] fields = {
                "New Password:", newPasswordField,
                "Confirm Password:", confirmPasswordField
        };

        int option = JOptionPane.showConfirmDialog(
                frame,
                fields,
                "Change Password",
                JOptionPane.OK_CANCEL_OPTION);

        if (option != JOptionPane.OK_OPTION)
            return;

        String newPass = new String(newPasswordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            currentPassword = newPass;
            savePassword(newPass); // OBJECT STREAM WRITE
            JOptionPane.showMessageDialog(frame, "Password successfully changed!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // // Input Field
    // public JPanel createField(String label, JComponent field) {
    // JPanel panel = new JPanel(new BorderLayout(5, 6));
    // panel.setBackground(Color.WHITE);

    // JLabel lbl = new JLabel(label);
    // lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
    // lbl.setForeground(Color.GRAY);

    // field.setPreferredSize(new Dimension(300, 36));
    // field.setFont(new Font("SansSerif", Font.PLAIN, 14));
    // field.setBorder(BorderFactory.createCompoundBorder(
    // BorderFactory.createLineBorder(new Color(200, 200, 200)),
    // BorderFactory.createEmptyBorder(6, 10, 6, 10)));

    // panel.add(lbl, BorderLayout.NORTH);
    // panel.add(field, BorderLayout.CENTER);

    // return panel;
    // }

    // class RoundedButton extends JButton {
    // public RoundedButton(String text) {
    // super(text);
    // setFocusPainted(false);
    // setForeground(Color.WHITE);
    // setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    // setBackground(new Color(0, 130, 140));
    // setAlignmentX(Component.CENTER_ALIGNMENT);
    // setContentAreaFilled(false);
    // setBorderPainted(false);

    // // Hover effect
    // addMouseListener(new MouseAdapter() {
    // @Override
    // public void mouseEntered(MouseEvent e) {
    // setBackground(new Color(0, 160, 180));
    // repaint();
    // }

    // @Override
    // public void mouseExited(MouseEvent e) {
    // setBackground(new Color(0, 130, 140));
    // repaint();
    // }
    // });
    // }

    // @Override
    // protected void paintComponent(Graphics g) {
    // Graphics2D g2 = (Graphics2D) g;
    // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    // RenderingHints.VALUE_ANTIALIAS_ON);
    // g2.setColor(getBackground());
    // g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
    // super.paintComponent(g);
    // }
    // }

    // class RoundedPanel extends JPanel {
    // private int radius;

    // public RoundedPanel(int radius) {
    // this.radius = radius;
    // setOpaque(false);
    // }

    // @Override
    // protected void paintComponent(Graphics g) {
    // Graphics2D g2 = (Graphics2D) g;
    // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    // RenderingHints.VALUE_ANTIALIAS_ON);
    // g2.setColor(Color.WHITE);
    // g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    // super.paintComponent(g);
    // }
    // }
}
