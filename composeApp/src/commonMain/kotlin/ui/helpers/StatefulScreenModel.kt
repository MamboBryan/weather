package ui.helpers

import cafe.adriel.voyager.core.model.StateScreenModel

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 19:01
 * USER    : mambo
 */

open class StatefulScreenModel<T>(
    private val initial: T
) : StateScreenModel<T>(initialState = initial) {

    fun updateState(block: T.() -> T) {
        mutableState.value = mutableState.value.block()
    }

}