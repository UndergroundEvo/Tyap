package com.sibsutis.tyap.lab1.logic

data class TreeNode(val value: String) {
    val children: MutableList<TreeNode> = mutableListOf()

    fun print(level: Int = 0): String {
        val sb = StringBuilder()
        sb.append("  ".repeat(level)).append(value).append("\n")
        children.forEach { sb.append(it.print(level + 1)) }
        return sb.toString()
    }
}
