package com.sibsutis.tyap.lab2.utils

import com.sibsutis.tyap.lab2.domain.DFA
import com.sibsutis.tyap.lab2.domain.Result

fun DFA.checkchain(input: String) : Result {
    var currentState = startState
    val configChanges = mutableListOf("Старт в состонии $currentState")

    for (symbol in input) {
        if (symbol !in alphabet) {
            return Result(
                false,
                "Символ '$symbol' отсуствует в алфавите.",
                configChanges
            )
        }
        val transitionKey = currentState to symbol
        if (transitionKey !in transitions) {
            return Result(
                false,
                "Нет переходного состояния '$currentState' для символа'$symbol'.",
                configChanges
            )
        }
        currentState = transitions[transitionKey]!!
        configChanges.add("Переход к $currentState, символ '$symbol'")
    }

    return if (currentState in acceptStates) {
        configChanges.add("Строка принята. Финальное состояние: $currentState")
        Result(true, "Строка принята.", configChanges)
    } else {
        configChanges.add("Строка отклонена. Финальное состояние: $currentState не являеться конечным.")
        Result(false, "Финальное состояние не являеться конечным", configChanges)
    }
}