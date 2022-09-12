package ru.netology.nmedia.utils

import kotlin.math.round

object Helper {

    @JvmStatic
    fun getShortCountView(count: Long): String {
        if (count > 999999) {
            return if (count % 1000000 < 100000)
                (count / 1000000).toString() + "M"
            else (round(count.toDouble() / 1000000 * 10) / 10).toString() + "M"
        }
        if (count > 9999) return (count / 1000).toString() + "K"
        if (count > 999) {
            return if (count % 1000 < 100)
                (count / 1000).toString() + "K"
            else (round(count.toDouble() / 1000 * 10) / 10).toString() + "K"
        }
        return count.toString()
    }
}
