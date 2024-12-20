// LogisticsApp.java

import com.theokanning.openai.service.OpenAiService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LogisticsApp extends App {
    private TariffAdvisor tariffAdvisor;
    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null) {
            System.err.println("Не найден ключ API.");
            return;
        }
        OpenAiService service = new OpenAiService(apiKey);

        LogisticsApp app = new LogisticsApp("Грузовичкофф", service);
        app.showLoginScreen();
    }

    public LogisticsApp(String nameCompany, OpenAiService service) {
        this.nameCompany = nameCompany;
        this.tariffAdvisor = new TariffAdvisor(service);
    }

    @Override
    public void showLoginScreen() {
        final JFrame loginFrame = new JFrame(nameCompany + " - Вход в систему");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        loginFrame.setLayout(new GridBagLayout());

        Font customFont = new Font("Verdana", Font.PLAIN, 14);

        JLabel userLabel = new JLabel("Имя пользователя:");
        userLabel.setFont(customFont);
        final JTextField usernameField = new JTextField(15);
        usernameField.setFont(customFont);

        JLabel passLabel = new JLabel("Пароль:");
        passLabel.setFont(customFont);
        final JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(customFont);

        JButton loginButton = new JButton("Войти");
        loginButton.setFont(customFont);
        JButton registerButton = new JButton("Регистрация");
        registerButton.setFont(customFont);

        addHoverEffect(loginButton);
        addHoverEffect(registerButton);


        // Логин и регистрация через БД
        login(loginButton, usernameField, passwordField, loginFrame);
        registration(registerButton, usernameField, passwordField, loginFrame);

        LogisticsApp.setIconApp("LogReg.png", loginFrame);

        loginFrame.setMinimumSize(new Dimension(400, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(userLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFrame.add(passLabel, gbc);

        gbc.gridx = 1;
        loginFrame.add(passwordField, gbc);

        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginFrame.add(registerButton, gbc);

        gbc.gridx = 1;
        loginFrame.add(loginButton, gbc);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    @Override
    public void showMainApp() {
        final JFrame frame = new JFrame("Грузоперевозки - Выбор тарифов");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font customFont = new Font("Verdana", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        //GPT
        JButton chatButton = new JButton("Чат-GPT по тарифам");
        chatButton.setFont(new Font("Verdana", Font.PLAIN, 14));
        chatButton.setBackground(Color.RED);


        addHoverEffect(chatButton);
        chatButton.addActionListener(e -> {
            ChatGPTDialog dialog = new ChatGPTDialog(frame, tariffAdvisor);
            dialog.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(chatButton);
        bottomPanel.setBackground(Color.PINK);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        LogisticsApp.setIconApp("img.png", frame);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        String[] tariffs = {"Эконом", "Стандарт", "Бизнес", "Премиум"};
        String[] descriptions = {
                "Базовый тариф для небольших грузов.",
                "Оптимальный выбор для стандартных перевозок.",
                "Повышенный комфорт и скорость доставки.",
                "Максимальный сервис для особых клиентов."
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int userId = dbManager.getUserId(currentUser);

        for (int i = 0; i < tariffs.length; i++) {
            final String tariff = tariffs[i];
            final String description = descriptions[i];

            JPanel tariffPanel = new JPanel(new BorderLayout());
            tariffPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            tariffPanel.setBackground(Color.WHITE);

            JLabel label = new JLabel("<html><b>" + tariff + "</b><br>" + description + "</html>");
            label.setFont(customFont);

            JButton addButton = new JButton("Добавить");
            addButton.setFont(customFont);
            addHoverEffect(addButton);

            addButton.addActionListener(e -> {
                dbManager.addItemToCart(userId, tariff);
                JOptionPane.showMessageDialog(frame, tariff + " добавлен в корзину!");
            });

            tariffPanel.add(label, BorderLayout.CENTER);
            tariffPanel.add(addButton, BorderLayout.EAST);

            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(tariffPanel, gbc);
        }

        JButton showCartButton = new JButton("Показать корзину");
        showCartButton.setFont(customFont);
        addHoverEffect(showCartButton);
        showCartButton.addActionListener(e -> showCart(frame));

        JButton logoutButton = new JButton("Выйти");
        logoutButton.setFont(customFont);
        addHoverEffect(logoutButton);
        logoutButton.addActionListener(e -> {
            isLoggedIn = false;
            currentUser = "";
            frame.dispose();
            showLoginScreen();
        });

        gbc.gridx = 0;
        gbc.gridy = tariffs.length;
        panel.add(showCartButton, gbc);

        gbc.gridy = tariffs.length + 1;
        panel.add(logoutButton, gbc);

        JButton wish = new JButton("Добавить пожелания");
        wish.setFont(customFont);
        addHoverEffect(wish);
        JPanel topPanel = new JPanel();
        topPanel.add(wish);
        topPanel.setBackground(Color.PINK);
        frame.add(topPanel, BorderLayout.NORTH);

        wish.addActionListener(e -> {
            String text = Converter.getTextFromFile();
            if (!text.isEmpty()) {
                int currentUserID = dbManager.getUserId(currentUser);
                dbManager.addWishToDB(currentUserID, text);
                JOptionPane.showMessageDialog(frame, "Пожелания добавлены!");
                dbManager.SortWish();
            }

        });

        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        LogisticsApp.setIconApp("img.png", frame);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showCart(JFrame parentFrame) {
        int userId = dbManager.getUserId(currentUser);
        List<String> cartItems = dbManager.getCartItems(userId);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String item : cartItems) {
            listModel.addElement(item);
        }

        JDialog cartDialog = new JDialog(parentFrame, "Корзина", true);
        cartDialog.setSize(600, 400);
        cartDialog.setLayout(new BorderLayout());

        Font customFont = new Font("Verdana", Font.PLAIN, 14);

        JList<String> cartList = new JList<>(listModel);
        cartList.setFont(customFont);

        JScrollPane scrollPane = new JScrollPane(cartList);
        JButton removeButton = new JButton("Удалить выбранное");
        removeButton.setFont(customFont);
        addHoverEffect(removeButton);

        // кнопка удаления товаров из корзины
        removeButton.addActionListener(e -> {
            List<String> selectedItems = cartList.getSelectedValuesList();
            if (!selectedItems.isEmpty()) {
                dbManager.removeCartItems(userId, selectedItems);
                for (String item : selectedItems) {
                    listModel.removeElement(item);
                }
                JOptionPane.showMessageDialog(cartDialog, "Выбранные товары удалены из корзины!");
            } else {
                JOptionPane.showMessageDialog(cartDialog, "Пожалуйста, выберите товар для удаления.");
            }
        });

        JButton removeAllButton = new JButton("Очистить корзину");
        removeAllButton.setFont(customFont);
        addHoverEffect(removeAllButton);

        // очистка корзины
        removeAllButton.addActionListener(e -> {
            if (!cartItems.isEmpty()) {
                dbManager.clearCart(userId);
                listModel.removeAllElements();
                JOptionPane.showMessageDialog(cartDialog, "Корзина очищена");
            } else {
                JOptionPane.showMessageDialog(cartDialog, "Корзина уже пуста!");
            }
        });

        JButton checkoutButton = new JButton("Оформить заказ");
        checkoutButton.setFont(customFont);
        addHoverEffect(checkoutButton);

        // оформление заказа
        checkoutButton.addActionListener(e -> {
            if (!cartItems.isEmpty()) {
                dbManager.createOrder(userId, cartItems);
                dbManager.clearCart(userId);
                listModel.clear();
                JOptionPane.showMessageDialog(cartDialog, "Спасибо за ваш заказ!");
            } else {
                JOptionPane.showMessageDialog(cartDialog, "Ваша корзина пуста.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(removeAllButton);

        cartDialog.setMinimumSize(new Dimension(600, 400));
        cartDialog.add(scrollPane, BorderLayout.CENTER);
        cartDialog.add(buttonPanel, BorderLayout.SOUTH);
        cartDialog.setLocationRelativeTo(parentFrame);
        cartDialog.setVisible(true);
    }

}