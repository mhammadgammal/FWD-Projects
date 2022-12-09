package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var buttonBackgroundColor: Int = 0
    private var text = ""
    private var progressLoadingRect = Rect()
    private var txtColor: Int = 0
    private var progressColor = 0
    private var circleProgressColor = 0
    private var buttonProgress = 0
    private var circleProgress = 0
    private val txtSize: Float = 55f
    private var offset = 0
    private val textRect = Rect()
    private var valueAnimator = ValueAnimator()

    private val attrips = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.LoadingButton,
        defStyleAttr,
        0
    )
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = txtSize
        typeface = Typeface.DEFAULT_BOLD
    }
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, _, new ->
        when (new) {
            ButtonState.Loading -> {
                startAnimation()
                requestLayout()
                invalidate()
            }
            ButtonState.Completed -> {
                stopAnim()
                invalidate()
            }
            else -> {}
        }

    }

    private fun startAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, 360).apply {
            duration = 3500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener {
                buttonProgress = it.animatedValue as Int
                circleProgress = ((widthSize / 365) * buttonProgress)
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationCancel(animation: Animator?) {
                    super.onAnimationCancel(animation)
                    buttonProgress = 0
                    circleProgress = 0
                }
            })
            start()
        }
    }

    private fun stopAnim() {
        valueAnimator.cancel()
        buttonProgress = 0
        circleProgress = 0
        invalidate()


    }


    init {
        isClickable = true
        with(attrips){
            buttonBackgroundColor = context.getColor(R.color.colorPrimary)
            txtColor = context.getColor(R.color.white)
            progressColor = context.getColor(R.color.colorPrimaryDark)
            circleProgressColor = context.getColor(R.color.colorAccent)
            recycle()
        }
    }

    fun buttonState(state: ButtonState) {
        this.buttonState = state
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        drawBackground(canvas)
        drawText(canvas, paint)
        if (buttonState == ButtonState.Loading) startButtonAnimating(canvas, paint)
        drawText(canvas, paint)
        canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    private fun drawBackground(canvas: Canvas?){
        canvas?.drawColor(buttonBackgroundColor)
    }

    private fun drawText(canvas: Canvas?, paint: Paint){

        if(buttonState == ButtonState.Loading){
            text = context.getString(R.string.button_loading)
        }else {
            text = context.getString(R.string.download)
        }
        paint.apply {
            getTextBounds(
                text,
                0,
                text.length,
                textRect
            )
            color = context.getColor(R.color.white)
        }

        offset = if (buttonState == ButtonState.Loading) 35 else 0

        val textX = widthSize / 2f - textRect.width() / 2f + 80
        val textY = heightSize / 2f + textRect.height() / 2f - textRect.bottom
        //

        canvas?.drawText(text, textX - offset, textY, paint)
    }

    private fun startButtonAnimating(canvas: Canvas?, paint: Paint){
        paint.color = progressColor
        progressLoadingRect.set(
            widthSize * buttonProgress / 360,
            0,
            widthSize,
            heightSize
        )

        canvas?.drawRect(
            progressLoadingRect, paint
        )

        circleAnimation(canvas, paint)
    }

    private fun circleAnimation(canvas: Canvas?, paint: Paint) {
        paint.apply {
            style = Paint.Style.FILL
            color = circleProgressColor
        }

        val circleStartX = widthSize / 2f + textRect.width() / 2f + 150
        val circleStartY = heightSize / 2f - 20

        val rectF = RectF(
            circleStartX, circleStartY,
            circleStartX + 40, circleStartY + 40)
        canvas?.drawArc(
            rectF,
            buttonProgress.toFloat(),
            360f - buttonProgress.toFloat(), true,
            paint
        )
    }


}