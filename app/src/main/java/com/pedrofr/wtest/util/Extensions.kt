package com.pedrofr.wtest.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.pedrofr.wtest.R
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*
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
fun ImageView.loadImage(imageUrl: String, placeHolderResourceId: Int) {
    Glide.with(this)
        .load(imageUrl)
        .centerCrop()
        .timeout(60000)
        .placeholder(placeHolderResourceId)
        .error(R.drawable.ic_baseline_error_24)
        .into(this)
}

fun ImageView.loadCircleImage(imageUrl: String, placeHolderResourceId: Int) {
    Glide.with(this)
        .load(imageUrl)
        .circleCrop() //shapes image into a circle
        .timeout(60000)
        .placeholder(placeHolderResourceId)
        .error(R.drawable.ic_baseline_error_24)
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


fun String.isDateFormatValid() = Pattern.compile(
        "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)"
    ).matcher(this).matches()


/**
 * Helper to set search word to lower case and without accents
 */
fun String.removeNonSpacingMarks() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")
        .toLowerCase()

/**
 * Date helper functions
 */

fun String.getDate(format: String): Date? {
//    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    return formatter.parse(this)

}

fun String.timestampToDate(): String {
    val formatterParser = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.getDefault())
    formatterParser.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatterParser.parse(this)

    val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return date?.let{
        formattedDate.format(date)
    } ?: ""
}

