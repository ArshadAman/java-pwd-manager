import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    JPanel listPanel;

    // Call to refresh entries from database and UI
    public void reloadEntries() {
        entries.clear();
        DatabaseManager db = new DatabaseManager();
        try {
            java.sql.ResultSet rs = db.getAllPasswords();
            while (rs != null && rs.next()) {
                String service = rs.getString("service");
                String username = rs.getString("username");
                String password = rs.getString("password");
                entries.add(new Entry(service, username, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshList();
    }

    static class Entry {
        String service, username, password;
        boolean isVisible;

        Entry(String service, String username, String password) {
            this.service = service;
            this.username = username;
            this.password = password;
            this.isVisible = false;
        }
    }

    ArrayList<Entry> entries = new ArrayList<>();

    Dashboard() {
        setTitle("Password Manager - Dashboard");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch entries from database
        DatabaseManager db = new DatabaseManager();
        try {
            java.sql.ResultSet rs = db.getAllPasswords();
            while (rs != null && rs.next()) {
                String service = rs.getString("service");
                String username = rs.getString("username");
                String password = rs.getString("password");
                entries.add(new Entry(service, username, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add Password Button
        JButton addPasswordButton = new JButton("Add Password");
        addPasswordButton.setBounds(20, 10, 150, 30);
        addPasswordButton.addActionListener(e -> {
            new AddPassword();
        });
        JButton logout = new JButton("Logout");
        logout.setBounds(40, 10, 150, 30);
        logout.addActionListener(e->{
            dispose();
            new LoginScreen();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addPasswordButton);
        buttonPanel.add(logout);
        add(buttonPanel, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);

        refreshList();

        setVisible(true);
    }

    void refreshList() {
        listPanel.removeAll();

        for (Entry entry : entries) {
            JPanel row = new JPanel(null);
            row.setPreferredSize(new Dimension(750, 35));
            row.setMaximumSize(new Dimension(2000, 35));
            JLabel serviceLabel = new JLabel("Service: " + entry.service);
            serviceLabel.setBounds(10, 5, 150, 30);
            row.add(serviceLabel);

            JLabel userLabel = new JLabel("Username: " + entry.username);
            userLabel.setBounds(170, 5, 200, 30);
            row.add(userLabel);

            JLabel passLabel = new JLabel("Password: " + (entry.isVisible ? entry.password : maskPassword(entry.password)));
            passLabel.setBounds(380, 5, 200, 30);
            row.add(passLabel);

            JButton toggleBtn = new JButton(entry.isVisible ? "Hide" : "Show");
            toggleBtn.setBounds(590, 5, 70, 30);
            row.add(toggleBtn);

            JButton editBtn = new JButton("Edit");
            editBtn.setBounds(670, 5, 60, 30);
            row.add(editBtn);

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setBounds(740, 5, 80, 30);
            row.add(deleteBtn);

            toggleBtn.addActionListener(e -> {
                entry.isVisible = !entry.isVisible;
                refreshList();
            });

            editBtn.addActionListener(e -> {
                String newService = JOptionPane.showInputDialog("Edit Service", entry.service);
                String newUsername = JOptionPane.showInputDialog("Edit Username", entry.username);
                String newPassword = JOptionPane.showInputDialog("Edit Password", entry.password);
                if (newService != null && newUsername != null && newPassword != null) {
                    entry.service = newService;
                    entry.username = newUsername;
                    entry.password = newPassword;
                    refreshList();
                }
            });

            deleteBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Delete this entry?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DatabaseManager db = new DatabaseManager();
                    db.deletePassword(entry.service);
                    entries.remove(entry);
                    refreshList();
                }
            });

            listPanel.add(row);
            listPanel.add(Box.createVerticalStrut(4));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private String maskPassword(String password) {
        return "•".repeat(password.length());
    }

    // Placeholder for original password retrieval logic
    private String getOriginalPassword(int row) {
        return "realpassword";
    }
}
