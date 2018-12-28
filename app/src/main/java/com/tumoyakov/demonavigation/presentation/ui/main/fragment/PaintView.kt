package com.tumoyakov.demonavigation.presentation.ui.main.fragment

import android.content.Context
import android.graphics.*
import android.os.Environment
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class PaintView(
    context: Context,
    private var pwHeight: Int = 400,
    private var pwWidth: Int = 400,
    private var path2: Path,
    private var paint: Paint
) : View(context) {

    init {
        this.setBackgroundColor(Color.WHITE)
    }

    private var bitmap = Bitmap.createBitmap(pwHeight, pwWidth, Bitmap.Config.ARGB_4444)
    private var canvas = Canvas(bitmap)
    private var drawingClassArrayList = ArrayList<DrawingClass>()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        canvas.drawPath(path2, paint)
        if (action == MotionEvent.ACTION_DOWN) {
            path2.moveTo(event.x, event.y)
            path2.lineTo(event.x, event.y)
        } else if (action == MotionEvent.ACTION_MOVE) {
            path2.lineTo(event.x, event.y)
            val pathWithPaint = DrawingClass(path2, paint)
            drawingClassArrayList.add(pathWithPaint)
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawingClassArrayList.size > 0) {
            canvas?.drawPath(
                drawingClassArrayList.last().drawingClassPath,
                drawingClassArrayList.last().drawingClassPaint
            )
        }
    }

    fun getImageArray(): Array<Int> = getIntArray(bitmap)

    fun getTrainArray(group: Int, num: Int): Array<Int> = getIntArray(readTestFile(group, num))

    private fun getIntArray(bm: Bitmap): Array<Int> {
        var array = Array(100*100){0}
        var num = 0
        val step = 6
        for(i in 0 until 100) {
            for(j in 0 until 100) {
                if(bm.getPixel(i*6, j*6) == Color.BLACK) {
                    array[num] = 1
                }
                num++
            }
        }
        return array
    }

    fun clear() {
        path2.reset()
        bitmap.eraseColor(Color.WHITE)
        invalidate()
    }

    fun saveFile(group: String, num: String) {
        var dir = File("${Environment.getExternalStorageDirectory()}", "neuropictures")
        dir.mkdirs()
        var file = File("${Environment.getExternalStorageDirectory()}/neuropictures", "$group $num.png")
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG,0, fos)
            fos?.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun saveTestFile(group: String, num: String): Boolean {
        var dir = File("${Environment.getExternalStorageDirectory()}/neuropictures/test/", "$group")
        dir.mkdirs()
        var file = File("${Environment.getExternalStorageDirectory()}/neuropictures/test/$group", "n $num.png")
        return try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG,0, fos)
            fos.close()
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun readTestFile(group: Int, num: Int): Bitmap {
        val file = File("${Environment.getExternalStorageDirectory()}/neuropictures/test/$group", "n $num.png")
        val immutableBitmap =
            BitmapFactory.decodeFile(file.absolutePath)
        val bm = immutableBitmap.copy(Bitmap.Config.ARGB_4444, true)
        return bm
    }

    fun readFile(group: String, num: String): Bitmap {
        val file = File("${Environment.getExternalStorageDirectory()}/neuropictures", "$group $num.png")
        path2.reset()
        val immutableBitmap =
            BitmapFactory.decodeFile(file.absolutePath)
        bitmap = immutableBitmap.copy(Bitmap.Config.ARGB_4444, true)
        return bitmap
    }
}