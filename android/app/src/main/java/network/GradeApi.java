package network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfejs API odpowiedzialny za operacje związane z ocenami.
 * Umożliwia dodawanie ocen, pobieranie ocen uczniów, informacji o przedmiotach i szczegółach ocen.
 */
public interface GradeApi {

    /**
     * Dodaje nową ocenę do systemu na podstawie przesłanych danych.
     *
     * @param gradeData mapa zawierająca dane oceny (uczeń, przedmiot, wartość, opis, nauczyciel)
     * @return obiekt {@link Call} bez odpowiedzi (status HTTP)
     */
    @POST("/api/grades")
    Call<Void> addGrade(@Body Map<String, Object> gradeData);

    /**
     * Pobiera wszystkie oceny przypisane danemu uczniowi.
     *
     * @param studentId identyfikator ucznia
     * @return obiekt {@link Call} z listą ocen
     */
    @GET("/api/grades/student/{studentId}")
    Call<List<Grade>> getGradesForStudent(@Path("studentId") int studentId);

    /**
     * Pobiera listę przedmiotów, do których przypisany jest dany uczeń.
     *
     * @param userId identyfikator ucznia
     * @return obiekt {@link Call} z listą przedmiotów
     */
    @GET("/api/class/student/{userId}/subjects")
    Call<List<ClassResponse>> getSubjectsForStudent(@Path("userId") int userId);

    /**
     * Pobiera oceny ucznia dla wybranego przedmiotu.
     *
     * @param studentId identyfikator ucznia
     * @param subjectId identyfikator przedmiotu
     * @return obiekt {@link Call} z listą ocen dla danego przedmiotu
     */
    @GET("/api/grades/student/{studentId}/subject/{subjectId}")
    Call<List<GradeResponse>> getGradesForSubject(
            @Path("studentId") int studentId,
            @Path("subjectId") int subjectId
    );

    /**
     * Pobiera szczegółowe informacje o konkretnej ocenie.
     *
     * @param gradeId identyfikator oceny
     * @return obiekt {@link Call} ze szczegółami oceny
     */
    @GET("api/grades/{gradeId}")
    Call<GradeDetailDTO> getGradeDetails(@Path("gradeId") int gradeId);

    /**
     * Pobiera nowe (nieprzeczytane) oceny ucznia.
     *
     * @param studentId identyfikator ucznia
     * @return obiekt {@link Call} z listą nowych ocen
     */
    @GET("/api/grades/student/{studentId}/new")
    Call<List<NewGradeResponse>> getNewGrades(@Path("studentId") int studentId);

    /**
     * Pobiera listę uczniów przypisanych do konkretnej grupy.
     *
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą uczniów
     */
    @GET("/api/grades/group/{groupId}/students")
    Call<List<StudentResponse>> getStudentsForGroup(@Path("groupId") int groupId);

    /**
     * Pobiera grupy, do których przypisany jest dany nauczyciel.
     *
     * @param teacherId identyfikator nauczyciela
     * @return obiekt {@link Call} z listą grup nauczyciela
     */
    @GET("/api/grades/teacher/{teacherId}/groups")
    Call<List<TeacherGroupResponse>> getGroupsForTeacher(@Path("teacherId") int teacherId);
}
