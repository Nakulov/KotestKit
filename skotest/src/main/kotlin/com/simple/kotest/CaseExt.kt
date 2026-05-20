package com.simple.kotest

fun <A1> case(
    arg1: A1
): Case1<A1> {
    return Case1(arg1)
}

fun <A1, A2> case(
    arg1: A1,
    arg2: A2
): Case2<A1, A2> {
    return Case2(arg1, arg2)
}

fun <A1, A2, A3> case(
    arg1: A1,
    arg2: A2,
    arg3: A3
): Case3<A1, A2, A3> {
    return Case3(arg1, arg2, arg3)
}

fun <A1, A2, A3, A4> case(
    arg1: A1,
    arg2: A2,
    arg3: A3,
    arg4: A4,
): Case4<A1, A2, A3, A4> {
    return Case4(arg1, arg2, arg3, arg4)
}

fun <A1, A2, A3, A4, A5> case(
    arg1: A1,
    arg2: A2,
    arg3: A3,
    arg4: A4,
    arg5: A5,
): Case5<A1, A2, A3, A4, A5> {
    return Case5(arg1, arg2, arg3, arg4, arg5)
}

fun <A1, A2, A3, A4, A5, A6> case(
    arg1: A1,
    arg2: A2,
    arg3: A3,
    arg4: A4,
    arg5: A5,
    arg6: A6,
): Case6<A1, A2, A3, A4, A5, A6> {
    return Case6(arg1, arg2, arg3, arg4, arg5, arg6)
}

fun <A1, A2, A3, A4, A5, A6, A7> case(
    arg1: A1,
    arg2: A2,
    arg3: A3,
    arg4: A4,
    arg5: A5,
    arg6: A6,
    arg7: A7,
): Case7<A1, A2, A3, A4, A5, A6, A7> {
    return Case7(arg1, arg2, arg3, arg4, arg5, arg6, arg7)
}

fun <A1, A2, A3, A4, A5, A6, A7, A8> case(
    arg1: A1,
    arg2: A2,
    arg3: A3,
    arg4: A4,
    arg5: A5,
    arg6: A6,
    arg7: A7,
    arg8: A8,
): Case8<A1, A2, A3, A4, A5, A6, A7, A8> {
    return Case8(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)
}