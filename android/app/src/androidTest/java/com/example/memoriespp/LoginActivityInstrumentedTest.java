package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.RootMatchers.withDecorView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {

    private View decorView;

    @Before
    public void setUp() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @Test
    public void testLoginWithInvalidCredentialsShowsSnackbar() {
        onView(withId(R.id.editTextText)).perform(typeText("invalid@example.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("wrongpassword"));
        onView(withId(R.id.button3)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Błąd logowania"))
                .check(matches(isDisplayed()));
    }

}
