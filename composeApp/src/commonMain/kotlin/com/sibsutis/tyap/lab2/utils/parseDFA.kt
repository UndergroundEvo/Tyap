package com.sibsutis.tyap.lab2.utils

import com.sibsutis.tyap.lab2.domain.DFA

fun parseDFA(input: String): DFA? {
    // Ожидаемый формат ввода:
    // states: q0,q1,q2,q3
    // alphabet: 0,1,a,b
    // transitions: q0,0->q1; q0,1->q0; ...
    // start: q0
    // accept: q3

    val lines = input.lines()
    if (lines.size < 5) return null

    val states = lines[0].removePrefix("states: ").split(",").toSet()
    val alphabet = lines[1].removePrefix("alphabet: ").split(",").map { it[0] }.toSet()

    val transitions = lines[2].removePrefix("transitions: ")
        .split("; ")
        .map {
            val (fromTo, toState) = it.split("->")
            val (fromState, symbol) = fromTo.split(",")
            (fromState to symbol[0]) to toState
        }
        .toMap()

    val startState = lines[3].removePrefix("start: ")
    val acceptStates = lines[4].removePrefix("accept: ").split(",").toSet()

    return DFA(states, alphabet, transitions, startState, acceptStates)
}