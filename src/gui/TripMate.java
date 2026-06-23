package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TripMate {

    public void launchfirstScreen() {

        
        JFrame mainframe = new JFrame("TripMate");
        mainframe.setSize(1280, 720);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(true);

        
        JPanel mainPanel = new GradientPanel(new Color(0, 130, 140), Color.WHITE);
        mainPanel.setLayout(new BorderLayout());


        mainPanel.setLayout(new BorderLayout());
        mainframe.add(mainPanel);

        
        JLabel titleLabel = new JLabel("T r i p M a t e", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 90));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(90, 0, 0, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        
        RoundedPanel bottomPanel = new RoundedPanel(70);
        bottomPanel.setLayout(new GridBagLayout());
       // bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30, 30,50, 50));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        //gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        
        JLabel headline = new JLabel("<html><center>Ready to explore<br>beyond boundaries?</center></html>", SwingConstants.CENTER);
        headline.setFont(new Font("SansSerif", Font.BOLD, 28));
        headline.setForeground(new Color(0, 130, 140));
        bottomPanel.add(headline, gbc);

       
        gbc.gridy++;
        RoundedButton startButton = new RoundedButton("Your Journey Starts Here!");
        startButton.setPreferredSize(new Dimension(320, 60));

        startButton.addActionListener(e -> {
            new Login().createLoginScreen();
    });


        bottomPanel.add(startButton, gbc);

        mainframe.setVisible(true);
    }

    
    class GradientPanel extends JPanel {
    private Color top;
    private Color bottom;
    public GradientPanel(Color top, Color bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, top, 0, getHeight(), bottom));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}

    
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }

    
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setBackground(new Color(0, 130, 140));
            setContentAreaFilled(false);
            setBorderPainted(false);

            // Hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(0, 160, 180));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(new Color(0, 130, 140));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
            super.paintComponent(g);
        }
    }
}


