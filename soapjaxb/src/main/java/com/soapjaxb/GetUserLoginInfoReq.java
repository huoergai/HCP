package com.soapjaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * D&T: 2020-07-15 23:10
 * Des:
 */
@XmlType(propOrder = {"header", "body"})
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class GetUserLoginInfoReq {

    private String header;

    private ReqBody body;

    public GetUserLoginInfoReq() {
    }

    @XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    public ReqBody getBody() {
        return this.body;
    }

    public void setBody(ReqBody reqBody) {
        body = reqBody;
    }

    @Override
    public String toString() {
        return "GetUserLoginInfoReq{" +
                "header='" + header + '\'' +
                ", body=" + body +
                '}';
    }
}
