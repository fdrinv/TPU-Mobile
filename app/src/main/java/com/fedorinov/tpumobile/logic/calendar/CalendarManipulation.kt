package com.fedorinov.tpumobile.logic.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.logic.calendar.CalendarManipulation.DayOfWeek.*
import java.time.DayOfWeek
import java.time.Period
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.abs

class CalendarManipulation {



    companion object {
        // - Получает число дней в текущем месяце
        fun getNumberOfDaysInCurrentMonth() =
            Calendar.getInstance(TimeZone.getDefault()).getActualMaximum(Calendar.DAY_OF_MONTH)

        fun getCurrentDayNumber() =
            Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)

        fun getFirstDayOfWeekInCurrentMonth(): Int {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        fun getLastDayOfMonthPreviousMonth(): Int {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.add(Calendar.MONTH, -1)
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        fun getLastDayOfWeekPreviousMonth() : Int {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        fun getFirstDayOfWeekCurrentMonth() : Int {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun lastDaysInPreviousMonth(delta: Int): List<Day> {

            val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
            val days = currentDateTime.dayOfWeek

            // - Получаем предыдущий месяц
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.add(Calendar.MONTH, -1)
            // - Получаем число дней в предыдущем месяце
            val daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            return buildList {
                for (day in daysOfMonth - delta..daysOfMonth) {
                    val localCalendar = Calendar.getInstance(TimeZone.getDefault())
                    localCalendar.add(Calendar.MONTH, -1)
                    localCalendar.set(Calendar.DAY_OF_MONTH, day)
                    add(
                        Day(
                            dayOfWeek = DayOfWeek.fromId(localCalendar.get(Calendar.DAY_OF_WEEK)),
                            dayOfMonth = day
                        )
                    )
                }
            }
        }

        /*fun generateCalendarDays(): List<Day> {
            buildList {
                // - Получаем номер первого дня недели текущего месяца
                val firstDayOfWeekCurrentMonth = getFirstDayOfWeekCurrentMonth()
                // - Получаем число дней, которое нужно подтянуть с предыдущего месяца
                val deltaDaysForPreviousMonth =
                    if (firstDayOfWeekCurrentMonth > MONDAY.id)
                        abs(firstDayOfWeekCurrentMonth - MONDAY.id)
                     else 0
                // - Добавляем дни в список дней календаря (всего в календаре 36 дней)
                for (numberOfDay in 1 + deltaDaysForPreviousMonth .. 36) {
                    // - Добавляем дни предыдущего месяца, если текущий месяц начинается не с понедельника
                    if (deltaDaysForPreviousMonth != 0) {
                        addAll(lastDaysInPreviousMonth(abs(firstDayOfWeekCurrentMonth - MONDAY.id)))
                    }

                }
            }
        }*/

    }

    data class Day(
        val dayOfWeek: DayOfWeek?,
        val dayOfMonth: Int
    )

    enum class DayOfWeek(val id: Int, val realName: String, val shortName: String) {
        MONDAY(
            id = 2,
            realName = App.getAppResources()?.getString(R.string.enum_day_monday) ?: "Monday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_monday_short) ?: "mo"
        ),
        TUESDAY(
            id = 3,
            realName = App.getAppResources()?.getString(R.string.enum_day_tuesday) ?: "Tuesday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_tuesday_short) ?: "tu"
        ),
        WEDNESDAY(
            id = 4,
            realName = App.getAppResources()?.getString(R.string.enum_day_wednesday) ?: "Wednesday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_wednesday_short) ?: "we"
        ),
        THURSDAY(
            id = 5,
            realName = App.getAppResources()?.getString(R.string.enum_day_thursday) ?: "Thursday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_thursday_short) ?: "th"
        ),
        FRIDAY(
            id = 6,
            realName = App.getAppResources()?.getString(R.string.enum_day_friday) ?: "Friday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_friday_short) ?: "fr"
        ),
        SATURDAY(
            id = 7,
            realName = App.getAppResources()?.getString(R.string.enum_day_saturday) ?: "Saturday",
            shortName = App.getAppResources()?.getString(R.string.enum_day_saturday_short) ?: "sa"
        );

        companion object {
            fun fromId(id: Int): DayOfWeek? = values().find { it.id == id}
        }
    }

}