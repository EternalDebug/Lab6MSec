package com.example.lab6msec.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.lab6msec.R
import com.example.lab6msec.data.PERMISSIONS
import com.example.lab6msec.data.getPermissionStates
import com.example.lab6msec.navigation.NavigationDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState

object PermDestination : NavigationDestination {
    override val route = "perm"
    override val titleRes = "Требуются разрешения: "
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GivePerms(
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit,
    appCont: Context
) {
    var states = mutableListOf<PermissionState>()
    val permisesState = mutableIntStateOf(0)
    var permises by permisesState
    permises+= getPermissionStates(states)

    if (permises == PERMISSIONS.size) {
        navigateHome()
    }

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = PermDestination.titleRes,
            canNavigateBack = false
        )
        for (state in states){
            PermissionRequired(
                permissionState = state,
                permissionNotGrantedContent = {
                    Text("")
                    Button(onClick = {
                        state.launchPermissionRequest()
                    },
                        modifier = modifier
                    ){
                        Text("Give me " + state.permission.split('.').get(3).replace('_', ' ').lowercase() + "?", fontSize = 19.sp)
                    }
                },
                permissionNotAvailableContent = {
                    Text("В настройках google health разрешите " + state.permission.split('.').get(3), fontSize = 19.sp)
                },
                content = {
                    permises++
                }
            )
        }
        Button(onClick = {
            if (permises == PERMISSIONS.size) {
                navigateHome()
            }
            else{
                val toast = Toast.makeText(appCont,R.string.not_all_perms, Toast.LENGTH_SHORT)
                toast.show()
            }
        },
            modifier = modifier
        ){
            Text("На главную")
        }
    }
}