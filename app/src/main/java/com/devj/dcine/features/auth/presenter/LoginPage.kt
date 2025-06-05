package com.devj.dcine.features.auth.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.util.TableInfo

@Composable
fun AuthPage(modifier: Modifier = Modifier) {

    Scaffold { padding->
        Column(modifier = Modifier.padding(padding)) {


            ElevatedButton(onClick = {

            }) {
                Text("Authenticate")
            }
        }
    }
}