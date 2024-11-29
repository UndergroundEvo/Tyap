package com.sibsutis.tyap.lab2.domain

data class Result(
    val accepted: Boolean,
    val explanation: String,
    val configChanges: List<String>
)