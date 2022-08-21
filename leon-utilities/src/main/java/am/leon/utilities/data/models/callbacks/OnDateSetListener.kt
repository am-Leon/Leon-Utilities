package am.leon.utilities.data.models.callbacks

import android.app.DatePickerDialog
import android.widget.DatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface OnDateSetListener : DatePickerDialog.OnDateSetListener {
    /**
     * @param selectedDate will be produced as date object.
     */
    fun onDateSet(displayDate: String, selectedDate: Date?)

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val s = StringBuilder()
        s.append(year).append("-")

        if ((month + 1) < 10)
            s.append("0").append((month + 1)).append("-")
        else
            s.append((month + 1)).append("-")

        if (dayOfMonth < 10)
            s.append("0").append(dayOfMonth)
        else
            s.append(dayOfMonth)

        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dateObject: Date? = sdf.parse(s.toString()) // Handle the ParseException here
        onDateSet(s.toString(), dateObject)
    }
}

