package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interfejs API odpowiedzialny za pobieranie przypisań nauczycieli do przedmiotów w ramach grup.
 */
public interface GroupMemberClassApi {

    /**
     * Pobiera listę przypisań nauczycieli do klas (przedmiotów) dla wskazanej grupy.
     *
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą przypisań nauczycieli w danej grupie
     */
    @GET("/api/groups/{groupId}/assignments")
    Call<List<AssignmentDTO>> getAssignments(@Path("groupId") int groupId);
}
