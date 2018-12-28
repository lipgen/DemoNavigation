package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Valute")
class Valute(
    @Attribute(name = "ID")
    var id: String,

    @PropertyElement(name = "NumCode")
    var numCode: Int,

    @PropertyElement(name = "CharCode")
    var charCode: String,

    @PropertyElement(name = "Nominal")
    var nominal: Int,

    @PropertyElement(name = "Name")
    var name: String,

    @PropertyElement(name = "Value")
    var value: String
)