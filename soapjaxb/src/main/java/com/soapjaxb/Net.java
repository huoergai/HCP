package com.soapjaxb;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * D&T: 2020-07-16 04:05
 * Des:
 */
public class Net {
    private static final String url = "";
    private static final int time_out = 30000;
    private static final String soapAction = "";

    public void get() {

        HttpTransportSE ht = new HttpTransportSE(url, time_out);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        SoapObject bodyContent = new SoapObject();

        GetUserInfoReqJsonData jsonObj = new GetUserInfoReqJsonData(
                "1.0.0",
                "CTGi*7d54Fs*eruieud545Jgsdudb===Yhdt6",
                "ED7632C339E29F48",
                "m82|9|WIFI网络|1.0.0|AArch64 Processor rev 4 (aarch64)",
                2,
                3,
                "t1001"
        );

        JsonData json = new JsonData();
        // type = "d:string"
        json.setContent(jsonObj.toString());

        GetUserLoginInfo loginInfo = new GetUserLoginInfo();
        loginInfo.setId("o0");
        loginInfo.setRoot("1");
        loginInfo.setJsonData(json);

        ReqBody body = new ReqBody();
        body.setUserLoginInfo(loginInfo);

        GetUserLoginInfoReq info = new GetUserLoginInfoReq();
        info.setHeader("");
        info.setBody(body);

        bodyContent.addProperty("GetUserLoginInfo", info);
        envelope.bodyOut = bodyContent;

        try {
            ht.call(soapAction, envelope);
            SoapObject resp = (SoapObject) envelope.bodyIn;
            resp.getProperty("");
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
