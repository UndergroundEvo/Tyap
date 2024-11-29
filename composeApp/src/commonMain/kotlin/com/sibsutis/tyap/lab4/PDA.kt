package com.sibsutis.tyap.lab4

import kotlinx.serialization.Serializable

/*@Serializable
data class PDA(
    val states: List<String>,
    val inputAlphabet: List<Char>,
    val stackAlphabet: List<Char>,
    val transitions: Map<String, List<Transition>>, // State -> List of transitions
    val startState: String,
    val finalStates: List<String>
)*/

@Serializable
class PDA(
    val states: List<String>,
    val inputAlphabet: List<Char>,
    val stackAlphabet: List<Char>,
    val transitions: Map<String, List<Transition>>,
    val startState: String,
    val finalStates: List<String>,
    val startStack: Char
)