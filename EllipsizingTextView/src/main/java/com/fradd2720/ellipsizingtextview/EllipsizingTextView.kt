package com.fradd2720.ellipsizingtextview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.floor

class EllipsizingTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var mMaxLines = maxLines
    private var isMaxLineAuto = false
    private var isShowing = false

    companion object {
        const val MAXLINES_AUTO = -1
    }

    init {
        maxLineAuto()
    }

    @SuppressLint("SetTextI18n")
    override fun setText(text: CharSequence?, type: BufferType?) {
        if (layout != null) isShowing = (visibility == VISIBLE)
        if (isShowing && isMaxLineAuto && text != null) visibility = View.INVISIBLE

        super.setText(text, type)

        if (!isShowing && visibility != View.INVISIBLE) return
        if (lineCount > mMaxLines) {
            super.setText(
                "${text!!.substring(0 until layout.getLineEnd(mMaxLines - 1) - 3)}...", type
            )
        }
        visibility = View.VISIBLE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        maxLineAuto(maxLines)
        caculateMaxLine()
    }

    private fun maxLineAuto(line: Int = mMaxLines) {
        mMaxLines = if (line == MAXLINES_AUTO) Int.MAX_VALUE else line
        isMaxLineAuto = (mMaxLines == MAXLINES_AUTO || mMaxLines == Int.MAX_VALUE)
    }

    private fun caculateMaxLine() {
        if (layout != null && isMaxLineAuto) mMaxLines =
            (floor(measuredHeight / (layout.paint.getFontMetricsInt(null) * layout.spacingMultiplier + layout.spacingAdd))).toInt(); text =
            text
    }
}