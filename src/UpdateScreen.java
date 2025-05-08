import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField serviceField;
    private JButton saveButton;
    private JButton cancelButton;
    DatabaseManager db = new DatabaseManager();
    public UpdateScreen(String serviceName, String username, String password) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Update Service Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background Panel with smooth gradient
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(236, 233, 252), 0, height, new Color(220, 248, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        background.setLayout(new BorderLayout());

        // Glassmorphic Panel
        JPanel glassPanel = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                g2d.setColor(Color.white);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels and Fields
        JLabel serviceLabel = new JLabel("Service Name:");
        serviceLabel.setFont(new Font("Poppins", Font.PLAIN, 14));

        serviceField = new JTextField(serviceName);
        serviceField.setFont(new Font("Poppins", Font.PLAIN, 14));
        serviceField.setEditable(false);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));

        usernameField = new JTextField(username);
        usernameField.setFont(new Font("Poppins", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 14));

        passwordField = new JPasswordField(password);
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));

        // Buttons
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 123, 255));
        saveButton.setForeground(Color.white);
        saveButton.setFont(new Font("Poppins", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.white);
        cancelButton.setFont(new Font("Poppins", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        // Layout configuration
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components to glassPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        glassPanel.add(serviceLabel, gbc);

        gbc.gridx = 1;
        glassPanel.add(serviceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        glassPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        glassPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        glassPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        glassPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        glassPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        glassPanel.add(cancelButton, gbc);

        background.add(glassPanel, BorderLayout.CENTER);
        add(background);

        // Action Listeners
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String updatedUsername = usernameField.getText();
                String updatedPassword = new String(passwordField.getPassword());
                // You can implement saving logic here (e.g., updating the database or model)
                boolean success = db.updatePassword(serviceName, updatedUsername, updatedPassword);
                if(success) {
                    JOptionPane.showMessageDialog(UpdateScreen.this, "Service details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(UpdateScreen.this, "Failed to update service details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the update screen without saving
            }
        });

        setVisible(true);
    }
}