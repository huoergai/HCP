package com.huoergai.hcp.preference

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

class SetFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val ps = PreferenceScreen(context, null).apply { }
        ps.addPreference(CheckBoxPreference(context).apply {
            title = "note"
            key = "note"
            summary = "some summary"
        })

        preferenceScreen = ps
    }
}