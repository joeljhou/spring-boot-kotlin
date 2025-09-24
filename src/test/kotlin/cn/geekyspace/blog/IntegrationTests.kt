package cn.geekyspace.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    /**
     * 测试前执行的初始化方法
     * 可以在这里创建共享资源、打印初始化信息等
     */
    @BeforeAll
    fun setup() {
        println(">> 测试初始化开始")
    }

    /**
     * 测试首页博客页面
     * 1. 请求 "/"
     * 2. 断言 HTTP 状态码为 200 OK
     * 3. 断言页面内容包含 "<h1>Blog</h1>"
     */
    @Test
    fun `Assert blog page title, content and status code`() {
        // 发送 GET 请求，获取 ResponseEntity
        val entity = restTemplate.getForEntity<String>("/")

        // 打印状态码
        println("状态码: ${entity.statusCode}")
        // 断言状态码为 200 OK
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        // 打印页面内容
        println("页面内容: ${entity.body}")
        // 断言页面包含 <h1>Blog</h1>
        assertThat(entity.body).contains("<h1>Blog</h1>")
    }

    /**
     * 测试文章页面（TODO）
     * 可以在这里实现对文章详情页的测试
     */
    @Test
    fun `Assert article page title, content and status code`() {
        println(">> 待实现文章页面测试")
    }

    /**
     * 测试完成后执行的清理方法
     * 可以在这里释放共享资源或打印结束信息
     */
    @AfterAll
    fun teardown() {
        println(">> 测试结束")
    }

}
