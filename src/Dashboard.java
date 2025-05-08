import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public Dashboard() {
        setTitle("Password Manager");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table model with columns
        model = new DefaultTableModel(new Object[]{"Service", "Username", "Password", "Show Password", "Edit", "Delete"}, 0);
        table = new JTable(model);

        // Set larger font for the table and headers
        Font largeFont = new Font("Arial", Font.PLAIN, 20);
        table.setFont(largeFont);
        table.setRowHeight(40); // Increase row height

        // Set larger font for the header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 22));

        // Add a sample row (optional)
        model.addRow(new Object[]{"Gmail", "john.doe", "mypassword", "Show Password", "Edit", "Delete"});

        // Custom renderer for buttons
        table.getColumn("Show Password").setCellRenderer(new ButtonCellRenderer());
        table.getColumn("Edit").setCellRenderer(new ButtonCellRenderer());
        table.getColumn("Delete").setCellRenderer(new ButtonCellRenderer());

        // Custom editor with inline logic
        table.getColumn("Show Password").setCellEditor(getInlineEditor("Show Password"));
        table.getColumn("Edit").setCellEditor(getInlineEditor("Edit"));
        table.getColumn("Delete").setCellEditor(getInlineEditor("Delete"));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Button Renderer (visual only)
    class ButtonCellRenderer extends JButton implements TableCellRenderer {
        public ButtonCellRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 18)); // Larger font for the buttons
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setForeground(Color.WHITE);
            setBackground(getText().equals("Edit") ? new Color(255, 193, 7) :
                    (getText().equals("Delete") ? new Color(220, 53, 69) : new Color(52, 152, 219)));
            return this;
        }
    }

    // Inline editor for buttons
    private DefaultCellEditor getInlineEditor(String buttonLabel) {
        JCheckBox checkBox = new JCheckBox(); // dummy editor base
        DefaultCellEditor editor = new DefaultCellEditor(checkBox) {
            JButton button = new JButton();
            String label;
            int row;

            {
                button.setOpaque(true);
                button.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font for the buttons
                button.setPreferredSize(new Dimension(150, 40)); // Increase button size
                button.addActionListener(e -> {
                    if (label.equals("Edit")) {
                        String service = model.getValueAt(row, 0).toString();
                        String username = model.getValueAt(row, 1).toString();
                        String password = model.getValueAt(row, 2).toString();

                        String newService = JOptionPane.showInputDialog(null, "Edit Service:", service);
                        String newUsername = JOptionPane.showInputDialog(null, "Edit Username:", username);
                        String newPassword = JOptionPane.showInputDialog(null, "Edit Password:", password);

                        if (newService != null && newUsername != null && newPassword != null) {
                            model.setValueAt(newService, row, 0);
                            model.setValueAt(newUsername, row, 1);
                            model.setValueAt(newPassword, row, 2);
                        }
                    } else if (label.equals("Delete")) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Delete this entry?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            model.removeRow(row);
                        }
                    } else if (label.equals("Show Password")) {
                        String password = model.getValueAt(row, 2).toString();
                        String currentText = button.getText();

                        if (currentText.equals("Show Password")) {
                            button.setText("Hide Password");
                            model.setValueAt(password, row, 2); // Reveal the password
                        } else {
                            button.setText("Show Password");
                            model.setValueAt("*****", row, 2); // Mask the password
                        }
                    }

                    fireEditingStopped();
                });
            }

            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                label = value.toString();
                this.row = row;
                button.setText(label);
                button.setForeground(Color.WHITE);
                button.setBackground(label.equals("Edit") ? new Color(255, 193, 7) :
                        (label.equals("Delete") ? new Color(220, 53, 69) : new Color(52, 152, 219)));
                return button;
            }

            public Object getCellEditorValue() {
                return label;
            }
        };

        return editor;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
