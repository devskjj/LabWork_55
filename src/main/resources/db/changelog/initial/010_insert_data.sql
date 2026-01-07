INSERT INTO roles (id, role)
VALUES (1, 'USER');

INSERT INTO users (id, username, password, email, enabled, role_id)
VALUES (1, 'user1', 'password', 'user1@mail.com', true, 1),
       (2, 'user2', 'password', 'user2@mail.com', true, 1),
       (3, 'user3', 'password', 'user3@mail.com', true, 1),
       (4, 'user4', 'password', 'user4@mail.com', true, 1),
       (5, 'user5', 'password', 'user5@mail.com', true, 1),
       (6, 'user6', 'password', 'user6@mail.com', true, 1),
       (7, 'user7', 'password', 'user7@mail.com', true, 1),
       (8, 'user8', 'password', 'user8@mail.com', true, 1),
       (9, 'user9', 'password', 'user9@mail.com', true, 1),
       (10, 'user10', 'password', 'user10@mail.com', true, 1);

INSERT INTO quizzes (id, title, description, creator_id)
VALUES (1, 'Животные', 'Вопросы о животных', 1),
       (2, 'Цвета', 'Вопросы о цветах и их названиях', 2),
       (3, 'Природа', 'Вопросы о растениях и природных объектах', 3),
       (4, 'География', 'Вопросы о странах, городах и реках', 4),
       (5, 'Математика для детей', 'Простые арифметические задачи', 5);

INSERT INTO questions (id, quiz_id, question_text)
VALUES (1, 1, 'Какое животное король джунглей?'),
       (2, 1, 'Кто умеет летать ночью и издает писк?'),
       (3, 1, 'Какое животное живет в воде и крякает?'),
       (4, 1, 'Кто самый большой млекопитающий на земле?'),
       (5, 1, 'Какое животное умеет менять цвет кожи для маскировки?');

INSERT INTO questions (id, quiz_id, question_text)
VALUES (6, 2, 'Какой цвет смешивается из красного и синего?'),
       (7, 2, 'Какой цвет символизирует траву?'),
       (8, 2, 'Какой цвет солнца?'),
       (9, 2, 'Какой цвет образуется при смешении желтого и синего?'),
       (10, 2, 'Какой цвет неба в ясный день?');

INSERT INTO questions (id, quiz_id, question_text)
VALUES (11, 3, 'Как называется дерево с шишками и иголками?'),
       (12, 3, 'Какой цвет имеют листья большинства деревьев летом?'),
       (13, 3, 'Как называется большой водоем с пресной водой?'),
       (14, 3, 'Как называется природное явление, когда идет снег?'),
       (15, 3, 'Какое растение производит мед?');

INSERT INTO questions (id, quiz_id, question_text)
VALUES (16, 4, 'Столица России?'),
       (17, 4, 'Какая самая большая страна в мире по территории?'),
       (18, 4, 'На каком континенте находится Египет?'),
       (19, 4, 'Как называется самая длинная река в мире?'),
       (20, 4, 'Страна известная кенгуру и коалой?');

INSERT INTO questions (id, quiz_id, question_text)
VALUES (21, 5, 'Сколько будет 2 + 3?'),
       (22, 5, 'Сколько будет 10 - 4?'),
       (23, 5, 'Сколько будет 3 * 3?'),
       (24, 5, 'Сколько будет 12 / 4?'),
       (25, 5, 'Какое число идет после 7?');

INSERT INTO options (question_id, option_text, is_correct)
VALUES (1, 'Слон', false),
       (1, 'Лев', true),
       (1, 'Тигр', false),
       (1, 'Обезьяна', false),

       (2, 'Летучая мышь', false),
       (2, 'Воробей', false),
       (2, 'Сова', true),
       (2, 'Бабочка', false),

       (3, 'Утка', true),
       (3, 'Кот', false),
       (3, 'Собака', false),
       (3, 'Лягушка', false),

       (4, 'Слон', false),
       (4, 'Синий кит', true),
       (4, 'Жираф', false),
       (4, 'Носорог', false),

       (5, 'Змея', false),
       (5, 'Ящерица', false),
       (5, 'Хамелеон', true),
       (5, 'Краб', false);

INSERT INTO options (question_id, option_text, is_correct)
VALUES (6, 'Зеленый', false),
       (6, 'Фиолетовый', true),
       (6, 'Оранжевый', false),
       (6, 'Коричневый', false),

       (7, 'Зеленый', true),
       (7, 'Красный', false),
       (7, 'Желтый', false),
       (7, 'Синий', false),

       (8, 'Синий', false),
       (8, 'Красный', false),
       (8, 'Зеленый', false),
       (8, 'Желтый', true),

       (9, 'Зеленый', true),
       (9, 'Фиолетовый', false),
       (9, 'Оранжевый', false),
       (9, 'Красный', false),

       (10, 'Красный', false),
       (10, 'Желтый', false),
       (10, 'Синий', true),
       (10, 'Зеленый', false);

INSERT INTO options (question_id, option_text, is_correct)
VALUES (11, 'Ель', true),
       (11, 'Береза', false),
       (11, 'Клен', false),
       (11, 'Дуб', false),

       (12, 'Желтый', false),
       (12, 'Зеленый', true),
       (12, 'Красный', false),
       (12, 'Синий', false),

       (13, 'Море', false),
       (13, 'Река', false),
       (13, 'Пруд', false),
       (13, 'Озеро', true),

       (14, 'Дождь', false),
       (14, 'Туман', false),
       (14, 'Снегопад', true),
       (14, 'Град', false),

       (15, 'Тля', false),
       (15, 'Медоносная пчела', true),
       (15, 'Оса', false),
       (15, 'Шмель', false);

INSERT INTO options (question_id, option_text, is_correct)
VALUES (16, 'Москва', true),
       (16, 'Санкт-Петербург', false),
       (16, 'Казань', false),
       (16, 'Новосибирск', false),

       (17, 'США', false),
       (17, 'Канада', false),
       (17, 'Китай', false),
       (17, 'Россия', true),

       (18, 'Африка', true),
       (18, 'Европа', false),
       (18, 'Азия', false),
       (18, 'Южная Америка', false),

       (19, 'Янцзы', false),
       (19, 'Амазонка', false),
       (19, 'Нил', true),
       (19, 'Миссисипи', false),

       (20, 'Индия', false),
       (20, 'Австралия', true),
       (20, 'Бразилия', false),
       (20, 'Южная Африка', false);

INSERT INTO options (question_id, option_text, is_correct)
VALUES (21, '5', true),
       (21, '4', false),
       (21, '6', false),
       (21, '3', false),

       (22, '7', false),
       (22, '6', true),
       (22, '5', false),
       (22, '8', false),

       (23, '6', false),
       (23, '12', false),
       (23, '8', false),
       (23, '9', true),

       (24, '4', false),
       (24, '6', false),
       (24, '3', true),
       (24, '2', false),

       (25, '6', false),
       (25, '8', true),
       (25, '7', false),
       (25, '9', false);

INSERT INTO quiz_results (id, user_id, quiz_id, score)
VALUES (1, 1, 1, 85),
       (2, 2, 1, 92),
       (3, 3, 1, 78),
       (4, 4, 1, 95),
       (5, 5, 1, 88),
       (6, 6, 1, 74),
       (7, 7, 1, 67),
       (8, 8, 1, 90),
       (9, 9, 1, 81),
       (10, 10, 1, 99);

INSERT INTO quiz_results (id, user_id, quiz_id, score)
VALUES (11, 1, 2, 80),
       (12, 2, 2, 85),
       (13, 3, 2, 78),
       (14, 4, 2, 92),
       (15, 5, 2, 87),
       (16, 6, 2, 73),
       (17, 7, 2, 90),
       (18, 8, 2, 68),
       (19, 9, 2, 95),
       (20, 10, 2, 88);

INSERT INTO quiz_results (id, user_id, quiz_id, score)
VALUES (21, 1, 3, 75),
       (22, 2, 3, 82),
       (23, 3, 3, 79),
       (24, 4, 3, 88),
       (25, 5, 3, 91),
       (26, 6, 3, 67),
       (27, 7, 3, 85),
       (28, 8, 3, 70),
       (29, 9, 3, 96),
       (30, 10, 3, 89);

INSERT INTO quiz_results (id, user_id, quiz_id, score)
VALUES (31, 1, 4, 95),
       (32, 2, 4, 89),
       (33, 3, 4, 93),
       (34, 4, 4, 87),
       (35, 5, 4, 90),
       (36, 6, 4, 72),
       (37, 7, 4, 77),
       (38, 8, 4, 85),
       (39, 9, 4, 98),
       (40, 10, 4, 80);

INSERT INTO quiz_results (id, user_id, quiz_id, score)
VALUES (41, 1, 5, 100),
       (42, 2, 5, 85),
       (43, 3, 5, 92),
       (44, 4, 5, 78),
       (45, 5, 5, 88),
       (46, 6, 5, 65),
       (47, 7, 5, 70),
       (48, 8, 5, 95),
       (49, 9, 5, 90),
       (50, 10, 5, 82);

INSERT INTO top_scores (result_id, score)
SELECT id, score
FROM quiz_results
ORDER BY score DESC LIMIT 10;
