import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ChatGPTDialog extends JDialog {

    private TariffAdvisor tariffAdvisor;
    private JTextArea textArea;
    private JTextField inputField;
    private JButton sendButton;

    // Настройки задержек
    private static final Random RANDOM = new Random();
    private static final int BASE_DELAY_MS = 15;
    private static final int RANDOM_EXTRA_DELAY_MS = 35;
    private static final int PUNCTUATION_EXTRA_DELAY_MS = 200;
    private static final int WORD_SPACE_EXTRA_DELAY_MS = 50;

    public ChatGPTDialog(Frame parent, TariffAdvisor tariffAdvisor) {
        super(parent, "Чат с GPT", true);
        this.tariffAdvisor = tariffAdvisor;

        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Отправить");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> {
            String question = inputField.getText().trim();
            if (!question.isEmpty()) {
                inputField.setText("");
                // Получаем ответ от TariffAdvisor
                String answer = tariffAdvisor.answerTariffQuestion(question);

                // Очищаем текстовое поле перед выводом нового ответа
                textArea.setText("");

                // Выводим ответ "по-человечески"

                printHumanLike("Ваш вопрос: "+ question+ "\n"+
                        "Ответ от GPT: " + answer);
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(parent);
    }


    private void printHumanLike(String message) {
        char[] chars = message.toCharArray();

        // Используем массив для хранения текущего индекса и Timer для анимации
        final int[] currentIndex = {0};

        // Создаём таймер, который срабатывает каждые X миллисекунд, добавляет по одному символу
        Timer timer = new Timer(0, null);

        timer.addActionListener(e -> {
            if (currentIndex[0] < chars.length) {
                char c = chars[currentIndex[0]];

                // Добавляем символ в textArea
                textArea.append(String.valueOf(c));

                // Определяем задержку для следующего символа
                int delay = BASE_DELAY_MS + RANDOM.nextInt(RANDOM_EXTRA_DELAY_MS + 1);

                if (isPunctuation(c)) {
                    delay += PUNCTUATION_EXTRA_DELAY_MS;
                }
                if (c == ' ') {
                    delay += WORD_SPACE_EXTRA_DELAY_MS + RANDOM.nextInt(RANDOM_EXTRA_DELAY_MS + 1);
                }

                // Обновляем задержку таймера для следующего цикла
                timer.setDelay(delay);

                // Скроллим вниз, чтобы видно было последний добавленный символ
                textArea.setCaretPosition(textArea.getDocument().getLength());

                currentIndex[0]++;
            } else {
                // Когда все символы добавлены, останавливаем таймер
                timer.stop();
            }
        });

        // Запускаем таймер с изначальной задержкой
        timer.setDelay(10);
        timer.start();
    }

    private boolean isPunctuation(char c) {
        return c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':';
    }
}