package com.sibsutis.tyap.lab1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sibsutis.tyap.lab1.logic.Grammar
import com.sibsutis.tyap.lab1.logic.Rule

@Composable
fun GrammarGUI() {
    var useDefaultGrammar by remember { mutableStateOf(true) }
    var minLength by remember { mutableStateOf("3") }
    var maxLength by remember { mutableStateOf("5") }
    var chains by remember { mutableStateOf(listOf<String>()) }
    var chosenChain by remember { mutableStateOf("") }
    var parseTree by remember { mutableStateOf<String?>(null) }


    val defaultGrammar = Grammar(
        rules = listOf(
            Rule("S", listOf("a", "S", "b")),
            Rule("S", listOf(""))
        ),
        startSymbol = "S"
    )

    /*    val defaultGrammar by remember {
            mutableStateOf<Grammar>(
                Grammar(
                    rules = listOf(
                        Rule("S", listOf("a", "S", "b")),
                        Rule("S", listOf(""))
                    ),
                    startSymbol = "S"
                )

                        Grammar (
                        rules = listOf(
                            Rule("S", listOf("aa", "A", "abS")),
                            Rule("S", listOf("")),
                            Rule("A", listOf("SaaS"))
                        ),
                startSymbol = "S"
            )
            )
        }*/

    var customGrammarRules by remember { mutableStateOf(mutableListOf<Rule>()) }
    var customStartSymbol by remember { mutableStateOf("S") }
    var newLHS by remember { mutableStateOf("") }
    var newRHS by remember { mutableStateOf("") }
    var grammar by remember { mutableStateOf(defaultGrammar) }
    var errorMessage by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Выберите грамматику:")
        Row {
            Button(
                onClick = {
                    useDefaultGrammar = true
                    grammar = defaultGrammar
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF055B7A),
                    contentColor = Color.White
                ), modifier = Modifier.padding(16.dp, 4.dp)
            ) {
                Text("Использовать предустановленную грамматику")
            }
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = {
                    useDefaultGrammar = false
                    grammar = Grammar(customGrammarRules, customStartSymbol)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF055B7A),
                    contentColor = Color.White
                ), modifier = Modifier.padding(16.dp, 4.dp)
            ) {
                Text("Использовать свою грамматику")
            }
        }

        Spacer(Modifier.height(10.dp))


        if (!useDefaultGrammar) {
            Text("Добавьте свои правила грамматики:")
            Row {
                TextField(
                    value = newLHS,
                    onValueChange = { newLHS = it },
                    modifier = Modifier.width(100.dp).padding(4.dp)
                )
                Text(" -> ")
                TextField(
                    value = newRHS,
                    onValueChange = { newRHS = it },
                    modifier = Modifier.width(200.dp).padding(4.dp)
                )
                Button(
                    onClick = {
                        if (newLHS.isNotEmpty() /*&& newRHS.isNotEmpty()*/) {
                            customGrammarRules.add(Rule(newLHS, newRHS.split(" ")))
                            newLHS = ""
                            newRHS = ""
                            errorMessage = "" // Сбрасываем ошибку
                        } else {
                            errorMessage = "Левая часть правила не должны быть пустыми"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF055B7A),
                        contentColor = Color.White
                    ), modifier = Modifier.padding(16.dp, 4.dp)
                ) {
                    Text("Добавить правило")
                }
            }
            Text("Текущие правила:")
            customGrammarRules.forEach { rule ->
                Text("${rule.lhs} -> ${rule.rhs.joinToString(" ")}")
            }

            Spacer(Modifier.height(10.dp))

            Text("Введите стартовый символ:")
            TextField(
                value = customStartSymbol,
                onValueChange = { customStartSymbol = it },
                modifier = Modifier.width(100.dp).padding(4.dp)
            )
            OutlinedButton(
                onClick = {
                    grammar = Grammar(customGrammarRules, customStartSymbol)
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF055B7A)
                ), modifier = Modifier.padding(16.dp, 4.dp)
            ) {
                Text("Готов")
            }
        }

        Spacer(Modifier.height(10.dp))

        Row {
            Text("Минимальная длина: ")
            TextField(
                value = minLength,
                onValueChange = { minLength = it },
                modifier = Modifier.width(100.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text("Максимальная длина: ")
            TextField(
                value = maxLength,
                onValueChange = { maxLength = it },
                modifier = Modifier.width(100.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                val minLen = minLength.toIntOrNull()
                val maxLen = maxLength.toIntOrNull()

                if (minLen == null || maxLen == null || minLen > maxLen) {
                    errorMessage = "Убедитесь, что минимальная и максимальная длины корректны"
                } else {
                    chains = grammar.generateChains(minLen, maxLen, leftDerivation = true)
                    errorMessage = if (chains.isEmpty()) "Не удалось сгенерировать цепочки" else ""
                }
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF055B7A),
                contentColor = Color.White
            ), modifier = Modifier.padding(16.dp, 4.dp)
        ) {
            Text("Сгенерировать цепочки")
        }

        Spacer(Modifier.height(10.dp))
        /////
        Text("Старт:" + grammar.startSymbol + " Правила:" + grammar.rules)
        //////
        Text("Сгенерированные цепочки:")
        SelectionContainer {
            Column {
                chains.forEach { chain ->
                    Text(chain)
                }
            }
        }


        Spacer(Modifier.height(10.dp))

        Text("Выберите цепочку для построения дерева:")
        TextField(
            value = chosenChain,
            onValueChange = { chosenChain = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                parseTree = grammar.buildParseTree(chosenChain)?.print()
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF055B7A),
                contentColor = Color.White
            ), modifier = Modifier.padding(16.dp, 4.dp)
        ) {
            Text("Построить дерево вывода")
        }

        Spacer(Modifier.height(10.dp))

        if (parseTree != null) {
            Text("Дерево вывода:")
            Text(parseTree!!)
        }


        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Text("Ошибка: $errorMessage", color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}