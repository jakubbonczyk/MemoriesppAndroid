package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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

        onView(withId(R.id.gradesButton)).check(matches(isDisplayed()));
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

        onView(withId(R.id.gradesButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testStudentCanViewGrades() throws InterruptedException {
        onView(withId(R.id.editTextText)).perform(typeText("student"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.gradesButton)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.yourGrades)).check(matches(isDisplayed()));
    }

    @Test
    public void testTeacherCanAddGrade() throws InterruptedException {
        // Logowanie jako teacher
        onView(withId(R.id.editTextText)).perform(typeText("teacher"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        Thread.sleep(1000); // Poczekaj na przejście

        // Kliknij "Oceny"
        onView(withId(R.id.gradesButton)).perform(click());
        Thread.sleep(1000);

        // Kliknij "Dodaj ocenę"
        onView(withId(R.id.addGradeButton)).perform(click());
        Thread.sleep(1000);

        // Wybierz grupę (np. 1A)
        onView(withText("Klasa 1")).perform(click());
        Thread.sleep(1000);

        // Wybierz ucznia (np. Anna Kowalska)
        onView(withId(R.id.studentsSpinner)).perform(click());
        onView(withText("Anna Kowalska")).perform(click());

        // Wybierz ocenę 1
        onView(withId(R.id.gradesSpinner)).perform(click());
        onView(withText("2")).perform(click());

        // Wpisz typ i komentarz
        onView(withId(R.id.typeEditText)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextMultiLine)).perform(typeText("test"), closeSoftKeyboard());

        // Kliknij "Zapisz"
        onView(withId(R.id.addGradeButton)).perform(scrollTo(), click());

        Thread.sleep(2000);
    }

    @Test
    public void testStudentCanViewNewGrade() throws InterruptedException {
        onView(withId(R.id.editTextText)).perform(typeText("student"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.gradesButton)).perform(click());
        Thread.sleep(1000);

        onView(withText("Matematyka")).perform(click());
        Thread.sleep(1000);

        onView(withText("Test")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.grade)).check(matches(withText("2")));
        onView(withId(R.id.gradeType)).check(matches(withText("Typ oceny: Test")));
        onView(withId(R.id.gradeDescription)).check(matches(withText("test")));
    }


}
