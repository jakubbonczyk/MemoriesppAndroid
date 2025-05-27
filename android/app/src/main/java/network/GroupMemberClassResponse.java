package network;

/**
 * Model odpowiedzi z serwera zawierający informacje o przypisaniu nauczyciela
 * do klasy w ramach danej grupy użytkowników.
 */
public class GroupMemberClassResponse {

    /** Identyfikator przypisania. */
    private int id;

    /** Identyfikator członka grupy. */
    private int groupMemberId;

    /** Identyfikator użytkownika (nauczyciela). */
    private int userId;

    /** Imię i nazwisko nauczyciela. */
    private String teacherName;

    /** Identyfikator klasy. */
    private int classId;

    /** Nazwa klasy. */
    private String className;

    /**
     * Zwraca identyfikator przypisania.
     *
     * @return identyfikator przypisania
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia identyfikator przypisania.
     *
     * @param id identyfikator przypisania
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca identyfikator członka grupy.
     *
     * @return identyfikator członka grupy
     */
    public int getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * Ustawia identyfikator członka grupy.
     *
     * @param groupMemberId identyfikator członka grupy
     */
    public void setGroupMemberId(int groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    /**
     * Zwraca identyfikator użytkownika (nauczyciela).
     *
     * @return identyfikator nauczyciela
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Ustawia identyfikator użytkownika (nauczyciela).
     *
     * @param userId identyfikator nauczyciela
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Zwraca imię i nazwisko nauczyciela.
     *
     * @return imię i nazwisko nauczyciela
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Ustawia imię i nazwisko nauczyciela.
     *
     * @param teacherName imię i nazwisko nauczyciela
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Ustawia identyfikator klasy.
     *
     * @param classId identyfikator klasy
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Zwraca nazwę klasy.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }

    /**
     * Ustawia nazwę klasy.
     *
     * @param className nazwa klasy
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
