import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Servicio
import com.dam.proteccioncivil.ui.screens.calendario.CalendarTitleColor
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
    val contexto = LocalContext.current
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(200) }
    val endMonth = remember { currentMonth.plusMonths(200) }
    val daysOfWeek = remember { daysOfWeek(DayOfWeek.MONDAY) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }

    val servicesInSelectedDate = remember {
        derivedStateOf {
            val date = selection?.date
            if (date == null) emptyList() else servicios[date].orEmpty()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = ContextCompat.getString(contexto, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
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
                // Borra la selection si se cambia de mes.
                selection = null
            }
            SimpleCalendarTitle(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(
                            state.firstVisibleMonth.yearMonth.previousMonth
                        )
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(
                            state.firstVisibleMonth.yearMonth.nextMonth
                        )
                    }
                },
            )
            HorizontalCalendar(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(MaterialTheme.colorScheme.secondary),
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
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 2.dp)
            ) {
                items(items = servicesInSelectedDate.value)
                { servicio ->
                    ServiceInformation(servicio)
                }
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

        servicio.guardia?.let {
            GuardiaCalendarCard(it)
        }

        servicio.infomur?.let {
            InfomurCalendarCard(it)
        }

        servicio.preventivo?.let {
            PreventivoCalendarCard(it)
        }
    }

}

@Composable
fun PreventivoCalendarCard(preventivo: Preventivo, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = if (preventivo.riesgo.toInt() == 0) Color.Green else Color.Red,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(75.dp)
            .border(3.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                .weight(0.2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                contentScale = ContentScale.FillHeight,
                painter = painterResource(if (preventivo.riesgo.toInt() == 0) R.drawable.preventivos else R.drawable.siren_transparent_png),
                contentDescription = stringResource(if (preventivo.riesgo.toInt() == 0) R.string.preventivos_image else R.string.preventivos_riesgo_image),
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
        Column(
            modifier = Modifier.weight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = preventivo.titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(text = preventivo.descripcion, fontSize = 16.sp, color = Color.Black)
        }
    }
}

@Composable
fun InfomurCalendarCard(infomur: Infomur, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.Blue, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(75.dp)
            .border(3.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                .weight(0.2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                contentScale = ContentScale.FillHeight,
                painter = painterResource(R.drawable.infomur),
                contentDescription = stringResource(R.string.infomur_image),
                modifier = Modifier
                    .background(Color.Cyan, shape = RoundedCornerShape(50))
            )
        }
        Column(
            modifier = Modifier.weight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = infomur.descripcion,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun GuardiaCalendarCard(guardia: Guardia, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color(255, 165, 0), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(75.dp)
            .border(3.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                .weight(0.2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Image(
                contentScale = ContentScale.FillHeight,
                painter = painterResource(R.drawable.guardias),
                contentDescription = stringResource(R.string.guardias_image),
                modifier = Modifier
                    .background(CalendarTitleColor, shape = RoundedCornerShape(50))
            )
        }
        Column(
            modifier = Modifier.weight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = guardia.descripcion,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}
