package com.liceadev.architectcoders.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.liceadev.architectcoders.R
import kotlinx.coroutines.delay
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class TestRecord {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(NavActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Test
    fun testRecord() {
        val textView = onView(
            allOf(
                withText("MyPhotos"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(withId(R.id.appBar))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("MyPhotos")))
    }
}
