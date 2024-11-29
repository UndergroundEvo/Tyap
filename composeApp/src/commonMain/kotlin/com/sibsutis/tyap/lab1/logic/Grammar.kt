package com.sibsutis.tyap.lab1.logic


class Grammar(val rules: List<Rule>, val startSymbol: String) {

    fun generateChains(minLength: Int, maxLength: Int, leftDerivation: Boolean): List<String> {
        val chains = mutableListOf<String>()
        generateChainsRec(startSymbol, minLength, maxLength, chains, leftDerivation)
        return chains
    }

    private fun generateChainsRec(
        current: String,
        minLength: Int,
        maxLength: Int,
        chains: MutableList<String>,
        leftDerivation: Boolean
    ) {
        if (current.length > maxLength) return
        if (isTerminal(current) && current.length in minLength..maxLength) {
            chains.add(current)
        } else {
            for (rule in rules) {
                val index =
                    if (leftDerivation) current.indexOf(rule.lhs) else current.lastIndexOf(rule.lhs)
                if (index != -1) {
                    val newChain =
                        current.substring(0, index) + rule.rhs.joinToString("") + current.substring(
                            index + rule.lhs.length
                        )
                    generateChainsRec(newChain, minLength, maxLength, chains, leftDerivation)
                }
            }
        }
    }

    private fun isTerminal(s: String): Boolean {
        return s.all { it.isLowerCase() || it.isDigit() }
    }

    fun buildParseTree(chain: String): TreeNode? {
        return buildParseTreeRec(startSymbol, chain)
    }

    private fun buildParseTreeRec(current: String, chain: String): TreeNode? {
        if (current == chain) return TreeNode(current)
        for (rule in rules) {
            val index = current.indexOf(rule.lhs)
            if (index != -1) {
                val newChain =
                    current.substring(0, index) + rule.rhs.joinToString("") + current.substring(
                        index + rule.lhs.length
                    )
                val child = buildParseTreeRec(newChain, chain)
                if (child != null) {
                    val node = TreeNode(current)
                    node.children.add(child)
                    return node
                }
            }
        }
        return null
    }
}

