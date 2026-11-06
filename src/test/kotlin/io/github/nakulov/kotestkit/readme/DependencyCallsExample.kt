package io.github.nakulov.kotestkit.readme

import io.github.nakulov.kotestkit.then
import io.github.nakulov.kotestkit.`when`
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk

data class Order(val id: Long, val total: Int)

interface OrderRepository {
    suspend fun save(order: Order)
}

class OrderService(private val repository: OrderRepository) {
    suspend fun place(order: Order) = repository.save(order)
}

class OrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerRoot

    val repository = mockk<OrderRepository>()
    val service = OrderService(repository)

    given("новый заказ") {
        val order = Order(id = 1000L, total = 20)
        coJustRun { repository.save(order) }
        `when` {
            service.place(order)
            then {
                coVerifySequence { repository.save(order) }
            }
        }
    }
})
