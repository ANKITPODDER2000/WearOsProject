package com.example.mobileapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.wearable.Node


@Composable
fun ConnectedNodesInfo(nodes: List<Node>) {
    if (nodes.isEmpty()) {
        Text(
            text = "Not found any connected device...",
            modifier = Modifier.padding(16.dp, 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    } else {
        LazyColumn {
            items(nodes) {
                ConnectedNodeInfo(it.displayName, it.id, it.isNearby)
            }
        }
    }
}

@Composable
fun ConnectedNodeInfo(
    nodeName: String,
    id: String,
    isNearby: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        tonalElevation = 2.dp,
        modifier = modifier
            .padding(0.dp, 16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp, 8.dp)) {
            Text(text = nodeName.replace("_", " "), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Id : $id")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "isNearby device : $isNearby")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectedNodeInfo() {
    ConnectedNodeInfo("Wear_OS_Small_Round_API_33", "c226634d", true)
}