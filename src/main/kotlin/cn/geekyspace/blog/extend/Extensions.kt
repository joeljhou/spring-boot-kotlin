package cn.geekyspace.blog.extend

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

/**
 * 扩展函数：将 LocalDateTime 格式化
 */
fun LocalDateTime.format(): String = this.format(chineseDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

/**
 * 英文日期格式化器
 * 格式：yyyy-MM-dd <序数日> yyyy
 * 示例：2025-09-24 24th 2025
 */
private val englishDateFormatter = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd")                                 // 年-月-日
    .appendLiteral(" ")                                           // 空格分隔
    .appendText(ChronoField.DAY_OF_MONTH, daysLookup)  // 日 -> 英文序数
    .appendLiteral(" ")                                           // 空格分隔
    .appendPattern("yyyy")                                       // 再次显示年份
    .toFormatter(Locale.ENGLISH)

/**
 * 根据数字返回英文序数后缀
 * 例如 1 -> "1st", 2 -> "2nd", 3 -> "3rd", 4 -> "4th", 11 -> "11th"
 */
private fun getOrdinal(n: Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else -> "${n}th"
}

/**
 * 中文日期格式化器
 * 示例：2025年09月24日
 */
private val chineseDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.CHINA)


/**
 * 将字符串转换为 URL-friendly 的 slug
 * 规则：
 * - 全部转小写
 * - 换行符替换为空格
 * - 非字母数字字符替换为空格
 * - 多个空格合并为单个 "-"
 * - 移除连续重复的 "-"
 *
 * 例如：
 * "Hello, World!" -> "hello-world"
 */
fun String.toSlug() = lowercase(Locale.getDefault())
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")

fun main(){
    // 测试日期格式化
    val now = LocalDateTime.now()
    println("当前时间（中文格式）: ${now.format()}") // 示例输出：2025年09月24日

    // 测试 toSlug 函数
    val title = "Hello, Kotlin 世界！ 2025"
    val slug = title.toSlug()
    println("原字符串: $title")
    println("转换后的 slug: $slug") // 示例输出：hello-kotlin-2025
}
