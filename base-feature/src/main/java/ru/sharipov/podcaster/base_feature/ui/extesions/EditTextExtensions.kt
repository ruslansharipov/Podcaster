package ru.sharipov.podcaster.base_feature.ui.extesions

import android.widget.EditText
import android.widget.TextView
import ru.sharipov.podcaster.base_feature.ui.extesions.setTextDistinct

/**
 * Свойство производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [EditText] текст и новый текст не совпадают.
 *
 * Также не меняет позицию выделения в [EditText].
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [EditText],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 * */
var EditText.distinctText: CharSequence
    get() = text
    set(value) {
        setTextDistinct(value)
    }

/**
 * Метод производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [EditText] текст и [newText] не совпадают.
 *
 * Также не меняет позицию выделения в [EditText].
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [EditText],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 *
 * @return был ли изменен текст [EditText].
 * */
fun EditText.setTextDistinct(newText: CharSequence): Boolean {
    val isCursorPosGreaterThanTextLen = selectionStart > newText.length
    val isCursorAtTheEnd = selectionStart == length()
    val newCursorPos = when {
        isCursorPosGreaterThanTextLen || isCursorAtTheEnd -> newText.length
        else -> selectionStart
    }
    val isChanged = (this as TextView).setTextDistinct(newText)
    if (isChanged) setSelection(newCursorPos)
    return isChanged
}
