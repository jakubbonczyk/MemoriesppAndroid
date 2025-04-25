package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GroupMemberClassApi {

    @GET("/api/groups/{groupId}/assignments")
    Call<List<AssignmentDTO>> getAssignments(@Path("groupId") int groupId);
}
