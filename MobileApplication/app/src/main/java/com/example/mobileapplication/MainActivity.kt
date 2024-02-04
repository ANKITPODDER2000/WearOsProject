package com.example.mobileapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.uicomponent.GetBasicButton
import com.example.mobileapplication.viemodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainViewModel: MainViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(mainViewModel)
        }
    }
}


@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val connectedNodeState by mainViewModel.connectedNodeState.collectAsState()
    val checkConnectedDeviceState by mainViewModel.checkConnectedDeviceState.collectAsState()
    val checkCurrentDeviceState by mainViewModel.checkCurrentDeviceState.collectAsState()
    val currentNodeState by mainViewModel.currentNodeState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp)
    ) {
        GetBasicButton("Check Connected Node", Modifier.fillMaxWidth(), RoundedCornerShape(4.dp)) {
            mainViewModel.checkConnectedNodes(context)
        }
        if (checkConnectedDeviceState) ConnectedNodesInfo(nodes = connectedNodeState)

        GetBasicButton("Check Current Node", Modifier.fillMaxWidth(), RoundedCornerShape(4.dp)) {
            mainViewModel.checkCurrentNode(context)
        }
        if (checkCurrentDeviceState) {
            currentNodeState?.let {
                ConnectedNodeInfo(it.displayName, it.id, it.isNearby)
            }
        }

        GetBasicButton("Send Asset", Modifier.fillMaxWidth(), RoundedCornerShape(4.dp)) {
            mainViewModel.sendAsset(context)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(MainViewModel())
}