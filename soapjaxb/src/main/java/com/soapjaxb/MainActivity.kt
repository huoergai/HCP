package com.soapjaxb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.StringWriter
import javax.xml.bind.JAXBContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val json = JsonData().apply {
            type = "d:string"
            content = "{\n" +
                    "   \"Version\": \"1.0.0\",\n" +
                    "   \"MasterSecret\": \"CTGi*7d54Fs*eruieud545Jgsdudb===Yhdt6\",\n" +
                    "   \"AppEstateId\": \"ED7632C339E29F48\",\n" +
                    "   \"DeviceInfo\": \"m82|9|WIFI网络|1.0.0|AArch64 Processor rev 4 (aarch64) \",\n" +
                    "   \"ClientType\": 2,\n" +
                    "   \"Language\": 3,\n" +
                    "   \"UserName\": \"t1001\"\n" +
                    "}"
        }

        val loginInfo = GetUserLoginInfo().apply {
            jsonData = json
        }
        val body = ReqBody().apply {
            userLoginInfo = loginInfo
        }

        val info = GetUserLoginInfoReq().apply {
            header = ""
            this.body = body
        }

        val jaxbContext = JAXBContext.newInstance(GetUserLoginInfoReq::class.java)
        val marshaller = jaxbContext.createMarshaller()
        val sw = StringWriter()
        marshaller.marshal(info, sw)

        Log.d("jaxb", "xml= $sw")

    }
}