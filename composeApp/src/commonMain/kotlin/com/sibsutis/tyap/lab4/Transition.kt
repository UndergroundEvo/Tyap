package com.sibsutis.tyap.lab4

import kotlinx.serialization.Serializable

@Serializable
data class Transition(
    val input: Char?,
    val stackTop: Char?,
    val newStack: List<Char>,
    val targetState: String
)
