import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class AddPassword extends JFrame {
    JLabel lbl;
    JButton save;
    JTextField service_name, service_username, service_pass;

    AddPassword() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Password Manager - Add Password");
        setAlwaysOnTop(true);
        setSize(720, 480);
        setLocationRelativeTo(null);  // Center the window

        // Label
        lbl = new JLabel("Add a New Password");
        lbl.setFont(new Font("Poppins", Font.BOLD, 24));
        lbl.setBounds(240, 30, 300, 30);
        add(lbl);

        // Service Name Field
        service_name = new JTextField();
        service_name.putClientProperty("JTextField.placeholderText", "Service Name");
        service_name.setBounds(200, 100, 300, 40);
        styleField(service_name);
        add(service_name);

        // Username Field
        service_username = new JTextField();
        service_username.putClientProperty("JTextField.placeholderText", "Username");
        service_username.setBounds(200, 160, 300, 40);
        styleField(service_username);
        add(service_username);

        // Password Field
        service_pass = new JTextField();
        service_pass.putClientProperty("JTextField.placeholderText", "Password");
        service_pass.setBounds(200, 220, 300, 40);
        styleField(service_pass);
        add(service_pass);

        // Save Button
        save = new JButton("Save Password");
        save.setBounds(250, 300, 200, 45);
        styleButton(save);
        add(save);

        // Action for Save Button
        save.addActionListener(e -> {
            String service = service_name.getText();
            String username = service_username.getText();
            String password = service_pass.getText();

            if (!service.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                // Here you'd save it to a file or database
                JOptionPane.showMessageDialog(this, "Password Saved Successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
                // Optionally clear fields
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    // Styling for fields
    private void styleField(JTextField field) {
        field.setFont(new Font("Poppins", Font.PLAIN, 14));
        field.setForeground(Color.DARK_GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    // Styling for buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String args[]) {
        new AddPassword();
    }
}
