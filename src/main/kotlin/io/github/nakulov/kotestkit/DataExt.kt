package io.github.nakulov.kotestkit

import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecRootScope
import io.kotest.datatest.withData

fun <A1> BehaviorSpecRootScope.withData(
    nameFn: (Case1<A1>) -> String,
    vararg cases: Case1<A1>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1) }
}

fun <A1, A2> BehaviorSpecRootScope.withData(
    nameFn: (Case2<A1, A2>) -> String,
    vararg cases: Case2<A1, A2>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2) }
}

fun <A1, A2, A3> BehaviorSpecRootScope.withData(
    nameFn: (Case3<A1, A2, A3>) -> String,
    vararg cases: Case3<A1, A2, A3>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3) }
}

fun <A1, A2, A3, A4> BehaviorSpecRootScope.withData(
    nameFn: (Case4<A1, A2, A3, A4>) -> String,
    vararg cases: Case4<A1, A2, A3, A4>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4) }
}

fun <A1, A2, A3, A4, A5> BehaviorSpecRootScope.withData(
    nameFn: (Case5<A1, A2, A3, A4, A5>) -> String,
    vararg cases: Case5<A1, A2, A3, A4, A5>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5) }
}

fun <A1, A2, A3, A4, A5, A6> BehaviorSpecRootScope.withData(
    nameFn: (Case6<A1, A2, A3, A4, A5, A6>) -> String,
    vararg cases: Case6<A1, A2, A3, A4, A5, A6>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6) }
}

fun <A1, A2, A3, A4, A5, A6, A7> BehaviorSpecRootScope.withData(
    nameFn: (Case7<A1, A2, A3, A4, A5, A6, A7>) -> String,
    vararg cases: Case7<A1, A2, A3, A4, A5, A6, A7>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6, case.arg7) }
}

fun <A1, A2, A3, A4, A5, A6, A7, A8> BehaviorSpecRootScope.withData(
    nameFn: (Case8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String,
    vararg cases: Case8<A1, A2, A3, A4, A5, A6, A7, A8>,
    test: suspend BehaviorSpecContextContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8) -> Unit
) {
    withData(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case ->
        test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6, case.arg7, case.arg8)
    }
}