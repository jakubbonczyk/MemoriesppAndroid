package com.example.memoriespp;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher dla Toastów, używany do weryfikacji obecności komunikatów Toast w testach instrumentalnych.
 * Wykorzystuje informacje o typie okna oraz tokenach aplikacji, aby określić, czy dany element
 * jest Toastem.
 */
public class ToastMatcher extends TypeSafeMatcher<Root> {

    /**
     * Opisuje matcher, który sprawdza, czy dany element to Toast.
     *
     * @param description opis testowanego elementu
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    /**
     * Sprawdza, czy dany element jest Toastem, analizując jego typ oraz tokeny okna.
     *
     * @param root element, który ma być sprawdzony
     * @return true, jeśli element jest Toastem, w przeciwnym razie false
     */
    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams2().type;

        boolean isToast = type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                || type == WindowManager.LayoutParams.TYPE_APPLICATION;

        if (isToast) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            return windowToken == appToken;
        }

        return false;
    }
}
