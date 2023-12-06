package am.leon.leonutilit

import am.leon.leonutilit.databinding.ActivityMainBinding
import am.leon.utilities.android.extentions.DatePickerType
import am.leon.utilities.android.extentions.launchDayPicker
import am.leon.utilities.android.helpers.components.datePicker.OnDateSetListener
import am.leon.utilities.android.helpers.logging.getClassLogger
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

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
        logger.warning("onDateSet: $displayDate")
    }

    companion object {
        private val logger = getClassLogger()
    }
}