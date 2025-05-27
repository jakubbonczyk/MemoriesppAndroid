package com.example.memoriespp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.UiObject;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testy instrumentalne sprawdzające kluczowe funkcjonalności panelu administratora.
 * Testują m.in.:
 * - logowanie jako administrator,
 * - dodawanie nowych klas i grup,
 * - dodawanie nauczycieli,
 * - przypisywanie nauczycieli do przedmiotów,
 * - dodawanie lekcji do planu zajęć.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdminActivitiesInstrumentedTest {

    /**
     * Przygotowuje środowisko testowe przez uruchomienie ekranu logowania
     * i zalogowanie się jako administrator.
     *
     * @throws InterruptedException w przypadku przerwy w wątku
     */
    @Before
    public void launchAndLoginAsAdmin() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.editTextText)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());
        Thread.sleep(1000);
    }

    /**
     * Testuje możliwość dodania nowej klasy przez administratora.
     *
     * @throws InterruptedException w przypadku przerwy w wątku
     */
    @Test
    public void testAdminCanAddNewClass() throws InterruptedException {
        onView(withId(R.id.manageClassesButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewClassButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.classInput)).perform(typeText("Informatyka"), closeSoftKeyboard());
        onView(withId(R.id.addNewClassButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewClassButton)).check(matches(isDisplayed()));
    }

    /**
     * Testuje możliwość dodania nowej grupy przez administratora.
     *
     * @throws InterruptedException w przypadku przerwy w wątku
     */
    @Test
    public void testAdminCanAddNewGroup() throws InterruptedException {
        onView(withId(R.id.manageGroupsButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewGroupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.emailInput)).perform(typeText("Klasa 2A"), closeSoftKeyboard());
        onView(withId(R.id.addNewGroupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewGroupButton)).check(matches(isDisplayed()));
    }

    /**
     * Testuje możliwość dodania nowego nauczyciela przez administratora.
     *
     * @throws InterruptedException w przypadku przerwy w wątku
     */
    @Test
    public void testAdminCanAddNewTeacher() throws InterruptedException {
        onView(withId(R.id.manageUsersButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewUserButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Nauczyciele")).perform(click());

        onView(withId(R.id.spinner2)).perform(click());
        onView(withText("Klasa 2A")).perform(click());

        onView(withId(R.id.nameInput)).perform(typeText("Jan"), closeSoftKeyboard());
        onView(withId(R.id.surnameInput)).perform(typeText("Kowalski"), closeSoftKeyboard());
        onView(withId(R.id.emailInput)).perform(typeText("jan.kowalski@example.com"), closeSoftKeyboard());

        onView(withId(R.id.addNewUserButton)).perform(scrollTo(), click());
        Thread.sleep(1000);

        onView(withId(R.id.defineNewUserButton)).check(matches(isDisplayed()));
    }

    /**
     * Testuje przypisanie nauczyciela do przedmiotu w klasie przez administratora.
     *
     * @throws InterruptedException w przypadku przerwy w wątku
     */
    @Test
    public void testAdminAssignsTeacherToSubject() throws InterruptedException {
        onView(withId(R.id.manageClassesButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.assignTeacherToClassButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.chooseGroupSpinner)).perform(click());
        onView(withText("Klasa 2A")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.subjectSpinner)).perform(click());
        onView(withText("Informatyka")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.addNewClassButton)).perform(click());
        Thread.sleep(1000);

        onView(withText("Przypisz nauczyciela do przedmiotu")).check(matches(isDisplayed()));
    }

    /**
     * Testuje możliwość dodania lekcji do planu zajęć przez administratora,
     * w tym wybór daty i godzin oraz potwierdzenie dodania lekcji.
     *
     * @throws InterruptedException      w przypadku przerwy w wątku
     * @throws UiObjectNotFoundException jeśli element UI nie zostanie znaleziony
     */
    @Test
    public void testAdminCanAddLessonToSchedule() throws InterruptedException, UiObjectNotFoundException {
        onView(withId(R.id.manageGroupsButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.manageScheduleButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.groupSpinner)).perform(click());
        onView(withText("Klasa 2A")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.assignmentSpinner)).perform(click());
        onView(withText("Jan Kowalski — Informatyka")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.dateBtn)).perform(click());
        Thread.sleep(1000);

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject nextMonth = device.findObject(new UiSelector().descriptionContains("Następny miesiąc"));
        if (nextMonth.exists()) {
            nextMonth.click();
            Thread.sleep(500);
        }

        UiObject day15 = device.findObject(new UiSelector().text("15"));
        if (day15.exists()) {
            day15.click();
            Thread.sleep(500);
        }

        UiObject okButton = device.findObject(new UiSelector().text("OK"));
        if (okButton.exists()) {
            okButton.click();
            Thread.sleep(500);
        }

        onView(withId(R.id.startTimeSpinner)).perform(click());
        onView(withText("09:00")).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.endTimeSpinner)).perform(click());
        onView(withText("10:00")).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.addLessonBtn)).perform(click());
        Thread.sleep(1000);

        onView(withText("2025-06-15 | Jan Kowalski — Informatyka | 09:00–10:00"))
                .check(matches(isDisplayed()));
    }
}
