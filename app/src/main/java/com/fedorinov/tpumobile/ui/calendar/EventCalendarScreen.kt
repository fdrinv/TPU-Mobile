package com.fedorinov.tpumobile.ui.calendar

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.logic.calendar.CalendarManipulation
import com.fedorinov.tpumobile.logic.utils.EVENT_DATE_PATTERN
import com.fedorinov.tpumobile.logic.utils.toString
import com.fedorinov.tpumobile.ui.common.icons.CalendarAction
import com.fedorinov.tpumobile.ui.common.icons.MenuAction
import com.fedorinov.tpumobile.ui.common.text.TextBLarge
import com.fedorinov.tpumobile.ui.common.text.TextBSmall
import com.fedorinov.tpumobile.ui.common.text.TextLSmall
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.PADDING_SMALL
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import com.fedorinov.tpumobile.ui.theme.weekParity
import com.ramcosta.composedestinations.annotation.Destination
import java.util.Date

private const val NUMBER_DAYS_IN_WEEK = 6

@Destination
@Composable
fun EventCalendarScreen() {
    EventCalendarScreenStateless()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCalendarScreenStateless(
    onMenuClicked: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { EventCalendarTopBar(onMenuClicked = onMenuClicked) },
        content = { paddingValues ->
            EventCalendarContent(paddingValues = paddingValues)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCalendarTopBar(onMenuClicked: () -> Unit) {
    Column {
        TopAppBar(
            navigationIcon = { MenuAction(onClick = onMenuClicked) },
            actions = { CalendarAction {} },
            title = { Text(stringResource(R.string.title_event_calendar)) }
        )
        Divider(modifier = Modifier.padding(horizontal = PADDING_BIG))
        CurrentDateAndEvent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PADDING_BIG, vertical = PADDING_BIG)
        )
    }
}

@Composable
private fun EventCalendarContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        CalendarGrid()
    }
}

@Composable
private fun CurrentDateAndEvent(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            // - Текущая дата
            TextBLarge(
                text = Date().toString(EVENT_DATE_PATTERN),
                fontWeight = FontWeight.Bold
            )
            TextBSmall(
                text = "Четная неделя".uppercase(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.weekParity
            )
        }
        // - Число событий на сегодня
        TextBLarge(
            text = "CЕГОДНЯ СОБЫТИЙ НЕТ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun CalendarGrid(
    modifier: Modifier = Modifier
) {
    // - Получаем число дней в текущем месяце
    val numberOfDays = CalendarManipulation.getNumberOfDaysInCurrentMonth()
    // - Получаем текущий день
    val currentDay = CalendarManipulation.getCurrentDayNumber()
    Log.i("CalendarGrid", "last day of month in the previous month ${CalendarManipulation.getLastDayOfMonthPreviousMonth()}")
    Log.i("CalendarGrid", "last day of week in the previous month ${CalendarManipulation.getLastDayOfWeekPreviousMonth()}")
    Log.i("CalendarGrid", "first day of week in the current month ${CalendarManipulation.getFirstDayOfWeekCurrentMonth()}")
    Column(modifier = modifier) {
        LazyVerticalGrid(columns = GridCells.Fixed(NUMBER_DAYS_IN_WEEK)) {
            items(CalendarManipulation.DayOfWeek.values()) { day ->
                TextLSmall(
                    text = day.shortName.uppercase(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(PADDING_SMALL)
                )
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(NUMBER_DAYS_IN_WEEK)) {
            items(count = numberOfDays) { day ->
                DayItem(
                    day = day + 1,
                    currentDay = currentDay
                )
            }
        }
    }
}

@Composable
private fun DayItem(
    day: Int,
    currentDay: Int
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .border(BorderStroke(Dp.Hairline, Color.Black))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(PADDING_SMALL)
        ) {
            Text("1")
            Text("2")
        }
        // - День недели (выделяем текущий день недели)
        Surface(
            color = if (day == currentDay) MaterialTheme.colorScheme.primary else Color.Unspecified,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(PADDING_SMALL)
                .sizeIn(24.dp)
        ) {
            Text(
                text = day.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(PADDING_SMALL)
                    .sizeIn(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun EventCalendarScreenPreview() {
    TPUMobileTheme {
        EventCalendarScreenStateless()
    }
}