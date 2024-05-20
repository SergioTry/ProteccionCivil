import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Servicio
import com.dam.proteccioncivil.ui.screens.calendario.CalendarioVM
import com.dam.proteccioncivil.ui.screens.calendario.Day
import com.dam.proteccioncivil.ui.screens.calendario.MonthHeader
import com.dam.proteccioncivil.ui.screens.calendario.SimpleCalendarTitle
import com.dam.proteccioncivil.ui.screens.calendario.rememberFirstCompletelyVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Calendario(
    calendarioVM: CalendarioVM,
    servicios: Map<LocalDate, List<Servicio>>,
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(200) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(200) } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek(DayOfWeek.MONDAY) } // Available from the library
    var selection by remember { mutableStateOf<CalendarDay?>(null) }

    val servicesInSelectedDate = remember {
        derivedStateOf {
            val date = selection?.date
            if (date == null) emptyList() else servicios[date].orEmpty()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfRow,
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)

        LaunchedEffect(visibleMonth) {
            // Clear selection if we scroll to a new month.
            selection = null
        }
        SimpleCalendarTitle(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        HorizontalCalendar(
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.LightGray),
            state = state,
            dayContent = { day ->
                val colors = if (day.position == DayPosition.MonthDate) {
                    calendarioVM.getServicioColors(servicios[day.date]?.firstOrNull())
                } else {
                    emptyList()
                }

                Day(
                    day = day,
                    isSelected = selection == day,
                    colors = colors,
                ) { clicked ->
                    selection = clicked
                }
            },
            monthHeader = {
                MonthHeader(
                    modifier = Modifier.padding(vertical = 8.dp),
                    daysOfWeek = daysOfWeek,
                )
            },
        )
        HorizontalDivider(color = Color.Gray)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = servicesInSelectedDate.value) { servicio ->
                ServiceInformation(servicio)
            }
        }
    }
}


@Composable
private fun LazyItemScope.ServiceInformation(servicio: Servicio) {
    Column(
        modifier = Modifier
            .fillParentMaxWidth()
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        servicio.guardia?.let { GuardiaCalendarCard(it,Modifier.weight(1f)) }
        servicio.infomur?.let { InfomurCalendarCard(it,Modifier.weight(1f)) }
        servicio.preventivo?.let { PreventivoCalendarCard(it,Modifier.weight(1f)) }
    }
    //HorizontalDivider(thickness = 2.dp, color = pageBackgroundColor)
}

@Composable
fun PreventivoCalendarCard(preventivo: Preventivo,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = if (preventivo.riesgo.toInt() == 0) Color.Green else Color.Red)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.fondo),
                contentDescription = "Imagen asociada a preventivo",
                modifier = Modifier.size(40.dp)
            )
        }
        Text(text = preventivo.titulo, modifier = Modifier.weight(1f))
    }
}

@Composable
fun InfomurCalendarCard(infomur: Infomur,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.Blue)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = infomur.descripcion)
    }
}

@Composable
fun GuardiaCalendarCard(guardia: Guardia,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.Magenta)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = guardia.descripcion)
    }
}
