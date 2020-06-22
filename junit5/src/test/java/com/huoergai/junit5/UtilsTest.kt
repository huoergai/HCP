package com.huoergai.junit5

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.logging.Logger
import java.util.stream.Stream

/**
 * D&T: 2020-06-22 15:16
 * Des:
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UtilsTest {

    private val logger = Logger.getLogger("Unit-TEST")

    @BeforeAll
    fun beforeAll() {
        println("------------------- UtilsTest start -----------------")
    }

    /**
     * 每个测试方法执行前会执行该方法
     */
    @BeforeEach
    // fun beforeEach(testInfo: TestInfo, repetInfo: RepetitionInfo) {
    fun beforeEach(testInfo: TestInfo) {
        println("beforeEach")
        /*if (repetInfo != null) {
            logger.info("about to execute repetition ${repetInfo.currentRepetition} of ${repetInfo.totalRepetitions} for ${testInfo.displayName}")
        }*/
    }

    /**
     * 修改测试方法显示名称
     */
    @DisplayName("addAlia")
    @Test
    fun add() {
        Assertions.assertEquals(5, Utils.add(2, 3))
        Assertions.assertEquals(4, 2 + 2)
        println("add done")
    }

    /**
     * 常规的测试方法,类似 JUnit4 中的 @Test
     */
    @Test
    fun minus() {
        println("minus done")
    }

    fun getData(): Stream<Int> {
        return Stream.of(100)
    }

    /**
     * 可自定义参数的测试方法：
     *     1.用 @ParameterizedTest 标记方法
     *     2.再用 @ValueSource() 传入基本类型的参数
     *     3.使用 @MethodSource 可将另一方法的返回值作为参数传入，但需用 stream 包装
     */
    @ParameterizedTest
    @MethodSource("getData")
    @ValueSource(ints = [10, 20, 30])
    fun parameterizedTest(num: Int) {
        println("No.$num")
    }

    /**
     * 可重复执行的测试方法
     *     1.可指定重复执行次数
     *     2.可获取重复信息
     */
    @RepeatedTest(3, name = "repeat {currentRepetition}/{totalRepetitions}")
    fun repeatedTest(repetitionInfo: RepetitionInfo) {
        println("repeatedTest:${repetitionInfo.currentRepetition}")
    }

    /**
     * for dynamic test:
     *     1.区别于以 @Test 等标记的静态测试方法，在编译期就已经确定，执行行为在运行期不能改变。
     *     2.TestFactory method must return a single DynamicNode / a Stream / a Collection/Iterable/Iterator/
     *       or array of DynamicNode instances
     *     3.It's not a test case but rather a factory for test cases.
     *     4.
     */
    @TestFactory
    fun testFactory(): Stream<DynamicTest> {
        return Stream.of("wo", "laa", "johnny")
            .map { dynamicTest("$it test") { println("${it.length}") } }
    }

    /**
     * this will result in a JUnitException, due to unacceptable return type.
     */
    // @TestFactory
    // fun testFactory1(): List<String> {
    //     return Arrays.asList("HELLO")
    // }

    @TestFactory
    fun testFactory2(): Collection<DynamicTest> {
        return listOf(
            dynamicTest("1st dynamic test") { assertEquals(3, Utils.add(1, 2)) },
            dynamicTest("2th dynamic test") { assert(true, { 2 + 2 == 4 }) }
        )
    }


    /* inner class MyTestTemplateInvocationContextProvider : TestTemplateInvocationContextProvider {

         private fun invocationContext(parameter: String): TestTemplateInvocationContext {

             return object : TestTemplateInvocationContext {
                 override fun getDisplayName(invocationIndex: Int): String {
                     return parameter
                 }

                 override fun getAdditionalExtensions(): MutableList<Extension> {
                     return Collections.singletonList(object : ParameterResolver {
                         override fun supportsParameter(
                             parameterContext: ParameterContext?, extensionContext: ExtensionContext?
                         ): Boolean {
                             return parameterContext?.parameter?.type == String::class.java
                         }

                         override fun resolveParameter(
                             parameterContext: ParameterContext?, extensionContext: ExtensionContext?
                         ): Any {
                             return parameter
                         }
                     })
                 }
             }
         }


         override fun supportsTestTemplate(context: ExtensionContext?): Boolean {
             return true
         }

         override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext> {
             return Stream.of(invocationContext("apple"), invocationContext("banana"))
         }

     }

     private val fruits = listOf<String>("apple", "banana", "watermelon")

     */
    /**
     * 1.A template for test cases
     * 2.the invoking time dependents on the number of invocation contexts returned by the registered
     *   providers.
     * 3.
     *//*
    @ExtendWith(MyTestTemplateInvocationContextProvider::class)
    @TestTemplate
    fun testTemplate(fruit: String) {
        assertTrue(fruits.contains(fruit))
    }*/

    // @DisplayName("HELLO")
    @Test
    fun displayNameGen() {
        println("displayNameGen")
    }

    @AfterEach
    fun afterEach() {
        println("afterEach")
    }

    @AfterAll
    fun afterAll() {
        println("------------------- UtilsTest end -----------------")
    }
}