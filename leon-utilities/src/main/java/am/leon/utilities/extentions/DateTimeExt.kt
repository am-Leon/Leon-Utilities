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

fun Context.launchDayPicker(listener: OnDateSetListener) {
    // Get calendar instance
    val calendar = Calendar.getInstance()

    // Get current time
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

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
    val datePickerDialog = DatePickerDialog(
        this, listener, currentYear, currentMonth, currentDay
    ).apply {
        // Set dates
        datePicker.maxDate = maxTime
        datePicker.minDate = minTime
    }

    // Show dialog
    datePickerDialog.show()
}

fun Context.launchTimePicker(onTimeSetListener: OnTimeSetListener) {
    val timePickerDialog = TimePickerDialog(
        this, onTimeSetListener, Calendar.getInstance()[Calendar.HOUR_OF_DAY],
        Calendar.getInstance()[Calendar.MINUTE], false
    )
    timePickerDialog.show()
}
