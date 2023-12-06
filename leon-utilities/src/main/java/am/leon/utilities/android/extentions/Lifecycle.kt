package am.leon.utilities.android.extentions

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T : Any?, L : SharedFlow<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect {
                body.invoke(it)
            }
        }
    }
}

fun <T : Any?, L : LiveData<T?>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(
        if (this is Fragment) viewLifecycleOwner else this
    ) {
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            body(it)
        }
    }
}

inline fun <T> LifecycleOwner.repeatOnStarted(
    crossinline block: suspend CoroutineScope.() -> T
) {
    var executed = false // flag to track if the block has been executed
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            if (!executed) {
                block()
                executed = true
            }
        }
    }
}

fun LifecycleOwner.updateViewInUiThread(action: () -> Unit) {
    repeatOnStarted {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main) {
                action()
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
fun LifecycleOwner.openAppIntentSettingsResultLauncher(
    callBack: (result: ActivityResult) -> Unit
): ActivityResultLauncher<Intent>? {
    if (!this.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) return null
    val launcher: ActivityResultLauncher<Intent>
    val type = ActivityResultContracts.StartActivityForResult()
    val result = ActivityResultCallback<ActivityResult> {
        callBack.invoke(it)
    }
    launcher = when (this) {
        is Fragment -> this.registerForActivityResult(type, result)
        is ComponentActivity -> {
            this.registerForActivityResult(type, result)
        }

        else -> throw IllegalAccessException("must be called from a Fragment or AppCompatActivity")
    }
    return launcher
}
