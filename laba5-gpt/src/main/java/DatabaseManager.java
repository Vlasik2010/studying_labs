import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "Vlas";
    private static final String PASSWORD = "password";

    public DatabaseManager() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Регистрация нового пользователя
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Пользователь может уже существовать
            return false;
        }
    }

    // Проверка учетных данных
    public boolean checkUserCredentials(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    // Проверка пароля
                    if (PasswordUtil.checkPassword(password, storedHash)) {
                        System.out.println("Аутентификация прошла успешно.");
                        return true;
                    } else {
                        System.out.println("Неверный пароль.");
                        return false;
                    }
                } else {
                    System.out.println("Пользователь с таким логином не найден.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение id пользователя по имени
    public int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Добавление товара в корзину
    public void addItemToCart(int userId, String productName) {
        String sql = "INSERT INTO carts (user_id, product_name) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, productName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение корзины пользователя
    public List<String> getCartItems(int userId) {
        List<String> cartItems = new ArrayList<>();
        String sql = "SELECT product_name FROM carts WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(rs.getString("product_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Очистка корзины пользователя
    public void clearCart(int userId) {
        String sql = "DELETE FROM carts WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление выбранных товаров из корзины
    public void removeCartItems(int userId, List<String> itemsToRemove) {
        String sql = "DELETE FROM carts WHERE user_id = ? AND product_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String item : itemsToRemove) {
                stmt.setInt(1, userId);
                stmt.setString(2, item);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Оформление заказа
    public void createOrder(int userId, List<String> cartItems) {
        String orderSql = "INSERT INTO orders (user_id) VALUES (?) RETURNING id";
        String orderItemsSql = "INSERT INTO order_items (order_id, product_name) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement orderItemsStmt = conn.prepareStatement(orderItemsSql)) {

            conn.setAutoCommit(false);

            // Создаем заказ
            orderStmt.setInt(1, userId);
            int orderId = -1;
            try (ResultSet rs = orderStmt.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt("id");
                }
            }

            // Добавляем товары заказа
            if (orderId != -1) {
                for (String product : cartItems) {
                    orderItemsStmt.setInt(1, orderId);
                    orderItemsStmt.setString(2, product);
                    orderItemsStmt.addBatch();
                }
                orderItemsStmt.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addWishToDB(int userId, String wish) {
        String sql = "INSERT INTO wishes (user_id, wish_text) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, wish);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void SortWish(){
        String sql = "SELECT * FROM wishes ORDER BY user_id ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("wish_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}