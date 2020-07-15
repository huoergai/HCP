package com.soapjaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * D&T: 2020-07-16 01:02
 * Des:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ReqBody {
    @XmlElement(name = "GetUserLoginInfo")
    private GetUserLoginInfo userLoginInfo;

    public ReqBody() {
    }

    public GetUserLoginInfo getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(GetUserLoginInfo userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

    @Override
    public String toString() {
        return "ReqBody{" +
                "userLoginInfo=" + userLoginInfo +
                '}';
    }
}
