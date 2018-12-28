package com.tumoyakov.demonavigation.data.files

import android.os.Environment
import com.tickaroo.tikxml.TikXml
import com.tumoyakov.demonavigation.domain.entity.ValCurs
import okio.Buffer
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


class CbrFile {
    private val LOG_TAG = "myLogs"
    private val FILENAME = "valcurs.xml"
    private val DIR_SD = "CBRFiles"

    fun xmlToFile(valCurs: ValCurs) {
        val tikXml = TikXml.Builder()
            .writeDefaultXmlDeclaration(true)
            .build()
        val buf = Buffer()
        tikXml.write(buf, valCurs).toString()
        writeFileSD(buf.readUtf8LineStrict())
    }

    private fun writeFileSD(data: String) {
        // проверяем доступность SD
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED
        ) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState())
            return
        }
        // получаем путь к SD
        var sdPath = Environment.getExternalStorageDirectory()
        // добавляем свой каталог к пути
        sdPath = File(sdPath.absolutePath + "/" + DIR_SD)
        // создаем каталог
        sdPath.mkdirs()
        // формируем объект File, который содержит путь к файлу
        val sdFile = File(sdPath, FILENAME)
        try {
            // открываем поток для записи
            val bw = BufferedWriter(FileWriter(sdFile))
            // пишем данные
            bw.write(data)
            // закрываем поток
            bw.close()
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}