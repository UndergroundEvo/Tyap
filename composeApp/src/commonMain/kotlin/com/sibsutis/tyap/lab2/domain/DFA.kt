package com.sibsutis.tyap.lab2.domain

data class DFA(
    val states: Set<String>,
    val alphabet: Set<Char>,
    // переходы вида (текущее состояние, символ) -> следующее состояние
    val transitions: Map<Pair<String, Char>, String>,
    val startState: String,
    val acceptStates: Set<String>
)