package am.leon.utilities.extentions

import am.leon.utilities.R
import am.leon.utilities.data.models.callbacks.OnDateSetListener
import am.leon.utilities.data.models.callbacks.OnTimeSetListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


fun Date.getDeviceCurrentDateTimeFormatted(pattern: String): String {
    val format = SimpleDateFormat(pattern, Locale.US)
    return format.format(this)
}

fun Context.getFormattedDuration(time: Int): String {
    val hour = time / 60
    val min = time % 60
    return getString(R.string.time_format, hour, min)
}

enum class DatePickerType { GENERAL, BEFORE_TODAY, AFTER_TODAY }

fun Context.launchDayPicker(pickerType: DatePickerType, listener: OnDateSetListener) {
    // Get calendar instance
    val calendar = Calendar.getInstance()

    // Get current time
    val calenderYear = calendar.get(Calendar.YEAR)
    val calenderMonth = calendar.get(Calendar.MONTH)
    val calenderDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog: DatePickerDialog
    when (pickerType) {
        DatePickerType.GENERAL -> datePickerDialog = DatePickerDialog(
            this, listener, calenderYear, calenderMonth, calenderDay
        )

        DatePickerType.BEFORE_TODAY -> datePickerDialog = DatePickerDialog(
            this, listener, calenderYear, calenderMonth, calenderDay
        ).apply {
            datePicker.maxDate = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        }

        DatePickerType.AFTER_TODAY -> {
            // Move day as first day of the month
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            // Min = time after changes
            val minTime = System.currentTimeMillis() - 1000 // disable before today

            // Move day as first day of the month
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            // Move to next month default is 1
            calendar.add(Calendar.MONTH, 5)
            // Go back one day (so last day of current month)
            calendar.add(Calendar.DAY_OF_MONTH, -1)

            // Max = current
            val maxTime = calendar.timeInMillis

            // Create dialog
            datePickerDialog = DatePickerDialog(
                this, listener, calenderYear, calenderMonth, calenderDay
            ).apply {
                // Set dates
                datePicker.maxDate = maxTime
                datePicker.minDate = minTime
            }
        }
    }
    datePickerDialog.show()
}

fun Context.launchTimePicker(onTimeSetListener: OnTimeSetListener) {
    val timePickerDialog = TimePickerDialog(
        this, onTimeSetListener, Calendar.getInstance()[Calendar.HOUR_OF_DAY],
        Calendar.getInstance()[Calendar.MINUTE], false
    )
    timePickerDialog.show()
}
