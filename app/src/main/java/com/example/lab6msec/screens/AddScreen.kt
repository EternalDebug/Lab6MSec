package com.example.lab6msec.screens

import com.example.lab6msec.R
import com.example.lab6msec.navigation.NavigationDestination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch
import java.time.Instant

object AddDestination : NavigationDestination {
    override val route = "add"
    override val titleRes = "Добавление заметки: "
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    repo: HRep,
    navigateBack: () -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = AddDestination.titleRes,
            canNavigateBack = true,
            navigateUp = navigateBack
        )
        val coroutineScope = rememberCoroutineScope()

        val newCal = remember {mutableStateOf("")}

        OutlinedTextField(
            value = newCal.value,
            onValueChange = { newVal -> newCal.value = newVal },
            label = { Text(stringResource(R.string.enterccal)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        val f = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        var time = remember { mutableLongStateOf(f) }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            //Text("", fontSize = 23.sp)
            DatePick(time = time)
        }
        Button(onClick = {
            coroutineScope.launch {
                repo.insertCalories(
                    count = newCal.value,
                    instant0 = Instant.ofEpochSecond(time.longValue),
                    instant1 = Instant.ofEpochSecond(time.longValue + 1)
                )
                navigateBack()
            }
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff4acc0a)
            )
        ){
            Text("Добавить запись", fontSize = 19.sp)
        }

    }
}