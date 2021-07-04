package extensions

fun Int.toBinaryString(length: Int = 16): String {
    return String.format(
        "%${length}s",
        Integer.toBinaryString(this)
    ).replace(" ".toRegex(), "0")
}