

# Описание базы данных

![Db](docs/db.png)

- База данных содержит в себе две таблицы:
    - `customer` - хранит инфоромацию о пользователе
    - `delay` - хранит задержку сообщения (общая для всех пользователей)

# Описание эндпоинтов

### http://localhost:8083/delay/{value}

- Изменение задержки

### http://localhost:8083/customer/init

- Добаввление пользователя

- Body:

```json
{
  "chatId": 1,
  "userId": 1,
  "lastMessage": "test",
  "index": 1,
  "username": "test2"
}
```

### http://localhost:8083/customer/index

- Обновление индекса

- Body:

```json
{
  "chatId": 1,
  "message": "qwerty",
  "userId": 1,
  "username": "qwerty"
}
```

### http://localhost:8083/customer/last/{id}

- Получение последнего сообщения пользователя

- Response:

```json
{
  "username": 1,
  "message": "qwerty"
}
```

### http://localhost:8083/customer/exist/{id}

- Проверка, существует ли пользователь. Возвращается true/false 

### Swagger

- для запуска Swagger необходимо воспользоваться url:

```
http://localhost:8083/swagger-ui/index.html#/
```

# Как запустить?

- Предварительно мной был создан образ приложения и добавлен на [DockerHub](https://hub.docker.com/u/vya4eslava):

- Для запуска Вам необходимо клонировать на свой компьютер репозиторий. Либо скачать .zip архив с проектом

- Установить в `docker-compose` токен и название бота:

```yaml
BOT_NAME:
BOT_TOKEN:
```

- При желании можно изменить задержку:

```yaml
DELAY_VALUE: 2000
```

- После этого выполнить команду:

```shell script
docker compose up -d
```

# Результаты

![Result](docs/result.jpg)
