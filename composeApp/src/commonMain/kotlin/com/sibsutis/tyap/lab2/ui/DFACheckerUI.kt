package com.sibsutis.tyap.lab2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sibsutis.tyap.lab2.domain.DFA
import com.sibsutis.tyap.lab2.domain.Result
import com.sibsutis.tyap.lab2.utils.checkchain
import com.sibsutis.tyap.lab2.utils.parseDFA

@Composable
fun DFACheckerUI(dfa: DFA) {
    var dfaInput by remember { mutableStateOf("") }
    dfaInput = "states: q0,q1,q2,q3\n" +
            "alphabet: 0,1,a,b\n" +
            "transitions: q0,0->q1; q0,1->q0; q1,1->q2; q2,a->q3; q3,0->q1\n" +
            "start: q0\n" +
            "accept: q3"
    var dfaCustom by remember { mutableStateOf<DFA?>(null) }
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Result?>(null) }

    var switchChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Проверка регулярного языка конечным автоматом", style = MaterialTheme.typography.h4)

        Row {
            Switch(
                checked = switchChecked,
                onCheckedChange = {
                    switchChecked = it
                }
            )
            Text("Ввести вручную цепочку", style = MaterialTheme.typography.h5)
        }


        if (switchChecked) {
            /* Пример
states: q0,q1,q2,q3
alphabet: 0,1,a,b
transitions: q0,0->q1; q0,1->q0; q1,1->q2; q2,a->q3; q3,0->q1
start: q0
accept: q3
            */
            //Text("Введите вручную DFA", style = MaterialTheme.typography.h5)
            TextField(
                value = dfaInput,
                onValueChange = { dfaInput = it },
                label = { Text("Введите DFA (states, alphabet, transitions, start state, accept states)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Введите цепочку") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (switchChecked) {
                result = parseDFA(dfaInput)?.checkchain(input)
            } else result = dfa.checkchain(input)
        }) {
            Text("Проверить!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        result?.let {
            Text(
                "Результат: ${if (it.accepted) "Работает!! 😊" else "Не работает 🫡"}",
                style = MaterialTheme.typography.h5
            )
            Text(it.explanation)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Переходы по автомату:")
            it.configChanges.forEach { change ->
                Text(change)
            }
        }
    }
}