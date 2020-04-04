package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import kotlin.math.roundToInt

/**
 * Свойство производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [TextView] текст и новый текст не совпадают.
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [TextView],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 * */
var TextView.distinctText: CharSequence
    get() = text
    set(value) {
        setTextDistinct(value)
    }

/**
 * Метод производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [TextView] текст и [newText] не совпадают.
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [TextView],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 *
 * @return был ли изменен текст [TextView].
 * */
fun TextView.setTextDistinct(newText: CharSequence): Boolean {
    if (TextUtils.equals(newText, text)) return false
    text = newText
    return true
}

/**
 * @return поток изменений текста, конвертированный в [String]
 */
fun TextView.textChangesString(): Observable<String> {
    return textChanges().map(CharSequence::toString)
}

/**
 * @return поток изменений текста, конвертированный в [String] с пропуском начального значения
 */
fun TextView.textChangesStringSkipFirst(): Observable<String> {
    return textChanges().skipInitialValue().map(CharSequence::toString)
}

fun EditText.setTextSaveCursor(text: CharSequence) {
    this.text?.let { it.replace(0, it.length, text) }
}

/**
 * Расширение для EditText, добавляет listener на изменение текста и меняет его согласно коллбеку
 *
 * @param onTextChanged - коллбек, вызываемый каждый раз при изменении текста
 */
fun EditText.setOnTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            removeTextChangedListener(this)
            onTextChanged.invoke(s?.toString() ?: EMPTY_STRING)
            addTextChangedListener(this)
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

/**
 * Обновление текста в EditText, осуществляемое только тогда, когда он изменяется.
 * Происходит с сохранением текущей позиции курсора.
 *
 * @param text проставляемый текст
 */
fun EditText.setTextIfChanged(text: CharSequence) {
    val oldCursorPosition = selectionEnd
    val newCursorPosition = if (oldCursorPosition > text.length) text.length else oldCursorPosition
    if (TextUtils.equals(this.text, text)) return
    setText(text)
    setSelection(newCursorPosition)
}

/**
 * Делает текст зачеркнутым
 */
fun TextView.makeStrikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * Возвращает количество строк, занимаемое текстом
 */
fun TextView.measureLineCount(): Int {
    val fullWidth = paint.measureText(text.toString())
    val actualWidth = width - paddingEnd - paddingStart
    return (fullWidth / actualWidth).roundToInt()
}

/**
 * Установка drawables в TextView
 */
fun TextView.updateDrawables(
    left: Drawable? = compoundDrawables[0],
    top: Drawable? = compoundDrawables[1],
    right: Drawable? = compoundDrawables[2],
    bottom: Drawable? = compoundDrawables[3]
) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom)
}
