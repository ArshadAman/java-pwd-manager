import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    public LoginScreen() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Password Manager - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Gradient background
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, new Color(236, 233, 252), w, h, new Color(220, 248, 250)));
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
            }
        };
        background.setLayout(new GridBagLayout());

        // Glassmorphic panel
        JPanel loginPanel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Drop shadow effect
                g2d.setColor(new Color(255, 255, 255, 120));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2d.dispose();
            }
        };
        loginPanel.setPreferredSize(new Dimension(360, 360));
        loginPanel.setOpaque(false);

        // Title
        JLabel title = new JLabel("Welcome Back 👋");
        title.setFont(new Font("Poppins", Font.BOLD, 28));
        title.setForeground(new Color(60, 60, 60));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Inputs and button
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Sign In");
        signupButton = new JButton("Sign Up");

        usernameField.putClientProperty("JTextField.placeholderText", "Username");
        passwordField.putClientProperty("JTextField.placeholderText", "Password");

        styleField(usernameField);
        styleField(passwordField);
        styleButton(loginButton);
//        styleButton(signupButton);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        loginPanel.add(title, gbc);
        gbc.gridy++;
        loginPanel.add(usernameField, gbc);
        gbc.gridy++;
        loginPanel.add(passwordField, gbc);
        gbc.gridy++;
        loginPanel.add(loginButton, gbc);
        gbc.gridy++;
        loginPanel.add(signupButton, gbc);

        background.add(loginPanel);
        add(background, BorderLayout.CENTER);

        // Login action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            DatabaseManager db = new DatabaseManager();
            boolean isValid = db.validateLogin(username, password);
            if (isValid) {
                new Dashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signupButton.addActionListener(e -> {
            new SignupScreen();
        });

        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setOpaque(false);
        field.setBackground(new Color(255, 255, 255, 160));
        field.setForeground(new Color(50, 50, 50));
        field.setCaretColor(Color.DARK_GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setFont(new Font("Poppins", Font.PLAIN, 15));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Poppins", Font.BOLD, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // Avoid ugly borders
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
