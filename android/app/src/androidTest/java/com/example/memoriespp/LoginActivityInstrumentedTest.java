package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        onView(withId(R.id.editTextText)).perform(typeText("invalid@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Błąd logowania")).check(matches(isDisplayed()));
    }

    @Test
    public void testAdminLoginDisplaysAdminHomeFragment() {
        onView(withId(R.id.editTextText)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());

        onView(withId(R.id.button3)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.manageUsersButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testTeacherLoginDisplaysHomeFragment() {
        onView(withId(R.id.editTextText)).perform(typeText("teacher"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.classesButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testStudentLoginDisplaysHomeFragment() {
        onView(withId(R.id.editTextText)).perform(typeText("student"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.classesButton)).check(matches(isDisplayed()));
    }
}
