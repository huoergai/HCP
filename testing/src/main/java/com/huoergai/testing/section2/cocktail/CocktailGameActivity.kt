package com.huoergai.testing.section2.cocktail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.button.MaterialButton
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

        val tvHighScore = findViewById<MaterialTextView>(R.id.cocktail_high_score)
        tvHighScore.text = getString(R.string.template_high_score, 0)
        val tvScore = findViewById<MaterialTextView>(R.id.cocktail_score)
        tvScore.text = getString(R.string.template_score, 0)

        val api: CocktailsApi? = null

        val sp = getSharedPreferences("cocktails", Context.MODE_PRIVATE)

        val repo = CocktailsRepositoryImpl(api, sp)
        val ff = CocktailsGameFactoryImpl(repo)
        val vm = CocktailsGameViewModel(
            repo,
            ff
        )

        vm.initGame()

        vm.getLoading().observe(this) {
            if (it) {
                Toast.makeText(this, "loading game...", Toast.LENGTH_SHORT).show()
                Log.d("CocktailActivity", "loading..")
            } else {
                Toast.makeText(this, "loaded...", Toast.LENGTH_SHORT).show()
                Log.d("CocktailActivity", "loaded...")
            }
        }

        vm.getScore().observe(this) {
            tvScore.text = getString(R.string.template_score, it.current)
            tvHighScore.text = getString(R.string.template_high_score, it.highest)
        }

        val btnNext = findViewById<MaterialButton>(R.id.cocktail_btn_next)
        btnNext.setOnClickListener {
            vm.nextQuestion()
        }

        val btn1 = findViewById<MaterialButton>(R.id.cocktail_btn_sour)
        val btn2 = findViewById<MaterialButton>(R.id.cocktail_btn_tea)

        btn1.setOnClickListener {
            vm.getQuestion().value?.let {
                vm.answerQuestion(it, it.correctOption)
            }
        }

        btn2.setOnClickListener {
            vm.getQuestion().value?.let {
                vm.answerQuestion(it, it.incorrectOption)
            }
        }
    }
}