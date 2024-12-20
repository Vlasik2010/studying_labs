import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Converter {
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    public static String getTextFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл для загрузки");

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Выбран файл: " + selectedFile.getAbsolutePath());

            StringBuilder text = new StringBuilder();
            try (Scanner scanner = new Scanner(selectedFile)) { // Закрывающая скобка после Scanner(selectedFile)
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append(System.lineSeparator());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Можно добавить сообщение пользователю
                JOptionPane.showMessageDialog(null, "Файл не найден: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            return text.toString();
        } else {
            // Пользователь отменил выбор файла
            System.out.println("Файл не был выбран.");
            return ""; // Или можно вернуть null, или выбросить исключение
        }
    }

}