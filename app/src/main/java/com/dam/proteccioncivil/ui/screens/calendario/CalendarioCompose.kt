package com.dam.proteccioncivil.ui.screens.calendario

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dam.proteccioncivil.R
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.DayOfWeek
import java.time.YearMonth


private val inActiveTextColorLight: Color @Composable get() = Color.LightGray
private val inActiveTextColorDark: Color @Composable get() = Color.Gray
val CalendarTitleColor: Color @Composable get() = Color(161, 102, 42)

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .background(
                color = CalendarTitleColor,
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_left),
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .testTag("MonthTitle"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentMonth.displayText(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.background,
            )
        }
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_right),
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.background,
    )
}


@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> MaterialTheme.colorScheme.tertiary
            DayPosition.InDate, DayPosition.OutDate -> if (isSystemInDarkTheme()) inActiveTextColorDark else inActiveTextColorLight
        }
        if (isSelected)
            Image(
                painter = painterResource(id = R.drawable.lupa),
                contentDescription = "Lupa",
                modifier = Modifier.fillMaxSize(),
            )
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (color in colors) {
                ColoredCircle(
                    color = color,
                )
            }
        }

    }
}

@Composable
fun ColoredCircle(color: Color) {
    Canvas(modifier = Modifier.size(10.dp)) {
        val strokeWidth = 1.5f // Ancho del borde
        val radius = size.minDimension / 2f // Radio del círculo
        drawCircle(color = color, radius = radius - strokeWidth)
        drawCircle(color = Color.Black, radius = radius, style = Stroke(width = strokeWidth))
    }
}

@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.background,
                text = dayOfWeek.displayText(uppercase = true),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
