package io.github.nakulov.kotestkit

import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecRootScope
import io.kotest.datatest.withGivens

fun <A1> BehaviorSpecRootScope.withGivens(
    nameFn: (Case1<A1>) -> String,
    vararg cases: Case1<A1>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1) }
}

fun <A1, A2> BehaviorSpecRootScope.withGivens(
    nameFn: (Case2<A1, A2>) -> String,
    vararg cases: Case2<A1, A2>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2) }
}

fun <A1, A2, A3> BehaviorSpecRootScope.withGivens(
    nameFn: (Case3<A1, A2, A3>) -> String,
    vararg cases: Case3<A1, A2, A3>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3) }
}

fun <A1, A2, A3, A4> BehaviorSpecRootScope.withGivens(
    nameFn: (Case4<A1, A2, A3, A4>) -> String,
    vararg cases: Case4<A1, A2, A3, A4>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4) }
}

fun <A1, A2, A3, A4, A5> BehaviorSpecRootScope.withGivens(
    nameFn: (Case5<A1, A2, A3, A4, A5>) -> String,
    vararg cases: Case5<A1, A2, A3, A4, A5>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5) }
}

fun <A1, A2, A3, A4, A5, A6> BehaviorSpecRootScope.withGivens(
    nameFn: (Case6<A1, A2, A3, A4, A5, A6>) -> String,
    vararg cases: Case6<A1, A2, A3, A4, A5, A6>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6) }
}

fun <A1, A2, A3, A4, A5, A6, A7> BehaviorSpecRootScope.withGivens(
    nameFn: (Case7<A1, A2, A3, A4, A5, A6, A7>) -> String,
    vararg cases: Case7<A1, A2, A3, A4, A5, A6, A7>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case -> test(case.arg1, case.arg2, case.arg3, case.arg4, case.arg5, case.arg6, case.arg7) }
}

fun <A1, A2, A3, A4, A5, A6, A7, A8> BehaviorSpecRootScope.withGivens(
    nameFn: (Case8<A1, A2, A3, A4, A5, A6, A7, A8>) -> String,
    vararg cases: Case8<A1, A2, A3, A4, A5, A6, A7, A8>,
    test: suspend BehaviorSpecGivenContainerScope.(arg1: A1, arg2: A2, arg3: A3, arg4: A4, arg5: A5, arg6: A6, arg7: A7, arg8: A8) -> Unit
) {
    withGivens(
        ts = cases.toList(),
        nameFn = nameFn
    ) { case ->
        test(
            case.arg1,
            case.arg2,
            case.arg3,
            case.arg4,
            case.arg5,
            case.arg6,
            case.arg7,
            case.arg8
        )
    }
}