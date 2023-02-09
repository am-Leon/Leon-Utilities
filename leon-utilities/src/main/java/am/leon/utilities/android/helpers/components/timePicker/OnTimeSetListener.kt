package am.leon.utilities.android.helpers.components.timePicker

import android.app.TimePickerDialog
import android.widget.TimePicker

interface OnTimeSetListener : TimePickerDialog.OnTimeSetListener {

    fun onTimeSet(displayTime: String, serviceFormat: String)

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val s = StringBuilder()
        if (hourOfDay < 10)
            s.append("0").append(hourOfDay).append(":")
        else
            s.append(hourOfDay).append(":")

        if (minute < 10)
            s.append("0").append((minute))
        else
            s.append((minute))

        onTimeSet(s.toString(), s.append(":00").toString())
    }
}