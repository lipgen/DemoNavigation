package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ValCurs")
class ValDyn(
    @Attribute(name = "DateRange1")
    var dateFrom: String,

    @Attribute(name = "DateRange2")
    var dateTo: String,

    @Element
    var dynamic: List<RecordRange>
)