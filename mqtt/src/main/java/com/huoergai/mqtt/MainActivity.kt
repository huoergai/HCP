package com.huoergai.mqtt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

const val serverUri = "tcp://192.168.1.2:1883"
const val clientId = "001"
const val subscriptionTopic = "pcTopic-1"
const val publishTopic = "androidTopic-1"

/**
 * MQTT 服务测试
 * 具体教程参看 doc/MQTT/ 下的文档
 */
class MainActivity : AppCompatActivity() {

    var mqttClient: MqttAndroidClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPublish = findViewById<Button>(R.id.btn_publish)
        btnPublish.setOnClickListener {
            publishMsg("msg:${System.currentTimeMillis()}")
        }
        mqttClient = MqttAndroidClient(this, serverUri, clientId)
        mqttClient?.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("MainActivity ", "messageArrived: $topic - $message")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("MainActivity ", " connectionLost:$cause")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("MainActivity ", " deliveryComplete:$token")
            }
        })

        val connOption = MqttConnectOptions()
        connOption.userName = "huoergai"
        connOption.password = "10133310".toCharArray()
        connOption.isCleanSession = false
        mqttClient?.connect(connOption, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                subscribeTopic(subscriptionTopic)
                subscribeTopic("test_topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MainActivity ", " onFailure: $serverUri :${exception?.message}")
            }
        })
    }

    private fun publishMsg(message: String) {
        val msg = MqttMessage()
        msg.payload = message.toByteArray()
        msg.qos = 0
        mqttClient?.publish(publishTopic, msg, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MainActivity ", "publish onSuccess:$message")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MainActivity ", "publish onFailure:${exception?.message}")
            }
        })

    }

    private fun subscribeTopic(subTop: String) {
        mqttClient?.subscribe(subTop, 0, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MainActivity ", "subscribe onSuccess:$subTop")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MainActivity ", " onFailure")
            }

        })

    }

}