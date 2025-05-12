package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PasswordResetInstrumentedTest {

    @Before
    public void launchLoginActivity() {
        ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void testResetPasswordButtonOpensResetPasswordActivity() {
        onView(withId(R.id.button4)).perform(click());

        onView(withId(R.id.button4)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyEmailShowsToast() {
        ActivityScenario.launch(ResetPasswordActivity.class);

        onView(withId(R.id.button4)).perform(click());

        onView(withText("Wprowadź adres e-mail"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }


}
