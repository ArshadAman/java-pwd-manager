import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class SignupScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;

    public SignupScreen() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Password Manager - Signup");
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
        JPanel signupPanel = new JPanel(new GridBagLayout()) {
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
        signupPanel.setPreferredSize(new Dimension(360, 360));
        signupPanel.setOpaque(false);

        // Title
        JLabel title = new JLabel("Register with Us 👋");
        title.setFont(new Font("Poppins", Font.BOLD, 28));
        title.setForeground(new Color(60, 60, 60));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Inputs and button
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        signupButton = new JButton("Sign Up");

        usernameField.putClientProperty("JTextField.placeholderText", "Username");
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        confirmPasswordField.putClientProperty("JTextField.placeholderText", "Confirm Password");

        styleField(usernameField);
        styleField(confirmPasswordField);
        styleField(passwordField);
        styleButton(signupButton);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        signupPanel.add(title, gbc);
        gbc.gridy++;
        signupPanel.add(usernameField, gbc);
        gbc.gridy++;
        signupPanel.add(passwordField, gbc);
        gbc.gridy++;
        signupPanel.add(confirmPasswordField, gbc);
        gbc.gridy++;
        signupPanel.add(signupButton, gbc);

        background.add(signupPanel);
        add(background, BorderLayout.CENTER);

        // Login action
        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(confirmPasswordField.getPassword());
            String c_password = new String(passwordField.getPassword());
            if (!username.isEmpty() && !password.isEmpty() && password.equals(c_password)) {
                dispose();
                new Dashboard(); // Make sure Dashboard class exists
            } else if(username.equals("admin") ) {
                JOptionPane.showMessageDialog(this, "User already exits", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(!password.equals(c_password) || (!username.isEmpty() && !password.isEmpty())){
                JOptionPane.showMessageDialog(this, "Password does't match", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
        new SignupScreen();
    }
}
