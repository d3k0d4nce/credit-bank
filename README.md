# 🏦 Credit Bank Monorepo

Микросервисная система для обработки кредитных заявок с полным мониторингом и observability.

---

## 📋 Технологический стек

| Компонент | Технология |
|-----------|-----------|
| **Язык** | Java 17 |
| **База данных** | PostgreSQL 15 |
| **База данных порт** | 5433 |
| **Название БД** | deal-service |
| **Логирование** | Logback → Grafana Loki |
| **Метрики** | Micrometer → Prometheus |
| **Визуализация** | Grafana |

---

## 🏗️ Архитектура

Проект состоит из следующих микросервисов:

- **Deal Service** (`/deal`) - управление кредитными сделками
- **Calculator Service** (`/calculator`) - расчёт кредитных параметров
- **Statement Service** (`/statement`) - обработка заявок
- **Dossier Service** (`/dossier`) - управление досье клиентов
- **Gateway Service** (`/gateway`) - API Gateway
- **API Module** (`/api`) - общая OpenAPI спецификация

---

## 🐳 Инфраструктура (Docker Compose)

| Сервис | Порт | Назначение |
|--------|------|------------|
| **Zookeeper** | 22181 | Координация Kafka |
| **Kafka** | 9092, 9093 | Message broker |
| **PostgreSQL** | 5432 | Основная БД |
| **Deal Service** | 8081 | Микросервис сделок |
| **Calculator Service** | 8080 | Микросервис расчётов |
| **Statement Service** | 8082 | Микросервис заявок |
| **Dossier Service** | 8083 | Микросервис досье |
| **Gateway Service** | 8888 | API Gateway |
| **Grafana** | 3000 | Визуализация метрик и логов |
| **Prometheus** | 9090 | Сбор метрик |
| **Loki** | 3100 | Агрегация логов |

---

## 📸 Swagger UI

Документация API доступна через Swagger UI. Здесь описаны все эндпоинты микросервисов, модели запросов и ответов.

<img width="1919" height="905" alt="image" src="https://github.com/user-attachments/assets/3623c985-8125-430e-a430-6d7fbf6ad8ac" />

---

## 📊 Основные метрики

### 1. Статус и доступность
- **Status** — работает ли приложение (UP/DOWN)
- **Up Time** — время работы приложения с момента запуска

### 2. Throughput (пропускная способность)
- **Requests per second** — количество запросов в секунду (по методам, статусам, URI)
- **Mean response time** — среднее время ответа

### 3. Качество обслуживания (SLA)
- **Requests that missed 50ms SLA** — количество запросов, не уложившихся в 50мс
- **Response time percentiles** — время ответа для 50%, 75%, 90%, 95% запросов

### 4. Популярность API
- **Top 10 APIs** — 10 самых часто вызываемых эндпоинтов

---

## 📈 Графики метрик

### HTTP статистика
На графике отображена общая статистика по HTTP запросам: количество запросов, статусы ответов (2xx, 4xx, 5xx), а также среднее время ответа.

<img width="1280" height="634" alt="image" src="https://github.com/user-attachments/assets/8647b073-1585-42a1-b4f7-1ef74b4740c8" />

---

### Spring Cloud Gateway метрики
Метрики API Gateway: количество маршрутов, активные соединения, ошибки маршрутизации.

<img width="1280" height="607" alt="image" src="https://github.com/user-attachments/assets/c416d379-447e-4586-b4ed-eb78eb3521f7" />

---

### Детализация по HTTP методам
Распределение запросов по методам (GET, POST, PUT, DELETE) и их успешность.

<img width="1280" height="596" alt="image" src="https://github.com/user-attachments/assets/48e0e016-2ce2-4f48-acbd-526c3850af4f" />

---

### Percentile времени ответа
График показывает время ответа для разных перцентилей (50%, 75%, 90%, 95%), что позволяет отслеживать выбросы и деградацию производительности.

<img width="1280" height="560" alt="image" src="https://github.com/user-attachments/assets/3ebce574-a6c0-409f-98c5-2625082d6b00" />

---

## 📝 Логирование

**Logback → Grafana Loki**

Все логи микросервисов собираются через Loki4j appender и отправляются в Loki. Дашборды Loki позволяют фильтровать логи по уровню (INFO, WARN, ERROR), сервису, временному диапазону и тексту сообщения.

### Общая статистика логов
Дашборд показывает общее количество логов по сервисам, распределение по уровням логирования и частоту ошибок во времени.

<img width="1533" height="842" alt="image" src="https://github.com/user-attachments/assets/ba4d114a-e058-4d7e-ab0a-7905012502f5" />

---

### Фильтрация по уровню ERROR
На этом дашборде отфильтрованы только ошибки (ERROR level), что позволяет быстро находить проблемные места в работе приложения.

<img width="1524" height="840" alt="image" src="https://github.com/user-attachments/assets/06e0b86c-76a2-47f7-998c-e8b249222362" />

---

### Поиск по сервису и тексту
Логи можно фильтровать по конкретному микросервису (deal, calculator, gateway) и искать по ключевым словам.

<img width="1511" height="846" alt="image" src="https://github.com/user-attachments/assets/34463cc2-70c3-4c93-869e-5833417aa575" />

---

### Логи Gateway сервиса
Пример логов API Gateway с информацией о входящих запросах, маршрутизации и ответах.

<img width="1518" height="839" alt="image" src="https://github.com/user-attachments/assets/bde076a3-b0d6-416d-af50-317e16448400" />

---

## 🔧 Примеры запросов Loki

```logql
# Все INFO логи
{app="credit-bank", level="INFO"}

# Ошибки за последний час
{app="credit-bank", level="ERROR"} | json

# Логи конкретного сервиса
{app="credit-bank"} |= "gateway"

# Поиск по email
{app="credit-bank"} |~ "kishko.2003@list.ru"

# Подсчёт ошибок по сервисам
sum by (service) (count_over_time({app="credit-bank", level="ERROR"}[1h]))
```