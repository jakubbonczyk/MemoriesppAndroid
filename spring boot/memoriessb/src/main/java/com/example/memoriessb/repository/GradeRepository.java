package com.example.memoriessb.repository;

import com.example.memoriessb.etities.Grade;
import com.example.memoriessb.etities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repozytorium JPA dla encji {@link Grade}.
 * Udostępnia metody do pobierania ocen na podstawie ucznia, nauczyciela, klasy oraz grupy.
 */
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    /**
     * Zwraca listę ocen danego ucznia z konkretnej klasy, posortowaną malejąco po ID.
     *
     * @param studentId identyfikator ucznia
     * @param classId   identyfikator klasy (przedmiotu)
     * @return lista ocen
     */
    List<Grade> findByStudent_IdAndSchoolClass_IdOrderByIdDesc(Integer studentId, Integer classId);

    /**
     * Zwraca listę nieprzeczytanych ocen dla danego ucznia.
     *
     * @param studentId identyfikator ucznia
     * @return lista nieprzeczytanych ocen
     */
    List<Grade> findByStudent_IdAndNotifiedFalse(Integer studentId);

    /**
     * Zwraca listę unikalnych klas (przedmiotów), w których nauczyciel wystawił oceny.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista klas
     */
    @Query("SELECT DISTINCT g.schoolClass FROM Grade g WHERE g.teacher.id = :teacherId")
    List<SchoolClass> findDistinctClassesByTeacherId(@Param("teacherId") Integer teacherId);

    /**
     * Zwraca listę ocen wystawionych przez danego nauczyciela w danej klasie.
     *
     * @param teacherId identyfikator nauczyciela
     * @param classId   identyfikator klasy
     * @return lista ocen
     */
    List<Grade> findByTeacher_IdAndSchoolClass_Id(Integer teacherId, Integer classId);

    /**
     * Zwraca listę unikalnych identyfikatorów grup, do których należą uczniowie oceniani przez danego nauczyciela.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista identyfikatorów grup
     */
    @Query("SELECT DISTINCT gm.userGroup.id " +
            "FROM Grade g JOIN g.student s " +
            "JOIN GroupMember gm ON gm.user.id = s.id " +
            "WHERE g.teacher.id = :teacherId")
    List<Integer> findDistinctGroupIdsByTeacherId(@Param("teacherId") Integer teacherId);

    /**
     * Zwraca listę wszystkich ocen danego ucznia, posortowaną malejąco po ID.
     *
     * @param studentId identyfikator ucznia
     * @return lista ocen
     */
    List<Grade> findByStudent_IdOrderByIdDesc(Integer studentId);
}
