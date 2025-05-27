package com.example.memoriespp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Przykładowy test jednostkowy, który wykonuje się na maszynie deweloperskiej (hoście).
 * Testuje prostą operację matematyczną (dodawanie).
 *
 * @see <a href="http://d.android.com/tools/testing">Dokumentacja testowania Android</a>
 */
public class ExampleUnitTest {

    /**
     * Testuje, czy dodawanie dwóch liczb działa poprawnie.
     * Sprawdza, czy wynik dodania 2 i 2 wynosi 4.
     */
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}