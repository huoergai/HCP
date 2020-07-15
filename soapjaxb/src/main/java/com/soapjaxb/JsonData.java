package com.soapjaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * D&T: 2020-07-16 02:06
 * Des:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class JsonData {

    @XmlAttribute(namespace = "http://www.w3.org/2001/XMLSchema-instance")
    private String type;
    @XmlValue
    private String content;

    public JsonData() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
