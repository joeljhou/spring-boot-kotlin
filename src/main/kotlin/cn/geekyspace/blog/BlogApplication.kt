package cn.geekyspace.blog

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args){
        // 关闭 Banner，默认会在控制台打印一个 ASCII Banner（Spring Boot 标志）
        setBannerMode(Banner.Mode.OFF)
    }
}
