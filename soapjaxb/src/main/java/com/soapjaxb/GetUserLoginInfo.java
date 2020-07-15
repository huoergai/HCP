package com.soapjaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * D&T: 2020-07-16 01:02
 * Des:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = "http://tempuri.org/")
public class GetUserLoginInfo {
    @XmlAttribute
    private String id;

    @XmlAttribute(namespace = "http://schemas.xmlsoap.org/soap/encoding")
    private String root;

    @XmlElement(name = "JsonData", namespace = "http://www.w3.org/2001/XMLSchema-instance")
    private JsonData jsonData;

    public GetUserLoginInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public JsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(JsonData json) {
        this.jsonData = json;
    }

    @Override
    public String toString() {
        return "GetUserLoginInfo{" +
                "id='" + id + '\'' +
                ", root='" + root + '\'' +
                ", jsonData=" + jsonData +
                '}';
    }
}
