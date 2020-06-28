package com.huoergai.testing.section2.cocktail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.huoergai.testing.R

/**
 * D&T: 2020-06-27 09:45
 * Des:
 */
class CocktailGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_game)

        findViewById<MaterialTextView>(R.id.cocktail_high_score).text =
            getString(R.string.template_high_score, 0)
        findViewById<MaterialTextView>(R.id.cocktail_score).text =
            getString(R.string.template_score, 0)


    }
}