package com.soapjaxb

import com.google.gson.GsonBuilder
import org.junit.Test
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val jstr =
            "<v:Envelope xmlns:i=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns:d=\\\"http://www.w3.org/2001/XMLSchema\\\" xmlns:c=\\\"http://schemas.xmlsoap.org/soap/encoding/\\\" xmlns:v=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\">\\n    <v:Header />\\n    <v:Body>\\n        <GetUserLoginInfo xmlns=\\\"http://tempuri.org/\\\" id=\\\"o0\\\" c:root=\\\"1\\\">\\n            <JsonData i:type=\\\"d:string\\\">{\\\"Version\\\":\\\"1.0.0\\\",\\\"MasterSecret\\\":\\\"CTGi*7d54Fs*eruieud545Jgsdudb===Yhdt6\\\",\\\"AppEstateId\\\":\\\"ED7632C339E29F48\\\",\\\"DeviceInfo\\\":\\\"m82|9|WIFI网络|1.0.0|AArch64 Processor rev 4 (aarch64) \\\",\\\"ClientType\\\":2,\\\"Language\\\":3,\\\"UserName\\\":\\\"t1001\\\"}</JsonData>\\n        </GetUserLoginInfo>\\n    </v:Body>\\n</v:Envelope>"

        val gson = GsonBuilder().create()

        val jsonObj = GetUserInfoReqJsonData(
            "1.0.0",
            "CTGi*7d54Fs*eruieud545Jgsdudb===Yhdt6",
            "ED7632C339E29F48",
            "m82|9|WIFI网络|1.0.0|AArch64 Processor rev 4 (aarch64)",
            2,
            3,
            "t1001"
        )

        val json = JsonData().apply {
            // type = "d:string"
            content = jsonObj.toString()
        }
        val loginInfo = GetUserLoginInfo().apply {
            id = "o0"
            root = "1"
            jsonData = json
        }
        val body = ReqBody().apply {
            userLoginInfo = loginInfo
        }

        val info = GetUserLoginInfoReq().apply {
            header = ""
            this.body = body
        }

        // val xml = JaxbUtils.objToXml(info)
        val jaxbContext = JAXBContext.newInstance(GetUserLoginInfoReq::class.java)
        val marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true)
        // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8);
        val sw = StringWriter()
        marshaller.marshal(info, sw)
        println("jaxb xml= $sw")

        // val recoverData = JaxbUtils.xmlToObj(sw.toString(), GetUserLoginInfoReq::class.java)
        val umMarshaller = jaxbContext.createUnmarshaller()
        val ret = umMarshaller.unmarshal(StringReader(sw.toString())) as GetUserLoginInfoReq


        println("recover obj= $ret")
    }
}