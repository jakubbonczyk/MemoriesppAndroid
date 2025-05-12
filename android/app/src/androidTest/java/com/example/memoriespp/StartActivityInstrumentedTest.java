package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StartActivityInstrumentedTest {

    @Before
    public void prepare() {
        StartActivity.ENABLE_ADS = false;
    }

    @Test
    public void testStartButtonNavigatesToLoginActivity() throws InterruptedException {
        ActivityScenario.launch(StartActivity.class);
        onView(withId(R.id.button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
    }
}
