package com.tumoyakov.demonavigation.data.repository

import com.tumoyakov.demonavigation.data.files.CbrFile
import com.tumoyakov.demonavigation.data.network.CbrApi
import com.tumoyakov.demonavigation.data.network.CbrApiHolder
import com.tumoyakov.demonavigation.domain.entity.ValCurs
import io.reactivex.Single

class CbrRepository(
    private val api: CbrApi = CbrApiHolder.api,
    private val cbrFile: CbrFile = CbrFile()
) {
    fun getValute(date: String): Single<ValCurs> = api.getValute(date)

    fun getDynamic(dateFrom: String, dateTo: String, valuteCode: String) =
        api.getDynamic(dateFrom, dateTo, valuteCode)

    fun writeValCursToFile(valCurs: ValCurs) = cbrFile.xmlToFile(valCurs)
}