import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/pwd_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "24Mmce23";

    private Connection conn;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertPassword(String service, String username, String password) {
        String sql = "INSERT INTO passwords (service, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, service);
            stmt.setString(2, username);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllPasswords() {
        String sql = "SELECT * FROM passwords";
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updatePassword(String service, String username, String password, String old_password) {
        String sql = "UPDATE passwords SET service=?, username=?, password=? WHERE service=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, service);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, old_password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePassword(String service) {
        String sql = "DELETE FROM passwords WHERE service=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, service);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().toLowerCase().contains("duplicate")) {
                System.out.println("Username already exists.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}