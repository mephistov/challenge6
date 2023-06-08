package com.nicolascastilla.domain

import com.nicolascastilla.domain.repositories.extensions.orderToFirebaseDb
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var valor = "123456_654987"
        assertEquals(valor.orderToFirebaseDb(), "123456_654987")
        valor = "10_+4"
        assertEquals(valor.orderToFirebaseDb(), "4_10")
        valor = "casa fea"
        assertEquals(valor.orderToFirebaseDb(), "ERROR")
        valor = ""
        assertEquals(valor.orderToFirebaseDb(), "ERROR")
        valor = "_3242342"
        assertEquals(valor.orderToFirebaseDb(), "ERROR")
        valor = "7_10_1"
        assertEquals(valor.orderToFirebaseDb(), "1_7_10")
    }
}