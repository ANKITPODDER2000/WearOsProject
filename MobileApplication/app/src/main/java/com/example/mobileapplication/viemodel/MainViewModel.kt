package com.example.mobileapplication.viemodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobileapplication.R
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.Random

class MainViewModel : ViewModel() {

    private val _checkConnectedDeviceState = MutableStateFlow(false)
    val checkConnectedDeviceState = _checkConnectedDeviceState.asStateFlow()

    private val _checkCurrentDeviceState = MutableStateFlow(false)
    val checkCurrentDeviceState = _checkCurrentDeviceState.asStateFlow()

    private val _connectedNodeState = MutableStateFlow(listOf<Node>())
    val connectedNodeState = _connectedNodeState.asStateFlow()

    private val _currentNodeState: MutableStateFlow<Node?> = MutableStateFlow(null)
    val currentNodeState = _currentNodeState.asStateFlow()

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun checkConnectedNodes(context: Context) {
        Log.d(TAG, "checkConnectedNodes: is called... time : ${System.currentTimeMillis()}")
        _checkConnectedDeviceState.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val connectedNodes = Wearable.getNodeClient(context).connectedNodes
            val nodes = Tasks.await(connectedNodes)
            _connectedNodeState.value = nodes
            Log.d(
                TAG,
                "checkConnectedNodes: connected node is : $nodes time : ${System.currentTimeMillis()}"
            )
        }
        Log.d(TAG, "checkConnectedNodes: is called... time : ${System.currentTimeMillis()}")
    }

    fun checkCurrentNode(context: Context) {
        Log.d(TAG, "checkCurrentNode: is called... time : ${System.currentTimeMillis()}")
        _checkCurrentDeviceState.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val currentNodeTask = Wearable.getNodeClient(context).localNode
            val nodes = Tasks.await(currentNodeTask)
            _currentNodeState.value = nodes
            Log.d(
                TAG,
                "checkCurrentNode: current node is : $nodes time : ${System.currentTimeMillis()}"
            )
        }
        Log.d(TAG, "checkCurrentNode: is called... time : ${System.currentTimeMillis()}")
    }

    fun sendAsset(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap =
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.kolkata
                )
            if (bitmap == null) {
                Log.e(TAG, "sendAsset: bitmap is null")
                return@launch
            }
            // val asset = getAssetFromBitmap(bitmap)
            val request = PutDataMapRequest.create("/mobile_application_image").run {
                dataMap.putInt("test_int", Random().nextInt(100000))
                asPutDataRequest()
            }
            val task = Wearable.getDataClient(context).putDataItem(request)
            val dataItem = Tasks.await(task)
            Log.d(TAG, "sendAsset: uri : ${dataItem.uri}")
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Uri : ${dataItem.uri}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getAssetFromBitmap(bitmap: Bitmap): Asset {
        val asset = ByteArrayOutputStream().let {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Asset.createFromBytes(it.toByteArray())
        }
        return asset
    }
}