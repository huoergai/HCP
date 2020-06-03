package com.huoergai.bitmap_drawable

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * D&T: 2020-06-03 02:36 PM
 * Des:
 */
class MaterialEditText : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initAttrs(context, attributeSet)
    }

    private val paddingRect: Rect = Rect()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val txtSize = Utils.dp2px(12f)
    private val textMargin = Utils.dp2px(8f).toInt()
    private val verticalOffset = Utils.dp2px(6f)
    private val horizontalOffset = Utils.dp2px(5f)
    private val extraOffset = Utils.dp2px(16f)

    var userLabel: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                refreshPadding(field)
            }
        }

    private var floatLabelShown: Boolean = false
    var floatLabelFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var showLabelAnim: ObjectAnimator =
        ObjectAnimator.ofFloat(MaterialEditText@ this, "floatLabelFraction", 0f, 1.0f)
            .setDuration(500)

    init {
        paint.textSize = txtSize
        paint.color = Color.parseColor("#dddddd")
        refreshPadding(userLabel)

        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!floatLabelShown && !TextUtils.isEmpty(s)) {
                    // show
                    floatLabelShown = true
                    showLabelAnim.start()
                } else if (floatLabelShown && TextUtils.isEmpty(s)) {
                    // hide
                    floatLabelShown = false
                    showLabelAnim.reverse()
                }
            }
        })
    }

    private fun initAttrs(context: Context, attr: AttributeSet) {
        // 从 attr 中过滤出数组中的属性及其对应值;
        // 由上可知, 可通过传入自定义的数组以获取该 View 的任意属性:
        // 例 new int[]{android.R.attr.layout_width, android.R.attr.colorAccent}
        val ta = context.obtainStyledAttributes(attr, R.styleable.MaterialEditText)
        // 用序号取出对应位置的值
        userLabel = ta.getBoolean(R.styleable.MaterialEditText_useLabel, false)
        ta.recycle()
    }

    private fun refreshPadding(userLabel: Boolean) {
        background.getPadding(paddingRect)
        if (userLabel) {
            setPadding(
                paddingRect.left,
                paddingRect.top + txtSize.toInt() + textMargin,
                paddingRect.right,
                paddingRect.bottom
            )
        } else {
            setPadding(
                paddingRect.left,
                paddingRect.top,
                paddingRect.right,
                paddingRect.bottom
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {

            if (userLabel) {
                // display hint text
                paint.alpha = (floatLabelFraction * 0xff).toInt()
                val labelExtraOffset = extraOffset * floatLabelFraction
                //  Log.d("MET", "floatLabelFraction=$floatLabelFraction")
                it.drawText(
                    hint.toString(),
                    horizontalOffset,
                    verticalOffset + labelExtraOffset,
                    paint
                )

            }

        }
    }
}