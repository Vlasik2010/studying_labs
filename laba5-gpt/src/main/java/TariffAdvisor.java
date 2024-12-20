import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

class TariffAdvisor {
    private OpenAiService service;

    public TariffAdvisor(OpenAiService service) {
        this.service = service;
    }

    public String answerTariffQuestion(String userQuestion) {
        String prompt = "Ты — эксперт по логистике. Вот доступные тарифы:\n" +
                "Эконом: Базовый тариф для небольших грузов.\n" +
                "Стандарт: Оптимальный выбор для стандартных перевозок.\n" +
                "Бизнес: Повышенный комфорт и скорость доставки.\n" +
                "Премиум: Максимальный сервис для особых клиентов.\n\n" +
                "Ты также можешь рассчитать примерную стоимость до указанного адреса.\n" +
                "Пользователь спрашивает: \"" + userQuestion + "\"\n\n" +
                "Дай подробный и точный ответ, ссылаясь на тарифы и примерные стоимости, если это уместно."+
                "Писать на одной строке не более 10 слов, постарайся делить текст на абзацы, так как текст не умещается в одну строку.";

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Ты — помощник, хорошо разбирающийся в тарифах и логистике.");
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), prompt);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(java.util.Arrays.asList(systemMessage, userMessage))
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        ChatCompletionResult result = service.createChatCompletion(request);
        if (!result.getChoices().isEmpty()) {
            return result.getChoices().get(0).getMessage().getContent();
        } else {
            return "Извините, я сейчас не могу ответить на ваш вопрос.";
        }
    }
}