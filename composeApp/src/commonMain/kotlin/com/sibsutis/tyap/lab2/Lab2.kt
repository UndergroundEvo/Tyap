package com.sibsutis.tyap.lab2

import androidx.compose.runtime.Composable
import com.sibsutis.tyap.lab2.domain.DFA
import com.sibsutis.tyap.lab2.ui.DFACheckerUI

@Composable
fun Lab2() {
    // Пример ДКА
    val dfa = DFA(
        states = setOf("q0", "q1", "q2"),
        alphabet = setOf('0', '1'),
        transitions = mapOf(
            ("q0" to '0') to "q1",
            ("q0" to '1') to "q0",
            ("q1" to '0') to "q1",
            ("q1" to '1') to "q2",
            ("q2" to '0') to "q2",
            ("q2" to '1') to "q2"
        ),
        startState = "q0",
        acceptStates = setOf("q2")
    )
     /** номер 16(В)
      * 00001a
     */
    /*val dfa = DFA(
        states = setOf("q0", "q1", "q2", "q3"),
        alphabet = setOf('0', '1', 'a', 'b'),
        transitions = mapOf(
            ("q0" to '0') to "q1",
            ("q0" to '1') to "q0",
            ("q0" to 'a') to "q0",
            ("q0" to 'b') to "q0",

            ("q1" to '1') to "q2",
            ("q1" to '0') to "q1",
            ("q1" to 'a') to "q0",
            ("q1" to 'b') to "q0",

            ("q2" to 'a') to "q3",
            ("q2" to '1') to "q0",
            ("q2" to 'b') to "q0",
            ("q2" to '0') to "q1",

            ("q3" to '0') to "q1",
            ("q3" to '1') to "q0",
            ("q3" to 'a') to "q0",
            ("q3" to 'b') to "q0"
        ),
        startState = "q0",
        acceptStates = setOf("q3")
    )*/

    DFACheckerUI(dfa)
}