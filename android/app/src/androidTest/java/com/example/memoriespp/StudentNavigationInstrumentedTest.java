package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StudentNavigationInstrumentedTest {

    @Before
    public void setUp() {
        StartActivity.ENABLE_ADS = false;
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testStudentNavigation() {
        onView(withId(R.id.gradesButton))
                .perform(scrollTo(), click());

        onView(withId(R.id.yourGrades))
                .check(matches(isDisplayed()));

        onView(withId(R.id.home))
                .perform(click());

        onView(withId(R.id.gradesButton))
                .perform(scrollTo(), click());

        onView(withId(R.id.constraintLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.home))
                .perform(click());

        onView(withId(R.id.gradesButton))
                .perform(scrollTo(), click());

        onView(withId(R.id.constraintLayout))
                .check(matches(isDisplayed()));
    }
}
