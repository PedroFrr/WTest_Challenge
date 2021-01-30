package com.pedrofr.wtest.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.pedrofr.wtest.R
import java.text.Normalizer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    view?.let { activity?.toast(message, length) }
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

/**
 * Helper functions for the View layer of the app.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Glide helper functions
 */
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .centerCrop()
        .timeout(60000)
        .placeholder(R.drawable.ic_baseline_error_24) //TODO change drawable
        .dontAnimate()
        .into(this)
}

/**
 * Helper functions to validate form field
 */
fun String.isEmailValid() =
    Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(this).matches()

//fun String.isDateValid() =
//    Pattern.compile(
//        "[0-9]{1,2}(/)[0-9]{1,2}(/)[0-9]{4}"
//    ).matcher(this).matches()

fun String.isDateValid(): Boolean {
    val regexCondition = Pattern.compile(
        "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)"
    ).matcher(this).matches()

//    val date = LocalDateTime
//        .parse(q.ADDDATE)
//        .toLocalDate()
//        .format(
//            DateTimeFormatter
//                .ofLocalizedDate(FormatStyle.LONG)
//                .withLocale(Locale.ENGLISH)
//        )
//
//    val dateView = dat
    val futureDateCondition = true

    return regexCondition && futureDateCondition

}

//TODO improve this regex
fun String.isHifenAndCharactersValid(): Boolean {
    val rx1 =  Pattern.compile("[A-Z]{6}(-)[A-Z]")
    val rx2 =  Pattern.compile("[A-Z]{5}(-)[A-Z]{1,2}")
    val rx3 =  Pattern.compile("[A-Z]{4}(-)[A-Z]{1,3}")
    val rx4=  Pattern.compile("[A-Z]{3}(-)[A-Z]{1,4}")
    val rx5=  Pattern.compile("[A-Z]{2}(-)[A-Z]{1,5}")
    val rx6=  Pattern.compile("[A-Z](-)[A-Z]{1,6}")

    return rx1.matcher(this).matches() ||
            rx2.matcher(this).matches() ||
            rx3.matcher(this).matches() ||
            rx4.matcher(this).matches() ||
            rx5.matcher(this).matches() ||
            rx6.matcher(this).matches()

}





/**
 * Helper to set search word to lower case and without accents
 */
fun String.removeNonSpacingMarks() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")
        .toLowerCase()


