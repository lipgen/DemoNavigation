package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ValCurs")
class ValCurs(
    @Attribute(name = "Date")
    var date: String,
    @Element
    var valute: List<Valute>
)