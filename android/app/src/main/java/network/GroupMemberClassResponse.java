package network;

public class GroupMemberClassResponse {
    private int id;
    private int groupMemberId;
    private int userId;
    private String teacherName;
    private int classId;
    private String className;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGroupMemberId() { return groupMemberId; }
    public void setGroupMemberId(int groupMemberId) { this.groupMemberId = groupMemberId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
