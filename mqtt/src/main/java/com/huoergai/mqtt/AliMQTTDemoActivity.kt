package com.huoergai.mqtt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.math.BigInteger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * D&T: 2020-07-02 18:07
 * Des:
 */
class AliMQTTDemoActivity : AppCompatActivity() {
    private val TAG = "AiotMqtt"

    /* 设备三元组信息 */
    private val PRODUCTKEY = "a11xsrWmW14"
    private val DEVICENAME = "paho_android"
    private val DEVICESECRET = "tLMT9QWD36U2SArglGqcHCDK9rK9nOrA"

    /* 自动Topic, 用于上报消息 */
    private val PUB_TOPIC = "/$PRODUCTKEY/$DEVICENAME/user/update"

    /* 自动Topic, 用于接受消息 */
    private val SUB_TOPIC = "/$PRODUCTKEY/$DEVICENAME/user/get"

    /* 阿里云Mqtt服务器域名 */
    val host = "tcp://$PRODUCTKEY.iot-as-mqtt.cn-shanghai.aliyuncs.com:443"
    private var clientId: String? = null
    private var userName: String? = null
    private var passWord: String? = null

    var mqttAndroidClient: MqttAndroidClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ali)

        setupMQTT()

        findViewById<Button>(R.id.publish).setOnClickListener { publishMessage("hello IoT") }
    }

    private fun setupMQTT() {
        /* 获取Mqtt建连信息clientId, username, password */
        val aiotMqttOption = AiotMqttOption().getMqttOption(PRODUCTKEY, DEVICENAME, DEVICESECRET)

        clientId = aiotMqttOption?.clientId
        userName = aiotMqttOption?.username
        passWord = aiotMqttOption?.password


        /* 创建MqttConnectOptions对象并配置username和password */
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.userName = userName
        mqttConnectOptions.password = passWord!!.toCharArray()

        /* 创建MqttAndroidClient对象, 并设置回调接口 */
        mqttAndroidClient = MqttAndroidClient(applicationContext, host, clientId)
        mqttAndroidClient?.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable) {
                Log.i(TAG, "connection lost")
            }

            @Throws(java.lang.Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                Log.i(
                    TAG,
                    "topic: " + topic + ", msg: " + String(message.payload)
                )
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {
                Log.i(TAG, "msg delivered")
            }
        })

        mqttAndroidClient?.connect(mqttConnectOptions, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "connect succeed")
                subscribeTopic(SUB_TOPIC)
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                Log.i(TAG, "connect failed")
            }
        })

    }

    /**
     * 订阅特定的主题
     *
     * @param topic mqtt主题
     */
    fun subscribeTopic(topic: String?) {
        mqttAndroidClient?.subscribe(topic, 0, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "subscribed succeed")
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                Log.i(TAG, "subscribed failed")
            }
        })
    }

    /**
     * 向默认的主题/user/update发布消息
     *
     * @param payload 消息载荷
     */
    private fun publishMessage(payload: String) {
        val message = MqttMessage()
        message.payload = payload.toByteArray()
        message.qos = 0
        mqttAndroidClient?.publish(PUB_TOPIC, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "publish succeed!")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.i(TAG, "publish failed!")
            }
        })
    }

    /**
     * MQTT建连选项类，输入设备三元组productKey, deviceName和deviceSecret, 生成Mqtt建连参数clientId，username和password.
     */
    internal class AiotMqttOption(
        var username: String = "",
        var password: String = "",
        var clientId: String = ""
    ) {

        /**
         * 获取Mqtt建连选项对象
         *
         * @param productKey   产品秘钥
         * @param deviceName   设备名称
         * @param deviceSecret 设备机密
         * @return AiotMqttOption对象或者NULL
         */
        fun getMqttOption(
            productKey: String,
            deviceName: String,
            deviceSecret: String
        ): AiotMqttOption? {
            try {
                val timestamp = System.currentTimeMillis()
                // clientId
                this.clientId = productKey + "." + deviceName + "|timestamp=" + timestamp +
                        ",_v=paho-android-1.0.0,securemode=2,signmethod=hmacsha256|"

                // userName
                username = "$deviceName&$productKey"

                // password
                val macSrc = "clientId" + productKey + "." + deviceName + "deviceName" +
                        deviceName + "productKey" + productKey + "timestamp" + timestamp
                val algorithm = "HmacSHA256"
                val secretKeySpec = SecretKeySpec(deviceSecret.toByteArray(), algorithm)
                val mac = Mac.getInstance(algorithm)
                mac.init(secretKeySpec)
                val macRes = mac.doFinal(macSrc.toByteArray())
                password = String.format("%064x", BigInteger(1, macRes))
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return this
        }
    }
}