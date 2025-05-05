import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Dashboard extends JFrame {
    private JTable table;
    private int hoveredRow = -1;
    private final int PASSWORD_COLUMN = 2;

    public Dashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(236, 233, 252), 0, height, new Color(220, 248, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        background.setLayout(new BorderLayout());
        setContentPane(background);
        setFontDefaults();

        JPanel wrapper = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                g2d.setColor(Color.white);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(30, 30, 30, 30));
        background.add(wrapper, BorderLayout.CENTER);

        String[] cols = {"Service", "Username", "Password", "Edit", "Delete"};
        Object[][] data = {
                {"Instagram", "arshadaman", "secret123", null, null},
                {"Twitter", "arshad_twt", "pa55w0rd", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
                {"LinkedIn", "arshad.ln", "qwerty", null, null},
        };
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;
            }
        };
        table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    if (row == hoveredRow) {
                        c.setBackground(new Color(225, 245, 254)); // Hover effect
                    } else {
                        c.setBackground((row % 2 == 0) ? new Color(245, 245, 245) : new Color(232, 232, 232)); // Zebra striping
                    }
                }
                return c;
            }
        };
        table.setRowHeight(60);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(52, 73, 94)); // Header color (Dark Blue-gray)
        header.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer maskedRenderer = new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                if (value != null) {
                    char[] stars = new char[value.toString().length()];
                    Arrays.fill(stars, '•');
                    setText(new String(stars));
                } else {
                    setText(null);
                }
            }
        };
        DefaultTableCellRenderer unmaskedRenderer = new DefaultTableCellRenderer();
        table.getColumnModel().getColumn(PASSWORD_COLUMN).setCellRenderer(maskedRenderer);

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));

        table.getColumn("Delete").setCellRenderer(new DeleteButtonRenderer());
        table.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox(), model));

        table.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                hoveredRow = table.rowAtPoint(e.getPoint());
                table.repaint();
            }
        });

        JButton toggle = new JButton("Show Passwords");
        toggle.setBackground(new Color(46, 204, 113)); // Green toggle button
        toggle.setForeground(Color.white);
        toggle.setFocusPainted(false);
        toggle.setBorder(BorderFactory.createEmptyBorder(14, 30, 14, 30));
        toggle.setFont(new Font("Poppins", Font.BOLD, 16));
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggle.addActionListener(e -> {
            boolean showing = toggle.getText().startsWith("Show");
            if (showing) {
                table.getColumnModel().getColumn(PASSWORD_COLUMN).setCellRenderer(unmaskedRenderer);
                toggle.setText("Hide Passwords");
            } else {
                table.getColumnModel().getColumn(PASSWORD_COLUMN).setCellRenderer(maskedRenderer);
                toggle.setText("Show Passwords");
            }
            model.fireTableDataChanged();
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        wrapper.add(scroll, BorderLayout.CENTER);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBar.setBackground(new Color(0, 0, 0, 0));
        buttonBar.add(toggle);
        wrapper.add(buttonBar, BorderLayout.SOUTH);

        setSize(800, 500); // Fixed window size
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setFontDefaults() {
        Font f = new Font("Helvetica Neue", Font.PLAIN, 18);
        UIManager.put("Label.font", f);
        UIManager.put("Table.font", f);
        UIManager.put("TableHeader.font", f.deriveFont(Font.BOLD));
        UIManager.put("Button.font", f);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}

class ButtonRenderer implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = new JButton("Edit");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(new Color(255, 193, 7)); // Yellow edit button
        button.setForeground(Color.blue);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(new Color(255, 193, 7)); // Yellow button color
        button.setForeground(Color.blue);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setText("Edit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String serviceName = (String) table.getValueAt(row, 0);
                    String username = (String) table.getValueAt(row, 1);
                    String password = (String) table.getValueAt(row, 2);
                    new UpdateScreen(serviceName, username, password);
                }
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.table = table;
        return button;
    }

    public Object getCellEditorValue() {
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

class DeleteButtonRenderer implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = new JButton("Delete");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(new Color(220, 53, 69)); // Red delete button
        button.setForeground(Color.blue);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}

class DeleteButtonEditor extends DefaultCellEditor {
    private JButton button;
    private JTable table;
    private DefaultTableModel model;

    public DeleteButtonEditor(JCheckBox checkBox, DefaultTableModel model) {
        super(checkBox);
        this.model = model;
        button = new JButton("Delete");
        button.setOpaque(true);
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(new Color(220, 53, 69)); // Red button color
        button.setForeground(Color.blue);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this row?", "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        model.removeRow(row);
                    }
                }
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.table = table;
        return button;
    }

    public Object getCellEditorValue() {
        return "";
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
