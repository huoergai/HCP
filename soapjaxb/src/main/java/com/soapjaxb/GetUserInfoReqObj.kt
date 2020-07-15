package com.soapjaxb

/**
 * D&T: 2020-07-15 23:01
 * Des:
 */
data class GetUserInfoReqJsonData(
    var Version: String,
    var MasterSecret: String,
    var AppEstateId: String,
    var DeviceInfo: String,
    var ClientType: Int,
    var Language: Int,
    var UserName: String
)