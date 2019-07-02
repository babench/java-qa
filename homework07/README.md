# Домашнее задание #07

## Перенаправить трафик в прокси-сервер

Цель:
Научится перенаправлять трафик в прокси-сервер для дальнейшего анализа.

- [X] Подключить библиотеку
- [X] Запустить прокси-сервер
- [X] Добавить адрес прокси-сервера к настройкам браузера
- [X] Получить трафик
- [X] Сохранить его в файл

Критерии оценки: 5 баллов за задание 

## Подробности реализация

1. В DriverFactory добавлена поддержка проксированного браузера (useBrowserMobProxy) и поддержка внешнего proxy (proxyEnabled).
2. Флаги управления при старте 
        <proxyEnabled>false</proxyEnabled>
        <proxyHost/>
        <proxyPort/>
3. Главный тест данного ДЗ  BrowserMobProxyIT
После его работы появится файл .log\ya.ru.har.log

### Тесты
Лог перекопмиляции с тестами (плагин failsafe:integration-test) : 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

### Фрагмент har файла .\log\ya.ru.har.log

===========
{"log":{"version":"1.2","creator":{"name":"BrowserMob Proxy","version":"2.1.5","comment":""},"pages":[{"id":"Page 0","startedDateTime":"2019-07-02T08:40:57.264Z","title":"Page 0","pageTimings":{"comment":""},"comment":""}],"entries":[{"pageref":"Page 0","startedDateTime":"2019-07-02T08:40:58.633Z","request":{"method":"GET","url":"https://ya.ru/","httpVersion":"HTTP/1.1","cookies":[],"headers":[],"queryString":[],"headersSize":412,"bodySize":0,"comment":""},"response":{"status":200,"statusText":"Ok","httpVersion":"HTTP/1.1","cookies":[],"headers":[],"content":{"size":0,"mimeType":"text/html; charset=UTF-8","comment":""},"redirectURL":"","headersSize":4579,"bodySize":53850,"comment":""},"cache":{},"timings":{"comment":"","blocked":0,"dns":2,"
===========

    