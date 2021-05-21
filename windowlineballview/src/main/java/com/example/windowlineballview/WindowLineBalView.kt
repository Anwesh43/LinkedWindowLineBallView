package com.example.windowlineballview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context
import android.app.Activity

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#311B92",
    "#00C853",
    "#00C853",
    "#C51162"
).map {
    Color.parseColor(it)
}.toTypedArray()
val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 3.9f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val rFactor : Float = 12.9f
val deg : Float = 45f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = 1f / this

fun Canvas.drawWindowLineBall(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    val r : Float = Math.min(w, h) / rFactor
    save()
    translate(w / 2, h / 2)
    for (j in 0..1) {
        save()
        drawRect(RectF(-size / 2, -size / 2, size * 0.5f * (1 - sf1), size / 2), paint)
        drawLine(0f, -size * 0.5f * sf2, 0f, size * 0.5f * sf2, paint)
        for (i in 0..1) {
            save()
            translate((-size / 2) * sf4, (-size / 2) * sf4)
            drawCircle(0f, 0f, r * sf3, paint)
            restore()
        }
        restore()
    }
    restore()
}

fun Canvas.drawWLBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawWindowLineBall(scale, w, h, paint)
}