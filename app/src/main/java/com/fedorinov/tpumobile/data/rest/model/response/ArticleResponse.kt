package com.fedorinov.tpumobile.data.rest.model.response

import com.fedorinov.tpumobile.ui.model.Article
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

/**
 * Ответ запроса на статью.
 * Пример:
 * {
 *   "id": "B4A5DBA4-DDA8-47F4-8C59-2D6ACD3B948B",
 *   "name": "Томский политехнический университет 3",
 *   "text": "<!DOCTYPE HTML> <html> <head> <meta charset=\"utf-8\"> <meta name="viewport" content="width=device-width, initial-scale=1.0"> <meta name="viewport" content="width=device-width, initial-scale=1.0"> <style>::-webkit-scrollbar { width: 12px; } ::-webkit-scrollbar-track { -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); border-radius: 10px; } ::-webkit-scrollbar-thumb { border-radius: 10px; -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5); } img {height: auto !important} div {overflow-wrap: break-word}body { background-image: url(https://internationals.tpu.ru:8080/api/media/img/7CDAEA5F-BC6D-461B-B214-12A975E88A55) }</style></head> <body><h2 style="text-align:center"><strong>Томский политехнический университет</strong></h2> <p style="text-align:center"><img style='max-width: 100%;' alt="" height="534" src="https://internationals.tpu.ru:8080/api/media/img/6C007E67-81F7-4FC9-84DE-EF7EEC72BB8F" width="800" /></p> <p><strong>Полное название университета</strong></p> <p>Федеральное государственное автономное образовательное учреждение высшего образования &laquo;Национальный исследовательский Томский политехнический университет&raquo;</p> <p><strong>Год основания</strong> - 1896</p> <p><strong>Всего студентов</strong> - 11 500+, в том числе 2900+ иностранных граждан из 50+ стран мира</p> <p><strong>Научно-педагогических работников&nbsp;</strong>-1700+</p> <p><strong>Выпускников </strong>-170000+</p> <p><strong>Школы&nbsp;</strong>-10</p> <p><strong>Количество образовательных программ</strong> -179</p> <p>Основные направления:</p> <ul> <li>Природные ресурсы</li> <li>Неразрушающий контроль и безопасность</li> <li>Новые производственные технологии</li> <li>Интеллектуальная энергетика</li> <li>Информационные технологии и робототехника</li> <li>Ядерные технологии</li> <li>Физика высокоэнергетических процессов</li> <li>Химические и биомедицинские технологии</li> <li>Международные обмены - <a href="https://portal.tpu.ru/ciap/partners">85 вузов-партнеров из 26 стран</a></li> </ul> <p>Инфраструктура студенческого городка:</p> <ul> <li>32 учебных и лабораторных корпуса</li> <li>научно-техническая библиотека, содержащая более 2,6 миллиона книг</li> <li>учебно-научный центр &laquo;Исследовательский ядерный реактор&raquo;</li> <li>учебный полигон для геологических практик</li> <li>15 комфортных студенческих общежитий</li> <li>международный культурный центр</li> <li>8 спортивных площадок и стадион</li> <li>25 метровый бассейн</li> </ul> <p>Томский политехнический университет &ndash; первый инженерный вуз в азиатской части страны. ТПУ входит в топ-10 технических университетов России по версии национальных и международных рейтингов.</p> <p>В Университете готовят высококлассных инженеров, которые обладают фундаментальными знаниями, предпринимательским мышлением и умеют решать нестандартные задачи.</p> <p>Энергичную и созидательную атмосферу в ТПУ поддерживают многочисленные творческие спортивные и волонтёрские объединения.</p> <p>Томский политех &ndash; это атмосфера интернационального студенчества, лучший в России кампус, интересная жизнь и учеба, а после &ndash; успешная карьера и самореализация.</p> <p><a href="https://tpu.ru/university/meet-tpu/ratings">ТПУ в мировых и российских рейтингах</a></p> <table align="center" border="0" cellpadding="1" cellspacing="1" style="max-width:100%; width:500px"> <tbody> <tr> <td style="text-align:center"> <p><a href="https://vk.com/tpunews"><img style='max-width: 100%;' alt="" height="43" src="https://internationals.tpu.ru:8080/api/media/img/5E06AE79-49EB-4AC8-AD6D-BBF291AC434A" width="43" /></a></p> </td> <td style="text-align:center"> <p><a href="https://www.youtube.com/user/TPUmedia"><img style='max-width: 100%;' alt="" height="51" src="https://internationals.tpu.ru:8080/api/media/img/40A67ECD-7B39-4C05-994F-13603957A66F" width="45" /></a></p> </td> </tr> </tbody> </table> </body></html>",
 *   "topic": "Томский политехнический университет 3",
 *   "createDate": "16:54 24.04.2023"
 * }
 *
 */
data class ArticleResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("topic") val topic: String? = null,
    @SerializedName("createDate") val createDate: String? = null
) {
    fun toArticle() =
        Article(
            id = UUID.fromString(id),
            name = name,
            text = text,
            topic = topic,
            createDate = Date(
                LocalDateTime
                    .parse(createDate, DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")) // 16:43 22.11.2022
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli())

        )
}
