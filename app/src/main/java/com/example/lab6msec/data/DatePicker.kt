package com.example.lab6msec.data

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePick(
    time: MutableState<Long>
) {
    val dateState = rememberDatePickerState()
    var dateDialogController = remember { mutableStateOf(false) }
    if (dateState.selectedDateMillis != null){
        time.value = dateState.selectedDateMillis!! / 1000
    }
    Text(
        text = "" + SimpleDateFormat("MM/dd/yyyy").format(Date(time.value * 1000) ) + "   ",
        fontSize = 23.sp
    )
    Button(onClick = {
        dateDialogController.value = true
    },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff5d00ff)
        )
    ){
        Text(text = " Date", fontSize = 25.sp)
    }
    if (dateDialogController.value) {
        DatePickerDialog(
            onDismissRequest = { dateDialogController.value = false },
            confirmButton = {
                TextButton(onClick = {
                    dateDialogController.value = false
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dateDialogController.value = false
                }) {
                    Text(text = "No")
                }
            }
        ) {
            DatePicker(state = dateState)
        }
    }
}