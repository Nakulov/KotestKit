package io.github.nakulov.kotestkit.serialization

import io.github.nakulov.kotestkit.Case1
import io.github.nakulov.kotestkit.Case2
import io.github.nakulov.kotestkit.Case3
import io.github.nakulov.kotestkit.Case4
import io.github.nakulov.kotestkit.Case5
import io.github.nakulov.kotestkit.Case6
import io.github.nakulov.kotestkit.Case7
import io.github.nakulov.kotestkit.Case8
import io.github.nakulov.kotestkit.TestOptions
import io.kotest.common.KotestInternal
import io.kotest.core.factory.TestFactory
import io.kotest.core.names.TestName
import io.kotest.core.spec.RootTest
import io.kotest.core.spec.TestDefinition
import io.kotest.core.spec.style.TestXMethod
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecRootScope
import io.kotest.core.spec.style.scopes.RootContainerWithConfigBuilder
import io.kotest.core.test.TestScope
import io.kotest.core.test.config.TestConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

private const val ONLY_JSON_TESTS = "Внутри withJson тесты регистрируют только shouldDecodeTest и shouldEncodeTest"

/**
 * Приёмник блока [withJson]. Заглушки ниже — члены класса: член внутреннего приёмника перекрывает функции спеки,
 * поэтому другой способ создать тест внутри группы не компилируется.
 */
class JsonTestScope internal constructor(
    @PublishedApi internal val spec: BehaviorSpecRootScope,
    @PublishedApi internal val json: Json,
) {
    /** Тест: ресурс -> DTO. Имя по умолчанию: `dto/user/full.json -> UserDto`. Сравнение через `shouldBe`. */
    inline fun <reified T : Any> shouldDecodeTest(resource: String, expected: T, options: TestOptions = TestOptions()) {
        decodeTest(spec, json, resource, expected, T::class.simpleName, options) { json.serializersModule.serializer<T>() }
    }

    /** Тест: DTO -> ресурс. Имя по умолчанию: `UserDto -> dto/user/full.json`. Сравнение через `shouldEqualJson`. */
    inline fun <reified T : Any> shouldEncodeTest(value: T, resource: String, options: TestOptions = TestOptions()) {
        encodeTest(spec, json, value, resource, T::class.simpleName, options) { json.serializersModule.serializer<T>() }
    }

    // Тесты ядра KotestKit

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output> shouldReturnTest(expected: Output, options: TestOptions, block: suspend TestScope.() -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output> shouldReturnTest(expected: Output, block: suspend TestScope.() -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input> shouldReturnTest(expected: Output, input: Input, options: TestOptions, block: suspend TestScope.(Input) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input> shouldReturnTest(expected: Output, input: Input, block: suspend TestScope.(Input) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, options: TestOptions, block: suspend TestScope.(Input1, Input2) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, block: suspend TestScope.(Input1, Input2) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, block: suspend TestScope.(Input1, Input2, Input3) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3, Input4) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, block: suspend TestScope.(Input1, Input2, Input3, Input4) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, input7: Input7, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, input7: Input7, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, input7: Input7, input8: Input8, options: TestOptions, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> shouldReturnTest(expected: Output, input1: Input1, input2: Input2, input3: Input3, input4: Input4, input5: Input5, input6: Input6, input7: Input7, input8: Input8, block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Output): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowTest(options: TestOptions, block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowTest(block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowExactlyTest(options: TestOptions, block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowExactlyTest(block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowWithMessageTest(message: String, options: TestOptions, block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T : Throwable> shouldThrowWithMessageTest(message: String, block: suspend TestScope.() -> Any?): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1> withGivens(nameFn: (Case1<A1>) -> String, vararg cases: Case1<A1>, test: suspend BehaviorSpecGivenContainerScope.(A1) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2> withGivens(nameFn: (Case2<A1, A2>) -> String, vararg cases: Case2<A1, A2>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3> withGivens(nameFn: (Case3<A1, A2, A3>) -> String, vararg cases: Case3<A1, A2, A3>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3, A4> withGivens(nameFn: (Case4<A1, A2, A3, A4>) -> String, vararg cases: Case4<A1, A2, A3, A4>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3, A4) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3, A4, A5> withGivens(nameFn: (Case5<A1, A2, A3, A4, A5>) -> String, vararg cases: Case5<A1, A2, A3, A4, A5>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3, A4, A5) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3, A4, A5, A6> withGivens(nameFn: (Case6<A1, A2, A3, A4, A5, A6>) -> String, vararg cases: Case6<A1, A2, A3, A4, A5, A6>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3, A4, A5, A6) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3, A4, A5, A6, A7> withGivens(nameFn: (Case7<A1, A2, A3, A4, A5, A6, A7>) -> String, vararg cases: Case7<A1, A2, A3, A4, A5, A6, A7>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3, A4, A5, A6, A7) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <A1, A2, A3, A4, A5, A6, A7, A8> withGivens(nameFn: (Case8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String, vararg cases: Case8<A1, A2, A3, A4, A5, A6, A7, A8>, test: suspend BehaviorSpecGivenContainerScope.(A1, A2, A3, A4, A5, A6, A7, A8) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    // BehaviorSpec

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun given(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun given(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun Given(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun Given(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xgiven(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xgiven(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xGiven(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xGiven(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fgiven(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fgiven(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fGiven(name: String, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fGiven(name: String): RootContainerWithConfigBuilder<BehaviorSpecGivenContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun addGiven(name: String, xmethod: TestXMethod, test: suspend BehaviorSpecGivenContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun context(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun context(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun Context(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun Context(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xcontext(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xcontext(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xContext(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun xContext(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fcontext(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fcontext(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fContext(name: String, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun fContext(name: String): RootContainerWithConfigBuilder<BehaviorSpecContextContainerScope> = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun addContext(name: String, xmethod: TestXMethod, test: suspend BehaviorSpecContextContainerScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    // Данные io.kotest.datatest

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(first: T, second: T, vararg rest: T, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(nameFn: (T) -> String, first: T, second: T, vararg rest: T, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(ts: Sequence<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(nameFn: (T) -> String, ts: Sequence<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(ts: Iterable<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(nameFn: (T) -> String, ts: Iterable<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withData(data: Map<String, T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(first: T, second: T, vararg rest: T, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(nameFn: (T) -> String, first: T, second: T, vararg rest: T, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(ts: Sequence<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(nameFn: (T) -> String, ts: Sequence<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(ts: Iterable<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(nameFn: (T) -> String, ts: Iterable<T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withContexts(data: Map<String, T>, test: suspend BehaviorSpecContextContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(first: T, second: T, vararg rest: T, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(nameFn: (T) -> String, first: T, second: T, vararg rest: T, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(ts: Sequence<T>, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(nameFn: (T) -> String, ts: Sequence<T>, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(ts: Iterable<T>, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(nameFn: (T) -> String, ts: Iterable<T>, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun <T> withGivens(data: Map<String, T>, test: suspend BehaviorSpecGivenContainerScope.(T) -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    // Спека и RootScope

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    @OptIn(KotestInternal::class)
    @Suppress("DEPRECATION")
    fun add(test: RootTest): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun add(test: TestDefinition): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun include(factory: TestFactory): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun include(prefix: String, factory: TestFactory): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun addTest(testName: TestName, disabled: Boolean, config: TestConfig?, test: suspend TestScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun addTest(testName: TestName, xmethod: TestXMethod, config: TestConfig?, test: suspend TestScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun addContainer(testName: TestName, xmethod: TestXMethod, config: TestConfig?, test: suspend TestScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun withJson(json: Json, block: JsonTestScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)

    @Deprecated(ONLY_JSON_TESTS, level = DeprecationLevel.ERROR)
    fun withJson(block: JsonTestScope.() -> Unit): Unit = throw UnsupportedOperationException(ONLY_JSON_TESTS)
}
