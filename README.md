Задача:

1) Сделать REST сервис с использованием JDBC и Servlet
2) Функционал любой на выбор, минимум CRUD сервис с несколькими видами entity
3) Запрещено использовать Spring, Hibernate, Lombok
4) Можно использовать Hikari CP, Mapstruckt
5) Должны быть реализованы связи ManayToOne(OneToMany),
   ManyToMany https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne, https://en.wikipedia.org/wiki/Many-to-many_(data_model), https://en.wikipedia.org/wiki/One-to-many_(data_model)
6) Связи должны быть отражены в коде(должны быть соответствующие коллекции внутри энтити)
7) Сервлет должен возвращать DTO, не возвращаем Entity, принимать также DTO
8) Должна быть правильная архитектура https://habr.com/ru/articles/269589/
9) Должен быть сервисный слой
10) Должны соблюдаться принципы ООП, Solid
11) Должны быть unit тесты, использовать Mockito и Junit, для проверки работы репозитория(DAO) с БД использовать
    testcontainers: https://testcontainers.com/, https://habr.com/ru/articles/444982/
12) Покрытие тестами должно быть больше 80%
13) Должны быть протестированы все слои приложения
14) Слой сервлетов, сервисный слой тестировать с помощью Mockito
15) БД на выбор Pestgres, MySQL
16) Ставим плагин SonarLint


CRUD сервис для базы данных приложения позволяющей бронировать технику в офисе. Изначально задумывалось
для общего пользованя велосипедами, самокатами и прочего колесного транспорта, что бы не возникло ситуации, 
когда ты планируешь уехать из офиса на велосипеде, а ни одного уже не осталось.

----------
--- БД ---
----------

// Город, в котором существует техника для бронирования
City {
    id
    name - название города
    timezone - таймзона города (не реализована)
}

// Пользователь системы
User {
    id - идентификатор пользователя в системе
    name - имя пользователя
    surname - фамилия пользователя
}

// Транспортное средство (пока что велосипеды, но вдруг еще что будет, поддерживаем мастшабируемость)
Vehicle {
    id
    city_id - город, в котором находится данная техника
    name - название техники
    image - изображение техники (не реализованно)
}

// Бронирование
Reservation {
    id
    vehicle_ids (M-to-M) - список забронированной техники
    user_id (M-to-O) - пользователь, кто забронировал
    startDatetime - с какого времени
    endDatetime - до какого времени
    status [ACTIVE, CANCELED] - статус бронирования (активно / отменено)
}


---------------
--- Запросы ---
---------------

Get /city/all - все города
Get /city/id - город по id
Post /city - создание города
{
    "name" : String
}
Put /city - изменение города
{
    "id" : Long
    "name" : String
}
Delete /city/id - удаление города 


Get /vehicle/all - весь транспорт
Get /vehicle/id - транспорт по id
Post /vehicle - создание транспорта
{
    "name": String,
    "city": {
        "id": Long,
    }
}
Put /vehicle - изменение транспорта
{
    "id": Long,
    "name": String,
    "city": {
        "id": Long,
    }
}
Delete /vehicle/id - удаление транспорта

Get /reservation/all - все бронирования
Get /reservation/id - бронирование по id
Post /reservation - создание брони
{
    "status": ["CANCELED", "ACTIVE"],
    "startDatetime": Timestamp,
    "endDatetime": Timestamp,
    "vehicleList": [
        {
            "id": Long
        }
    ],
    "user": {
        "id": Long
    }
}
Put /Reservation - изменение брони
{
    "id": Long
    "status": ["CANCELED", "ACTIVE"],
    "startDatetime": Timestamp,
    "endDatetime": Timestamp,
    "vehicleList": [
        {
        "id": Long
        }
    ],
    "user": {
        "id": Long
    }
}
Delete /Reservation/id - удаление брони

Get /user/all - все пользователи
Get /user/id - пользователь по id
Post /user - создание пользователя
{
    "name": String,
    "surname": String
}
Put /user - изменение пользователя
{
    "id": Long,
    "name": String,
    "surname": String
}
Delete /user/id - удаление пользователя


В планах когда нибудь добавить поддержку фотографий, реализовать следующие запросы и прикрутить тг бота.

VehicleAvailability {
"vehicle": VehicleItem,
"nearestReservations: List<Reservation>,
}

VehicleItem {
"id": Vehicle.id,
"name": Vehicle.name,
"image": vechicle.image,
}

Reservation {
"id": Reservation.id,
"startDateTime": Reservation.startDateTime в таймзоне текущего города (по HEADER_CITY_ID),
"endDateTime": Reservation.endDateTime в таймзоне текущего города (по HEADER_CITY_ID),
"user": User,
}

UserReservation {
"id": Reservation.id,
"startDateTime": Reservation.startDateTime в таймзоне текущего города (по HEADER_CITY_ID),
"endDateTime": Reservation.endDateTime в таймзоне текущего города (по HEADER_CITY_ID),
"vehicles": List<VehicleItem>,
}

User {
"id": User.id,
"name": User.name,
"surname": User.surname,
}

