package com.sibsutis.tyap.lab4

fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    var stack = mutableListOf(pda.startStack)
    var currentIndex = 0
    val explanation = StringBuilder()

    explanation.append("Начальное состояние: $currentState, Стэк: $stack\n")

    while (currentIndex <= chain.length || stack.isNotEmpty()) {
        val currentInput = if (currentIndex < chain.length) chain[currentIndex] else null
        val stackTop = stack.lastOrNull()

        // Ищем обычный или EPS-переход
        val transition = pda.transitions[currentState]?.find {
            (it.input == currentInput || it.input == null) && it.stackTop == stackTop
        }

        if (transition != null) {
            explanation.append(
                if (transition.input == null) "\nEPS состояние, удаление\n"
                else "Читаемый символ: '$currentInput', ^Стек: '$stackTop'\n"
            )
            explanation.append("Применяемое превращение: $transition\n")

            // Обновляем стек
            stack.removeAt(stack.size - 1)
            stack.addAll(transition.newStack.reversed())

            // Обновляем состояние и текущий символ (если это не EPS)
            currentState = transition.targetState
            if (transition.input != null) currentIndex++

            explanation.append("Новое состояние: $currentState, Стек: $stack\n")
        } else {
            explanation.append("Нет подходящего перехода для символа '$currentInput' и стека '$stackTop'\n")
            return Pair(false, explanation.toString())
        }

        explanation.append("      Текущее состояние: $currentState, финальное: ${pda.finalStates}\n")
        explanation.append("      Стек пуст: ${stack.isEmpty()}, текущий индекс: $currentIndex, длинна цепочки: ${chain.length}\n")

        // Проверяем, достигли ли мы финального состояния с пустым стеком
        if (stack.isEmpty() && currentIndex == chain.length) {
            return if (pda.finalStates.contains(currentState)) {
                explanation.append("Успешно\n")
                Pair(true, explanation.toString())
            } else {
                explanation.append("Отклонена: финальное состояние не достигнуто\n")
                Pair(false, explanation.toString())
            }
        }
    }

    explanation.append("Отклонена: стек не пуст или символы не обработаны полностью\n")
    return Pair(false, explanation.toString())
}


/*fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    var stack = mutableListOf(pda.startStack)
    var currentIndex = 0
    var explanation = StringBuilder()

    explanation.append("Начальное состояние: $currentState, Стэк: $stack\n")

    while (currentIndex <= chain.length || stack.isNotEmpty()) {
        val currentInput = if (currentIndex < chain.length) chain[currentIndex] else null
        val stackTop = stack.lastOrNull()

        // Проверяем переход
        val transition = pda.transitions[currentState]?.find {
            it.input == currentInput && it.stackTop == stackTop
        }

        if (transition != null) {
            explanation.append("Читаемый символ: '$currentInput', ^Стек: '$stackTop'\n")
            explanation.append("Применяемое превращение: $transition\n")
            stack.removeAt(stack.size - 1)
            stack.addAll(transition.newStack.reversed())
            currentState = transition.targetState
            explanation.append("Новое состояние: $currentState, Стек: $stack\n")
            currentIndex++
        } else {
            val epsTransition = pda.transitions[currentState]?.find {
                it.input == null && it.stackTop == stackTop
            }

            if (epsTransition != null) {
                explanation.append("\n")
                //explanation.append("EPS transition applied\n")
                explanation.append("EPS состояние, удаление\n")
                stack.removeAt(stack.size - 1)
                stack.addAll(epsTransition.newStack.reversed())
                currentState = epsTransition.targetState
                explanation.append("Новое состояние: $currentState, Стек: $stack\n")
            } else {
                return Pair(false, explanation.toString())
            }
        }

        explanation.append("      Текущее состояние: $currentState, финальное:  ${pda.finalStates}\n")
        explanation.append("      Стек пуст: ${stack.isEmpty()}, текущий индекс: $currentIndex, длинна цепочки: ${chain.length} \n")

        // Проверяем, достигли ли мы финального состояния с пустым стеком
        if (stack.isEmpty() && currentIndex == chain.length) {
            return if (pda.finalStates.contains(currentState)) {
                explanation.append("Успешно")
                Pair(true, explanation.toString())
            } else {
                explanation.append("Отклонена")
                Pair(false, explanation.toString())
            }
        }
    }

    return Pair(false, explanation.toString())
}*/

/*
fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    var stack = mutableListOf(pda.startStack)
    var currentIndex = 0
    var explanation = StringBuilder()

    explanation.append("Initial State: $currentState, Stack: $stack\n")

    while (currentIndex <= chain.length || stack.isNotEmpty()) {
        val currentInput = if (currentIndex < chain.length) chain[currentIndex] else null
        val stackTop = stack.lastOrNull()

        // Проверяем переход
        val transition = pda.transitions[currentState]?.find {
            it.input == currentInput && it.stackTop == stackTop
        }

        if (transition != null) {
            explanation.append("Reading Input: '$currentInput', Stack Top: '$stackTop'\n")
            explanation.append("Applying Transition: $transition\n")
            stack.removeAt(stack.size - 1)
            stack.addAll(transition.newStack.reversed())
            currentState = transition.targetState
            explanation.append("New State: $currentState, Stack: $stack\n")
            currentIndex++
        } else {
            val epsTransition = pda.transitions[currentState]?.find {
                it.input == null && it.stackTop == stackTop
            }

            if (epsTransition != null) {
                explanation.append("EPS transition applied\n")
                stack.removeAt(stack.size - 1)
                stack.addAll(epsTransition.newStack.reversed())
                currentState = epsTransition.targetState
                explanation.append("New State: $currentState, Stack: $stack\n")
            } else {
                return Pair(false, explanation.toString())
            }
        }

        // Проверяем, достигли ли мы финального состояния с пустым стеком
        if (stack.isEmpty() && currentIndex == chain.length) {
            return if (pda.finalStates.contains(currentState)) {
                Pair(true, explanation.toString())
            } else {
                Pair(false, explanation.toString())
            }
        }
    }

    return Pair(false, explanation.toString())
}
*/

/*
fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    var stack = mutableListOf(pda.startStack)
    var currentIndex = 0
    var explanation = StringBuilder()

    explanation.append("Initial State: $currentState, Stack: $stack\n")

    while (currentIndex <= chain.length || stack.isNotEmpty()) {
        val currentInput = if (currentIndex < chain.length) chain[currentIndex] else null
        val stackTop = stack.lastOrNull()

        // Проверяем переход
        val transition = pda.transitions[currentState]?.find {
            it.input == currentInput && it.stackTop == stackTop
        }

        if (transition != null) {
            explanation.append("Reading Input: '$currentInput', Stack Top: '$stackTop'\n")
            explanation.append("Applying Transition: $transition\n")
            stack.removeAt(stack.size - 1)
            stack.addAll(transition.newStack.reversed())
            currentState = transition.targetState
            explanation.append("New State: $currentState, Stack: $stack\n")
            currentIndex++
        } else {
            val epsTransition = pda.transitions[currentState]?.find {
                it.input == null && it.stackTop == stackTop
            }

            if (epsTransition != null) {
                explanation.append("EPS transition applied\n")
                stack.removeAt(stack.size - 1)
                stack.addAll(epsTransition.newStack.reversed())
                currentState = epsTransition.targetState
                explanation.append("New State: $currentState, Stack: $stack\n")
            } else {
                return Pair(false, explanation.toString())
            }
        }

        if (stack.isEmpty() && currentIndex == chain.length) {
            return if (pda.finalStates.contains(currentState)) {
                Pair(true, explanation.toString())
            } else {
                Pair(false, explanation.toString())
            }
        }
    }

    return Pair(false, explanation.toString())
}
*/

/*fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    var stack = mutableListOf(pda.startStack)  // Стек начинается с начального символа
    var currentIndex = 0
    var explanation = StringBuilder()

    explanation.append("Initial State: $currentState, Stack: $stack\n")

    while (currentIndex <= chain.length || stack.isNotEmpty()) {
        val currentInput = if (currentIndex < chain.length) chain[currentIndex] else null
        val stackTop = stack.lastOrNull()

        // Проверяем, есть ли подходящий переход
        val transition = pda.transitions[currentState]?.find {
            it.input == currentInput && it.stackTop == stackTop
        }

        if (transition != null) {
            // Применяем переход
            explanation.append("Reading Input: '$currentInput', Stack Top: '$stackTop'\n")
            explanation.append("Applying Transition: $transition\n")
            stack.removeAt(stack.size - 1)  // Убираем верхний элемент из стека
            stack.addAll(transition.newStack.reversed())  // Добавляем новые элементы в стек
            currentState = transition.targetState
            explanation.append("New State: $currentState, Stack: $stack\n")
            currentIndex++
        } else {
            // Если нет подходящего перехода, проверяем возможность использования EPS-перехода
            val epsTransition = pda.transitions[currentState]?.find {
                it.input == null && it.stackTop == stackTop
            }

            if (epsTransition != null) {
                // Применяем EPS-переход
                explanation.append("EPS transition applied\n")
                stack.removeAt(stack.size - 1)  // Убираем верхний элемент из стека
                stack.addAll(epsTransition.newStack.reversed())  // Добавляем новые элементы в стек
                currentState = epsTransition.targetState
                explanation.append("New State: $currentState, Stack: $stack\n")
            } else {
                // Если нет ни перехода по входу, ни EPS-перехода, завершаем выполнение
                return Pair(false, explanation.toString())
            }
        }

        // Если стек пуст и цепочка завершена, проверяем, что мы в конечном состоянии
        if (stack.isEmpty() && currentIndex == chain.length) {
            return if (pda.finalStates.contains(currentState)) {
                Pair(true, explanation.toString())
            } else {
                Pair(false, explanation.toString())
            }
        }
    }

    // Если мы вышли из цикла, это значит, что стек пуст и цепочка завершена
    return if (stack.isEmpty() && currentIndex == chain.length) {
        Pair(true, explanation.toString())
    } else {
        Pair(false, explanation.toString())
    }
}*/

/*fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    // Текущее состояние автомата
    var currentState = pda.startState

    // Инициализация стека (начинается с фиксированного символа 'Z')
    val stack = mutableListOf('Z')

    // Индекс текущего символа в цепочке
    var index = 0

    // Лог для отображения последовательной смены конфигураций
    val log = StringBuilder("Initial State: $currentState, Stack: $stack\n")

    // Пока не прошли всю цепочку и пока возможны переходы
    while (index <= chain.length) {
        val currentInput = if (index < chain.length) chain[index] else null // null для конца цепочки
        val stackTop = if (stack.isNotEmpty()) stack.last() else null // Верхний символ стека или null

        log.append("Reading Input: '${currentInput ?: "EPS"}', Stack Top: '${stackTop ?: "null"}'\n")

        // Найти подходящие переходы
        val transitions = pda.transitions[currentState]?.filter { transition ->
            (transition.input == currentInput || transition.input == null) &&
                    (transition.stackTop == stackTop || transition.stackTop == null)
        }

        if (transitions.isNullOrEmpty()) {
            // Если нет подходящих переходов, возвращаем объяснение отказа
            log.append("No valid transition from state '$currentState' with input '${currentInput ?: "EPS"}' and stack top '${stackTop ?: "null"}'\n")
            return Pair(false, log.toString())
        }

        // Выбираем первый доступный переход
        val transition = transitions.first()
        log.append("Applying Transition: ${transition}\n")

        // Обновляем состояние
        currentState = transition.targetState

        // Обновляем стек
        if (stackTop != null && transition.stackTop != null) {
            stack.removeAt(stack.size - 1) // Убираем верхний символ стека
        }
        if (transition.newStack.isNotEmpty()) {
            stack.addAll(transition.newStack.reversed()) // Добавляем новые символы на стек
        }

        // Увеличиваем индекс, если это не ε-переход
        if (transition.input != null) {
            index++
        }

        // Лог текущей конфигурации
        log.append("New State: $currentState, Stack: $stack\n")
    }

    // Проверяем конечное состояние и пустоту стека
    val isAccepted = currentState in pda.finalStates && stack.isEmpty()
    log.append(
        if (isAccepted) "Chain Accepted: Final State: $currentState, Stack Empty\n"
        else "Chain Rejected: Final State: $currentState, Stack: $stack\n"
    )

    return Pair(isAccepted, log.toString())
}*/

/*
fun processChain(pda: PDA, chain: String): Pair<Boolean, String> {
    var currentState = pda.startState
    val stack = mutableListOf(pda.startState) // Инициализация стека начальным символом
    //val stack = mutableListOf('Z') // Начальный стек с фиксированным символом 'Z'

    var index = 0
    while (index <= chain.length) {
        val currentInput = if (index < chain.length) chain[index] else null // Текущий символ или null, если цепочка закончилась
        val stackTop = if (stack.isNotEmpty()) stack.last() else null

        // Найти подходящий переход
        val possibleTransitions = pda.transitions[currentState]?.filter { transition ->
            transition.input == currentInput || transition.input == null // Переход на текущий символ или ε-переход
        }?.filter { transition ->
            transition.stackTop == stackTop || transition.stackTop == null // Условие на стек
        }

        if (possibleTransitions.isNullOrEmpty()) {
            // Нет доступных переходов
            val explanation = "No valid transitions from state '$currentState' with input '$currentInput' and stack top '${stackTop ?: "null"}'"
            return Pair(false, explanation)
        }

        // Применить первый подходящий переход
        val transition = possibleTransitions.first()
        currentState = transition.targetState

        // Обновить стек
        if (stack.isNotEmpty() && transition.stackTop != null) {
            stack.removeAt(stack.size - 1) // Убираем текущий верхний символ
        }
        if (transition.newStack.isNotEmpty()) {
            stack.addAll(transition.newStack.reversed()) // Добавляем символы в стек
        }

        // Увеличиваем индекс только для явного ввода (не для ε-перехода)
        if (transition.input != null) {
            index++
        }
    }

    // Проверяем, находится ли автомат в конечном состоянии и стек пуст
    val isAccepted = currentState in pda.finalStates && stack.isEmpty()
    val explanation = if (isAccepted) {
        "The chain is accepted."
    } else {
        "The chain is rejected because it does not end in a final state or stack is not empty."
    }

    return Pair(isAccepted, explanation)
}*/
