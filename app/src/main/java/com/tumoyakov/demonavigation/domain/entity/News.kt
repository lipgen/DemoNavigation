package com.tumoyakov.demonavigation.domain.entity

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "News")
class News(
    @Element
    val ItemList: List<NewsItem>
)