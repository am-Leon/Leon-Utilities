package am.leon.utilities.android.helpers.components.spinner

import android.view.View
import android.widget.AdapterView

@Suppress("UNCHECKED_CAST")
interface SpinnerOnItemSelected<M> : AdapterView.OnItemSelectedListener {

    fun onItemSelected(selectedItem: M?)

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelected(parent?.getItemAtPosition(position) as M?)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}