package network;

import com.google.gson.annotations.SerializedName;

/**
 * Model żądania tworzenia nowej klasy.
 * Używany przy wysyłaniu danych do serwera w celu utworzenia klasy.
 */
public class CreateClassRequest {

    /** Nazwa nowej klasy. */
    @SerializedName("className")
    private final String className;

    /**
     * Tworzy nowe żądanie utworzenia klasy.
     *
     * @param className nazwa klasy
     */
    public CreateClassRequest(String className) {
        this.className = className;
    }

    /**
     * Zwraca nazwę klasy do utworzenia.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }
}
