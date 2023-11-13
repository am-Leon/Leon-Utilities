package am.leon.utilities.android.extentions


fun ByteArray.toCharArray(): CharArray {
    val charArray = CharArray(size * 2)
    var index = 0

    for (byte in this) {
        val unsignedByte = byte.toInt() and 0xFF

        val firstNibble = unsignedByte shr 4
        charArray[index++] = firstNibble.toHexChar()

        val secondNibble = unsignedByte and 0x0F
        charArray[index++] = secondNibble.toHexChar()
    }

    return charArray
}

fun Int.toHexChar(): Char {
    return if (this in 0..9)
        '0' + this
    else
        'A' + (this - 10)
}

fun CharArray.toByteArray(): ByteArray {
    val byteArray = mutableListOf<Byte>()

    for (i in indices step 2) {
        val hexToTwosComplement: Int = if (i + 1 < size)
            charArrayOf(this[i], this[i + 1]).hexToTwosComplement()
        else
            charArrayOf(this[i], '0').hexToTwosComplement()

        byteArray.add(hexToTwosComplement.toByte())
    }

    return byteArray.toByteArray()
}

private fun CharArray.hexToTwosComplement(): Int {
    var decimalValue = 0
    val isNegative =
        isNotEmpty() && (this[0] == '8' || this[0] == '9' || this[0] == 'A' || this[0] == 'B' || this[0] == 'C' || this[0] == 'D' || this[0] == 'E' || this[0] == 'F')

    for (element in this) {
        decimalValue = when (element) {
            in '0'..'9' -> {
                val digitValue = element - '0'
                decimalValue shl 4 or digitValue
            }

            in 'A'..'F' -> {
                val digitValue = element - 'A' + 10
                decimalValue shl 4 or digitValue
            }

            else -> {
                throw NumberFormatException("Invalid character in StringBuilder: $element")
            }
        }
    }

    return if (isNegative) {
        // Apply two's complement for negative values
        decimalValue - (1 shl 8)
    } else {
        decimalValue
    }
}

private fun hexToByteArray(hexString: String): ByteArray {
    val len = hexString.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(
            hexString[i + 1], 16
        )).toByte()
        i += 2
    }
    return data
}
