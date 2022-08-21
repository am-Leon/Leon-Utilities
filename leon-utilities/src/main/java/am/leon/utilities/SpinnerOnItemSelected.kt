package am.leon.utilities

import android.view.View
import android.widget.AdapterView

interface SpinnerOnItemSelected : AdapterView.OnItemSelectedListener {

    fun onItemSelected(selectedItem: Any?)

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelected(parent?.getItemAtPosition(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}