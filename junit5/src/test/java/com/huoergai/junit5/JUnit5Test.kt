package com.huoergai.junit5

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.io.BufferedWriter
import java.io.FileWriter
import java.lang.reflect.Method
import java.nio.file.Path
import java.util.logging.Logger
import java.util.stream.Stream

/**
 * D&T: 2020-06-22 15:16
 * Des:
 */
@Tag("TagTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JUnit5Test {

    private val logger = Logger.getLogger("Unit-TEST")
    private val fruits = listOf<String>("apple", "banana", "watermelon")

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

    /**
     * 1.A template for test cases
     * 2.the invoking time dependents on the number of invocation contexts returned by the registered
     *   providers.
     * 3.
     */
    @ExtendWith(MyTestTemplateInvocationContextProvider::class)
    @TestTemplate
    fun testTemplate(fruit: String) {
        println("param=$fruit")
        // assertTrue(fruits.contains(fruit))
    }

    /**
     * "@TestMethodOrder" to configure the test method execution order for the annotated test class
     *     1.build-in MethodOrder: Alphanumeric/OrderAnnotation/Random
     *     2.Annotate test class with "@TestMethodOrder", and specific a kind of MethodOrder
     */
    @Order(0)
    @Test
    fun testMethodOrder() {
        println("methodOrder:")
    }

    /**
     * used to configure the test instance lifecycle for the annotated test class
     *      1.TestInstance.Lifecycle: PER_CLASS/PER_METHOD(default mode);
     *      2.with the "per-class" mode, it's possible to declare @BeforeAll and @AfterAll
     *        on non-static methods and on interface default methods.
     */
    fun testInstance() {

    }

    /**
     * use to declare custom display names for test classes and test methods.
     *     1. take precedence over @DisplayNameGenerator
     */
    @DisplayName("renamedMethod")
    @Test
    fun displayName() {

    }

    /**
     * 1.@Nested tests have more capabilities to express the relationship among several groups of tests
     * 2.denote that the annotated class is a non-static nested test class;
     */
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
    inner class A_display_name_generator_with_build_in_param {

        @Test
        fun method_with_undersocre() {

        }
    }

    class IndicativeSentences : DisplayNameGenerator.ReplaceUnderscores() {
        override fun generateDisplayNameForMethod(
            testClass: Class<*>?,
            testMethod: Method?
        ): String {
            return "${testClass?.simpleName} ${testMethod?.name}".replace('_', ' ')
        }
    }

    @Nested
    @DisplayNameGeneration(IndicativeSentences::class)
    inner class A_display_name_generator_with_custom_param {

        @Test
        fun method_with_underscore() {

        }
    }

    /**
     * Declare a custom display name generator for the test class
     *
     */
    @Test
    fun displayNameGen() {
        println("displayNameGen")
    }

    /**
     * executed before other methods in the current class
     * 1.must be static unless the test class use the "per-class" test instance lifecycle
     *
     */
    @BeforeAll
    fun beforeAll() {
        println("------------------- UtilsTest start -----------------")
    }

    /**
     * executed after other methods in the current class
     */
    @AfterAll
    fun afterAll() {
        println("------------------- UtilsTest end -----------------")
    }

    /**
     * execute before each test method
     */
    @BeforeEach
    // fun beforeEach(testInfo: TestInfo, repetInfo: RepetitionInfo) {
    fun beforeEach(testInfo: TestInfo) {
        println("beforeEach")
        /*if (repetInfo != null) {
            logger.info("about to execute repetition ${repetInfo.currentRepetition} of ${repetInfo.totalRepetitions} for ${testInfo.displayName}")
        }*/
        if (testInfo.tags.contains("fast")) {
            println("fast test start")
        }
        if (testInfo.tags.contains("slow")) {
            println("slow test start")
        }
    }

    /**
     * execute after each test method
     */
    @AfterEach
    fun afterEach() {
        println("afterEach")
    }

    @Target(
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CLASS,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(AnnotationRetention.RUNTIME)
    @Tag("fast")
    annotation class FastTag

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Tag("slow")
    @Test
    annotation class SlowTest

    /**
     * Tag & Meta-Annotations and Composed Annotations
     * 1.used to declare tags for filtering tests,either at the class or method.
     * 2.as annotations can be used as meta-annotations, we can define our own composed annotation
     *   with automatically inherit the semantics of its meta-annotations.
     */
    @Tag("methodTag")
    @FastTag
    @Test
    fun tagTest() {
        println("customTag")
    }

    @SlowTest
    fun tagTestTest() {
        println("slow test ...")
    }

    /**
     * used to disable a test class or test method.
     * 1.recommend provide short explanation for why a test class or test method has been disabled.
     */
    @Disabled("bug No.101 has not been fixed yet!")
    @Test
    fun disabledTest() {

    }

    /**
     * fail a test when it's timeout
     */
    @Timeout(5)
    @Test
    fun timeoutTest() {

    }

    /**
     * used to register extensions declaratively;
     * 1.register one or more extensions declaratively by annotating a test interface,test class
     *   test method or custom composed annotation with @ExtendWith(...)
     *
     * @see testTemplate method for example
     */
    @Test
    fun extendWith() {

    }

    /**
     * used to register extensions programmatically via fields.
     *
     */
    @Test
    fun registerExtension() {

    }

    /**
     * used to create and clean a temporary directory for an individual test or all tests in a test class.
     */
    @Test
    fun tempDir(@TempDir tmpDir: Path) {
        val file = tmpDir.resolve("test.txt").toFile()
        val bw = BufferedWriter(FileWriter(file, true))
        bw.write("hello:${System.currentTimeMillis()} \n")
        println("path=${file.absolutePath}")
        bw.flush()
        bw.close()
    }

}