package am.leon.leonutilit

import am.leon.leonutilit.databinding.ActivityMainBinding
import am.leon.utilities.android.extentions.DatePickerType
import am.leon.utilities.android.extentions.launchDayPicker
import am.leon.utilities.android.helpers.components.datePicker.OnDateSetListener
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), OnDateSetListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputPassword.setOnClickListener {
            launchDayPicker(DatePickerType.BEFORE_TODAY, this)
        }
    }

    override fun onDateSet(displayDate: String, selectedDate: Date?) {
        Log.e(TAG, "onDateSet: $displayDate")
    }
}