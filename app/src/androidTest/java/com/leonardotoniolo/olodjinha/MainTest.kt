package com.leonardotoniolo.olodjinha

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.DrawerActions.close
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.leonardotoniolo.olodjinha.view.activities.main.MainActivity
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainTest{


    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityTestRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun telaPrincipal() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}