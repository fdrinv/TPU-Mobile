package com.fedorinov.tpumobile.logic.calendar

import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R
import java.util.Calendar
import java.util.TimeZone

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

        fun lastDaysInPreviousMonth(delta: Int): List<Day> {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.add(Calendar.MONTH, -1)
            val daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            buildList {
                for (day in daysOfMonth..delta) {
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

        fun generateCalendarDays(): List<Day> {
            buildList<Day> {
                // - Получаем номер первого дня недели текущего месяца
                val firstDayOfWeekCurrentMonth = getFirstDayOfWeekCurrentMonth()

                for (numberOfDay in 1 .. 36){

                    if (firstDayOfWeekCurrentMonth > 2) {
                        lastDaysInPreviousMonth(7 - firstDayOfWeekCurrentMonth)
                    }

                }
            }
        }

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