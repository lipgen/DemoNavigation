package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Record")
class RecordRange(
    @Attribute(name = "Date")
    var date: String,

    @PropertyElement(name = "Nominal")
    var nominal: Int,

    @PropertyElement(name = "Value")
    var value: String
)