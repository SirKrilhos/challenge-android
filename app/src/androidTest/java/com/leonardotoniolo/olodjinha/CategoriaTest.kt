package com.leonardotoniolo.olodjinha

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.leonardotoniolo.olodjinha.model.Categoria
import android.support.test.rule.ActivityTestRule
import com.leonardotoniolo.olodjinha.view.activities.categoria.CategoriaActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class CategoriaTest {

    private lateinit var categoria: Categoria

    @get:Rule
    var activityTestRule: ActivityTestRule<CategoriaActivity>
            = ActivityTestRule<CategoriaActivity>(CategoriaActivity::class.java, true, false)

    @Test
    fun telaCelular() {
        categoria = Categoria(3, "Celulares", null)
        val intent = CategoriaActivity.newInstance(InstrumentationRegistry.getInstrumentation().targetContext, categoria)
        activityTestRule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_normal))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.products_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun telaSemProdutos() {
        categoria = Categoria(9, "Papelaria", null)
        val intent = CategoriaActivity.newInstance(InstrumentationRegistry.getInstrumentation().targetContext, categoria)
        activityTestRule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.texto_sem_produto))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}