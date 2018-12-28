package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Item")
class NewsItem(
    @PropertyElement(name = "Date")
    val date: String,
    @PropertyElement(name = "Url")
    val url: String,
    @PropertyElement(name = "Title")
    val title: String
)