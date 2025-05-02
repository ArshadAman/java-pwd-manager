import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Password Manager - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background gradient
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(236, 233, 252),
                        width, height, new Color(220, 248, 250)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
                g2d.dispose();
            }
        };
        background.setLayout(new GridBagLayout());

        // Glassmorphic login panel
        JPanel loginPanel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.setColor(Color.white);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.setColor(new Color(255, 255, 255, 80));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2d.dispose();
            }
        };
        loginPanel.setPreferredSize(new Dimension(350, 350));
        loginPanel.setOpaque(false);

        // Components
        JLabel title = new JLabel("Welcome Back 👋");
        title.setFont(new Font("Poppins", Font.BOLD, 26));
        title.setForeground(new Color(70, 70, 70));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Sign In");

        usernameField.putClientProperty("JTextField.placeholderText", "Username");
        passwordField.putClientProperty("JTextField.placeholderText", "Password");

        styleField(usernameField);
        styleField(passwordField);
        styleButton(loginButton);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        loginPanel.add(title, gbc);

        gbc.gridy++;
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        loginPanel.add(passwordPanel, gbc);

        gbc.gridy++;
        loginPanel.add(loginButton, gbc);

        background.add(loginPanel);
        add(background, BorderLayout.CENTER);

        // Events
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setBackground(new Color(255, 255, 255, 180));
        field.setForeground(new Color(33, 33, 33));
        field.setCaretColor(new Color(33, 33, 33));
        field.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        field.setFont(new Font("Poppins", Font.PLAIN, 14));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(14, 25, 14, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Poppins", Font.BOLD, 16));
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}