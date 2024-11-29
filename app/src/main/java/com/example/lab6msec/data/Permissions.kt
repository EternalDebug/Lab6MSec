package com.example.lab6msec.data

import androidx.compose.runtime.Composable
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

val PERMISSIONS =
    setOf(
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class)
    )

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun getPermissionStates(states: MutableList<PermissionState>): Int {
    var permises = 0
    PERMISSIONS.forEach {
        val state = rememberPermissionState(it)
        states.add(state)
        if (state.hasPermission) {
            permises++
        }
    }
    return permises
}