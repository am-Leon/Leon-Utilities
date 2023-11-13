package am.leon.utilities.common.domain.models.enums

import am.leon.utilities.R
import androidx.annotation.StringRes

enum class LanguageSet(val id: Int, val prefix: String, @StringRes val resName: Int) {
    ARABIC(1001, "ar", R.string.arabic),
    ENGLISH(1002, "en", R.string.english);

    companion object {
        fun find(prefix: String): LanguageSet {
            for (i in 0 until values().size) {
                if (values()[i].prefix == prefix)
                    return values()[i]
            }
            return ENGLISH
        }

        fun findByResName(resName: Int): LanguageSet {
            for (i in 0 until values().size) {
                if (values()[i].resName == resName)
                    return values()[i]
            }
            return ENGLISH
        }

        fun findByID(id: Int): LanguageSet {
            for (i in 0 until values().size) {
                if (values()[i].id == id)
                    return values()[i]
            }
            return ENGLISH
        }
    }
}