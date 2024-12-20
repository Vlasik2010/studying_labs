// App.java (Абстрактный класс)
import javax.swing.*;
import java.awt.*;

public abstract class App implements ShowCart, ShowMainApp, ShowLoginScreen {
    protected String nameCompany;
    protected boolean isLoggedIn = false;
    protected String currentUser = "";
    protected DatabaseManager dbManager = new DatabaseManager();

    protected static void setIconApp(String path, JFrame Parentframe) {
        ImageIcon icon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(path));
        Parentframe.setIconImage(icon.getImage());
    }

    protected static void addHoverEffect(final JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = button.getForeground();

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                animateButtonColor(button, originalColor, Color.BLUE, 200);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                animateButtonColor(button, Color.BLUE, originalColor, 200);
            }
        });
    }

    protected static void animateButtonColor(final JButton button, Color startColor, Color endColor, int duration) {
        Timer timer = new Timer(0, null);
        int steps = 20;
        int delay = duration / steps;
        float[] startRGB = startColor.getRGBComponents(null);
        float[] endRGB = endColor.getRGBComponents(null);
        float[] delta = new float[3];
        for (int i = 0; i < 3; i++) {
            delta[i] = (endRGB[i] - startRGB[i]) / steps;
        }
        final int[] step = {0};
        timer.addActionListener(e -> {
            if (step[0] < steps) {
                float[] newColor = new float[3];
                for (int i = 0; i < 3; i++) {
                    newColor[i] = startRGB[i] + delta[i] * step[0];
                }
                button.setForeground(new Color(newColor[0], newColor[1], newColor[2]));
                step[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setDelay(delay);
        timer.start();
    }

    protected void registration(JButton registerButton, JTextField usernameField, JPasswordField passwordField, JFrame parentframe) {
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = PasswordUtil.hashPassword(new String(passwordField.getPassword()));
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentframe, "Имя пользователя и пароль не могут быть пустыми.");
                return;
            }

            boolean success = dbManager.registerUser(username, password);
            if (success) {
                JOptionPane.showMessageDialog(parentframe, "Регистрация успешна!");
            } else {
                JOptionPane.showMessageDialog(parentframe, "Пользователь уже существует.");
            }
        });
    }

    protected void login(JButton loginButton, JTextField usernameField, JPasswordField passwordField, JFrame parentframe) {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean success = dbManager.checkUserCredentials(username, password);
            if (success) {
                isLoggedIn = true;
                currentUser = username;
                JOptionPane.showMessageDialog(parentframe, "Вход выполнен!");
                parentframe.dispose();
                showMainApp();
            } else {
                JOptionPane.showMessageDialog(parentframe, "Неправильное имя пользователя или пароль.");
            }
        });
    }
}