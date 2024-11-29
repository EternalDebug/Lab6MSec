package com.example.lab6msec.screens

import com.example.lab6msec.R
import com.example.lab6msec.navigation.NavigationDestination
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.lab6msec.data.DatePick
import com.example.lab6msec.data.HRep
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.runtime.LaunchedEffect
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import com.example.lab6msec.data.CaloriesCompose

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = "Ваши калории: "
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    repo: HRep,
    navigateToAdd: () -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = HomeDestination.titleRes,
            canNavigateBack = false
        )
        Button(onClick = {
            navigateToAdd()
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff5d00ff)
            )
        ){
            Text("Добавление данных", fontSize = 19.sp)
        }
        var key = remember {mutableStateOf(0)}
        var list = remember {mutableStateOf(listOf<TotalCaloriesBurnedRecord>())}
        var calories = remember {mutableStateOf(0.0)}
        var start = remember {mutableStateOf(0L)}
        val f = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        var final = remember {mutableStateOf(f)}

        LaunchedEffect(start.value, final.value, key.value) {
            calories.value = repo.aggregateCalories2(start.value, final.value).inKilocalories
            list.value = repo.readCaloriesByTimeRange(start.value, final.value)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("C ", fontSize = 23.sp)
            DatePick(time = start)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("По ", fontSize = 23.sp)
            DatePick(time = final)
        }

        Text("Всего за период сожжено: " + calories.value, fontSize = 23.sp)


        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            for(record in list.value){
                item{
                    CaloriesCompose(
                        record = record,
                        modifier = modifier,
                        repo = repo,
                        key = key
                    )
                }
            }
        }

    }
}