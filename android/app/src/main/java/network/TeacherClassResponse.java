package network;

public class TeacherClassResponse {
    private int id;
    private String className;

    public TeacherClassResponse(int id, String className) {
        this.id = id;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }
}
