package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testy instrumentalne aktywności logowania oraz podstawowych funkcji
 * aplikacji dla różnych ról użytkowników (administrator, nauczyciel, uczeń).
 *
 * Testy obejmują m.in.:
 * - próby logowania z niepoprawnymi danymi,
 * - poprawne logowanie dla różnych ról,
 * - wyświetlanie odpowiednich fragmentów po zalogowaniu,
 * - przeglądanie ocen przez ucznia,
 * - dodawanie oceny przez nauczyciela,
 * - weryfikację wyświetlania nowej oceny dla ucznia.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {

    private View decorView;

    /**
     * Przygotowuje środowisko testowe, uruchamia aktywność logowania
     * i pobiera główny widok okna.
     */
    @Before
    public void setUp() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    /**
     * Testuje wyświetlanie komunikatu o błędzie logowania po podaniu niepoprawnych danych.
     */
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

    /**
     * Testuje poprawne logowanie administratora
     * i wyświetlenie elementów interfejsu dedykowanych administratorowi.
     */
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

    /**
     * Testuje poprawne logowanie nauczyciela
     * i wyświetlenie fragmentu domowego nauczyciela.
     */
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

    /**
     * Testuje poprawne logowanie ucznia
     * i wyświetlenie fragmentu domowego ucznia.
     */
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

    /**
     * Testuje możliwość przeglądania ocen przez ucznia.
     */
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

    /**
     * Testuje możliwość dodawania ocen przez nauczyciela.
     */
    @Test
    public void testTeacherCanAddGrade() throws InterruptedException {
        onView(withId(R.id.editTextText)).perform(typeText("teacher"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.gradesButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.addGradeButton)).perform(click());
        Thread.sleep(1000);

        onView(withText("Klasa 1")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.studentsSpinner)).perform(click());
        onView(withText("Anna Kowalska")).perform(click());

        onView(withId(R.id.gradesSpinner)).perform(click());
        onView(withText("2")).perform(click());

        onView(withId(R.id.typeEditText)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextMultiLine)).perform(typeText("test"), closeSoftKeyboard());

        onView(withId(R.id.addGradeButton)).perform(scrollTo(), click());

        Thread.sleep(2000);
    }

    /**
     * Testuje, czy uczeń może zobaczyć nowo dodaną ocenę wraz z jej szczegółami.
     */
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
