package com.huoergai.junit5

import org.junit.jupiter.api.extension.*
import java.util.stream.Stream

/**
 * D&T: 2020-06-23 15:17
 * Des:
 */
class MyTestTemplateInvocationContextProvider : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(context: ExtensionContext): Boolean {
        return true
    }

    override fun provideTestTemplateInvocationContexts(
        context: ExtensionContext
    ): Stream<TestTemplateInvocationContext> {
        return Stream.of(invocationContext("apple"), invocationContext("banana"))
    }

    private fun invocationContext(parameter: String): TestTemplateInvocationContext {

        return object : TestTemplateInvocationContext {
            override fun getDisplayName(invocationIndex: Int): String {
                return "$parameter + No.$invocationIndex"
            }

            override fun getAdditionalExtensions(): List<Extension> {

                return listOf(object : ParameterResolver {

                    override fun supportsParameter(
                        parameterContext: ParameterContext, extensionContext: ExtensionContext
                    ): Boolean {
                        return parameterContext.parameter.type == String::class.java
                    }

                    override fun resolveParameter(
                        parameterContext: ParameterContext, extensionContext: ExtensionContext
                    ): Any {
                        return "$parameter + extension"
                    }
                })
            }
        }
    }
}