package com.sibsutis.tyap.lab4

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json

@Composable
fun Lab4() {
    var useDefaultAutomaton by remember { mutableStateOf(false) }
    var pdaInput by remember { mutableStateOf("") }
    var pda: PDA? by remember { mutableStateOf(null) }
    var chain by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Pair<Boolean, String>?>(null) }

    // Разбор кр aaaaacccccb
    val defaultPDA = PDA(
        states = listOf("q0", "q1", "q2", "q3", "q4", "q5", "q6", "qf"),
        inputAlphabet = listOf('a', 'b', 'c'),
        stackAlphabet = listOf('a', 'Z'),
        transitions = mapOf(
            "q0" to listOf(
                // (q0, a, Z) = (q1, Z)
                Transition(input = 'a', stackTop = 'Z', newStack = listOf('Z'), targetState = "q1")
            ),
            "q1" to listOf(
                // (q1, a, Z) = (q2, aZ)
                Transition(input = 'a', stackTop = 'Z', newStack = listOf('a', 'Z'), targetState = "q2"),
                // (q1, a, a) = (q2, aa)
                Transition(input = 'a', stackTop = 'a', newStack = listOf('a', 'a'), targetState = "q2"),
                // (q1, c, a) = (q3, E)
                Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q3")
            ),
            "q2" to listOf(
                // (q2, a, a) = (q1, a)
                Transition(input = 'a', stackTop = 'a', newStack = listOf('a'), targetState = "q1")
            ),
            "q3" to listOf(
                // (q3, c, a) = (q4, a)
                Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q4"),
                // (q3, c, Z) = (q6, Z)
                Transition(input = 'c', stackTop = 'Z', newStack = listOf('Z'), targetState = "q6")
            ),
            "q4" to listOf(
                // (q4, c, a) = (q5, a)
                Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q5")
            ),
            "q5" to listOf(
                // (q5, c, a) = (q5, a)
                Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q5"),
                // (q5, b, a) = (q6, E)
                Transition(input = 'b', stackTop = 'a', newStack = listOf(), targetState = "q6")
            ),
            "q6" to listOf(
                // (q6, b, a) = (q6, E)
                Transition(input = 'b', stackTop = 'a', newStack = listOf(), targetState = "q6"),
                // (q6, L, Z) = (qf, E)
                Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf"),
                // (q6, c, Z) = (q6, Z)
                Transition(input = 'c', stackTop = 'Z', newStack = listOf('Z'), targetState = "q6")
            )
        ),
        startState = "q0",
        finalStates = listOf("qf"),
        startStack = 'Z'
    )

    // aaabbbccc
    /*val defaultPDA = PDA(
        states = listOf("q0", "q1", "q2", "qf"),
        inputAlphabet = listOf('a', 'b', 'c'),
        stackAlphabet = listOf('a', 'Z'),
        transitions = mapOf(
            "q0" to listOf(
                Transition(input = 'a', stackTop = 'Z', newStack = listOf('a', 'Z'), targetState = "q0"),
                Transition(input = 'a', stackTop = 'a', newStack = listOf('a', 'a'), targetState = "q0"),
                Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1")
            ),
            "q1" to listOf(
                Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                Transition(input = 'c', stackTop = 'a', newStack = listOf('Z'), targetState = "q2")
            ),
            "q2" to listOf(
                Transition(input = 'c', stackTop = 'a', newStack = listOf('Z'), targetState = "q2"),
                Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf") // EPS transition
            )
        ),
        startState = "q0",
        finalStates = listOf("qf"),
        startStack = 'Z'
    )*/

    // нормальный 21(а) aaccc
    /*val defaultPDA = PDA(
        states = listOf("q0", "q1", "q2", "q3", "qf"),
        inputAlphabet = listOf('a', 'b', 'c'),
        stackAlphabet = listOf('a', 'Z'),
        transitions = mapOf(
            "q0" to listOf(
                // (q0, a, Z) = {q0, aZ}
                Transition(input = 'a', stackTop = 'Z', newStack = listOf('a', 'Z'), targetState = "q0"),
                // (q0, a, a) = {q0, aa}
                Transition(input = 'a', stackTop = 'a', newStack = listOf('a', 'a'), targetState = "q0"),
                // (q0, b, a) = {q1, a}
                Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                // (q0, c, a) = {q2, E}
                Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
            ),
            "q1" to listOf(
                // (q1, b, a) = {q1, a}
                Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                // (q1, c, a) = {q2, E}
                Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
            ),
            "q2" to listOf(
                // (q2, c, a) = {q3, a}
                Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q3"),
                // (q2, null, Z) = {qf, E} — переход в финальное состояние
                Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf")
            ),
            "q3" to listOf(
                // (q3, c, a) = {q2, E}
                Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2"),
                // (q3, null, Z) = {qf, E} — переход в финальное состояние
                Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf")
            )
        ),
        startState = "q0",
        finalStates = listOf("qf"),
        startStack = 'Z'
    )*/

    // плохие автоматы
    /*    val defaultPDA = PDA(
            states = listOf("q0", "q1", "q2", "q3", "qf"),
            inputAlphabet = listOf('a', 'b', 'c'),
            stackAlphabet = listOf('a', 'Z'),
            transitions = mapOf(
                "q0" to listOf(
                    // (q0, a, Z) = {q0, aZ}
                    Transition(input = 'a', stackTop = 'Z', newStack = listOf('a', 'Z'), targetState = "q0"),
                    // (q0, a, a) = {q0, aa}
                    Transition(input = 'a', stackTop = 'a', newStack = listOf('a', 'a'), targetState = "q0"),
                    // (q0, b, a) = {q1, a}
                    Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                    // (q0, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
                ),
                "q1" to listOf(
                    // (q1, b, a) = {q1, a}
                    Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                    // (q1, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
                ),
                "q2" to listOf(
                    // (q2, c, a) = {q3, a}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q3")
                ),
                "q3" to listOf(
                    // (q3, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2"),
                    // (q3, L, Z) = {qf, E}
                    Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf")
                )
            ),
            startState = "q0",
            finalStates = listOf("qf"),
            startStack = 'Z'
        )*/

    /*    val defaultPDA = PDA(
            states = listOf("q0", "q1", "q2", "q3", "qf"),
            inputAlphabet = listOf('a', 'b', 'c'),
            stackAlphabet = listOf('a', 'Z'),
            transitions = mapOf(
                "q0" to listOf(
                    // (q0, a, Z) = {q0, aZ}
                    Transition(input = 'a', stackTop = 'Z', newStack = listOf('a', 'Z'), targetState = "q0"),
                    // (q0, a, a) = {q0, aa}
                    Transition(input = 'a', stackTop = 'a', newStack = listOf('a', 'a'), targetState = "q0"),
                    // (q0, b, a) = {q1, a}
                    Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                    // (q0, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
                ),
                "q1" to listOf(
                    // (q1, b, a) = {q1, a}
                    Transition(input = 'b', stackTop = 'a', newStack = listOf('a'), targetState = "q1"),
                    // (q1, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2")
                ),
                "q2" to listOf(
                    // (q2, c, a) = {q3, a}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q3")
                ),
                "q3" to listOf(
                    // (q3, c, a) = {q2, E}
                    Transition(input = 'c', stackTop = 'a', newStack = listOf(), targetState = "q2"),
                    // (q3, L, Z) = {qf, E}
                    Transition(input = null, stackTop = 'Z', newStack = listOf(), targetState = "qf")
                )
            ),
            startState = "q0",
            finalStates = listOf("qf"),
            startStack = 'Z'
        )*/

    /*val defaultPDA = PDA(
        states = listOf("q0"),
        inputAlphabet = listOf('a', 'b', 'c', 'v'),
        stackAlphabet = listOf('a', 'b', 'c', 'Z'),
        transitions = mapOf(
            "q0" to listOf(
                Transition(input = 'c', stackTop = 'Z', newStack = listOf('Z'), targetState = "q0"),
                Transition(input = 'c', stackTop = 'a', newStack = listOf('a'), targetState = "q0"),
                Transition(input = 'c', stackTop = 'b', newStack = listOf('b'), targetState = "q0"),
                Transition(
                    input = 'a',
                    stackTop = 'Z',
                    newStack = listOf('a', 'Z'),
                    targetState = "q0"
                ),
                Transition(
                    input = 'a',
                    stackTop = 'a',
                    newStack = listOf('a', 'a'),
                    targetState = "q0"
                ),
                Transition(input = 'a', stackTop = 'b', newStack = listOf(), targetState = "q0"),
                Transition(
                    input = 'b',
                    stackTop = 'Z',
                    newStack = listOf('b', 'Z'),
                    targetState = "q0"
                ),
                Transition(
                    input = 'b',
                    stackTop = 'b',
                    newStack = listOf('b', 'b'),
                    targetState = "q0"
                ),
                Transition(input = 'b', stackTop = 'a', newStack = listOf(), targetState = "q0"),
                Transition(
                    input = null,
                    stackTop = 'Z',
                    newStack = listOf(),
                    targetState = "q0"
                ) // EPS transition
            )
        ),
        startState = "q0",
        finalStates = listOf("q0")
    )*/

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Проверка ДМПА автомата", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        // Переключатель
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text("Использовать встроенный автомат")
            Switch(
                checked = useDefaultAutomaton,
                onCheckedChange = {
                    useDefaultAutomaton = it
                    pda = if (useDefaultAutomaton) defaultPDA else null
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!useDefaultAutomaton) {
            Text("Введите автомат в JSON формате:")
            BasicTextField(
                value = pdaInput,
                onValueChange = { pdaInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colors.primary)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (pdaInput.isEmpty()) Text("")
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                try {
                    pda = Json.decodeFromString(pdaInput)
                } catch (e: Exception) {
                    pda = null
                }
            }) {
                Text("Загрузить")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (pda != null) {
            Text("Автомат успешно загружен: начальное состояние: '${pda!!.startState}', финальное состояние: ${pda!!.finalStates.joinToString()}")
            Spacer(modifier = Modifier.height(16.dp))

            Text("Введите контекстно свободную цепочку:")
            BasicTextField(
                value = chain,
                onValueChange = { chain = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colors.primary)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (chain.isEmpty()) Text("aacccc")
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val (isAccepted, explanation) = processChain(pda!!, chain)
                result = Pair(isAccepted, explanation)
            }) {
                Text("Проверить!")
            }

            Spacer(modifier = Modifier.height(16.dp))

            result?.let {
                Text(if (it.first) "Принята!" else "Отклонена")
                SelectionContainer {
                    Text("Обьяснение: ${it.second}")
                }
            }

        } else if (!useDefaultAutomaton) {
            Text("Неправильный формат автомата")
        }
    }
}
