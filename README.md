# 🚛 LogisticsApp - Java-приложение для управления логистикой

## 📌 Описание проекта

**LogisticsApp** - это Java-приложение с графическим интерфейсом, предназначенное для управления грузоперевозками. 
Оно включает в себя систему аутентификации, выбор тарифов, корзину товаров, оформление заказов и поддержку пожеланий клиентов.
Дополнительно в проекте реализована интеграция с **GPT-3.5** для рекомендаций по тарифам.

---

## 🚀 Функциональные возможности

- 🔐 **Аутентификация**: регистрация и вход пользователей
- 📦 **Корзина**: добавление, удаление и оформление заказов
- 📜 **Выбор тарифа**: эконом, стандарт, бизнес, премиум
- 🤖 **Чат с AI**: интеграция с GPT-3.5 для тарифных консультаций
- 📄 **Логирование**: запись заказов и пожеланий клиентов в файлы
- 🖥 **Графический интерфейс**: построен с использованием **Swing**
- 🗄 **Работа с базой данных**: PostgreSQL для хранения пользователей, заказов и пожеланий

---

## ⚙ Установка и запуск

### 1️⃣ Требования

- **JDK 11+** (рекомендуется JDK 17)
- **Maven** (для сборки проекта)
- **PostgreSQL** (для хранения данных)
- **API-ключ OpenAI** (для GPT-3.5)

### 2️⃣ Клонирование репозитория

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
cd YOUR_REPO
```

### 3️⃣ Установка зависимостей

```bash
mvn clean install
```

### 4️⃣ Настройка конфигурации

Создайте `.env` файл в корневой папке и укажите настройки:

```env
DB_URL=jdbc:postgresql://localhost:5432/logistics_db
DB_USER=your_db_user
DB_PASSWORD=your_db_password
OPENAI_API_KEY=your_openai_api_key
```

### 5️⃣ Запуск приложения

```bash
mvn exec:java -Dexec.mainClass="com.example.LogisticsApp"
```

---

## 📂 Структура проекта

```
📦 LogisticsApp
 ┣ 📂 src
 ┃ ┗ 📂 com/logistics
 ┃ ┃ ┣ 📜 LogisticsApp.java      # Основной класс приложения
 ┃ ┃ ┣ 📜 App.java               # Базовый класс
 ┃ ┃ ┣ 📜 TariffAdvisor.java     # GPT-3.5 консультант
 ┃ ┃ ┣ 📜 ChatGPTDialog.java     # Чат с AI
 ┃ ┃ ┣ 📜 PasswordUtil.java      # Хеширование паролей
 ┃ ┃ ┣ 📜 FileAppendLogger.java  # Логирование данных
 ┃ ┃ ┣ 📜 DatabaseManager.java   # Работа с БД
 ┃ ┃ ┣ 📜 Converter.java         # Обработчик файлов
 ┃ ┃ ┣ 📜 interfaces             # Интерфейсы
 ┃ ┃ ┃ ┣ 📜 ShowCart.java
 ┃ ┃ ┃ ┣ 📜 ShowMainApp.java
 ┃ ┃ ┃ ┗ 📜 ShowLoginScreen.java
 ┣ 📜 pom.xml
 ┣ 📜 .env
 ┣ 📜 README.md
 ┗ 📜 LICENSE
```

---

## 🗄 Работа с базой данных (PostgreSQL)

Приложение использует PostgreSQL для хранения данных. Вот основные SQL-запросы для создания таблиц:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_name VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    order_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_name VARCHAR(255) NOT NULL
);

CREATE TABLE wishes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    wish_text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
```

---

## 🤝 Вклад в проект

Хотите помочь в развитии? Следуйте этим шагам:

1. Сделайте **fork** репозитория
2. Создайте новую ветку (`git checkout -b feature-branch`)
3. Внесите изменения и зафиксируйте (`git commit -m 'Добавил новую фичу'`)
4. Отправьте изменения (`git push origin feature-branch`)
5. Создайте **Pull Request**

---

## 📜 Лицензия

Этот проект распространяется под лицензией **MIT**.

---

## 📞 Контакты

📧 **Email:** [sintsev.vlas15@icloud.com](mailto\:sintsev.vlas15@icloud.com) 💬 **Telegram:** [@Vlasik_01](https://t.me/Vlasik_01)

