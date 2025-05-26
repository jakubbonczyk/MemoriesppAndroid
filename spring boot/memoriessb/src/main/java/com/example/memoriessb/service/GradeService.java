package com.example.memoriessb.service;


import com.example.memoriessb.DTO.*;
import com.example.memoriessb.etities.*;
import com.example.memoriessb.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serwis odpowiedzialny za zarządzanie ocenami uczniów.
 * Umożliwia dodawanie ocen, pobieranie szczegółów, analizowanie średnich oraz zarządzanie powiadomieniami o ocenach.
 */
@Service
@AllArgsConstructor
public class GradeService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberClassRepository groupMemberClassRepository;
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserGroupRepository userGroupRepository;

    private final DateTimeFormatter FMT = DateTimeFormatter.ISO_DATE;

    /**
     * Dodaje nową ocenę na podstawie przesłanego żądania.
     *
     * @param req dane oceny zawierające identyfikatory ucznia, nauczyciela i przedmiotu
     * @throws EntityNotFoundException jeśli uczeń, nauczyciel lub przedmiot nie istnieje
     */
    public void addGrade(GradeRequest req) {
        Grade g = new Grade();
        g.setGrade(req.getGrade());
        g.setDescription(req.getDescription());
        g.setType(req.getType());

        User student = userRepository.findById(req.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Uczeń nie istnieje"));
        User teacher = userRepository.findById(req.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Nauczyciel nie istnieje"));
        SchoolClass sc = schoolClassRepository.findById(req.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Przedmiot nie istnieje"));

        g.setStudent(student);
        g.setTeacher(teacher);
        g.setSchoolClass(sc);

        gradeRepository.save(g);
    }

    /**
     * Zwraca listę przedmiotów ucznia wraz ze średnią ocen z każdego z nich.
     *
     * @param userId identyfikator ucznia
     * @return lista przedmiotów z obliczonymi średnimi ocenami
     * @throws EntityNotFoundException jeśli użytkownik nie jest przypisany do grupy
     */
    public List<SchoolClassDTO> getSubjectsForStudent(int userId) {
        GroupMember gm = groupMemberRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Brak członkostwa w grupie"));

        List<SchoolClass> classes = groupMemberClassRepository
                .findByGroupMember_UserGroup_Id(gm.getUserGroup().getId())
                .stream()
                .map(GroupMemberClass::getSchoolClass)
                .distinct()
                .collect(Collectors.toList());

        return classes.stream()
                .map(sc -> {
                    List<Grade> grades = gradeRepository
                            .findByStudent_IdAndSchoolClass_IdOrderByIdDesc(userId, sc.getId());

                    double avg = grades.stream()
                            .mapToInt(Grade::getGrade)
                            .average()
                            .orElse(Double.NaN);

                    Double average = Double.isNaN(avg) ? null : avg;
                    return new SchoolClassDTO(sc.getId(), sc.getClassName(), average);
                })
                .collect(Collectors.toList());
    }

    /**
     * Zwraca szczegóły wszystkich ocen ucznia z danego przedmiotu.
     *
     * @param studentId identyfikator ucznia
     * @param classId   identyfikator przedmiotu
     * @return lista ocen z danego przedmiotu
     */
    public List<GradeSummaryDTO> getGradesForSubject(int studentId, int classId) {
        return gradeRepository.findByStudent_IdAndSchoolClass_IdOrderByIdDesc(studentId, classId)
                .stream()
                .map(g -> new GradeSummaryDTO(
                        g.getId(),
                        g.getGrade(),
                        g.getType(),
                        g.getIssueDate().format(FMT)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca szczegóły jednej oceny (pełne informacje).
     *
     * @param gradeId identyfikator oceny
     * @return szczegóły oceny w postaci DTO
     * @throws EntityNotFoundException jeśli ocena nie istnieje
     */
    public GradeDetailDTO getGradeDetails(int gradeId) {
        Grade g = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Ocena nie istnieje"));
        return new GradeDetailDTO(
                g.getId(),
                g.getGrade(),
                g.getType(),
                g.getIssueDate().format(FMT),
                g.getDescription(),
                g.getStudent().getName() + " " + g.getStudent().getSurname(),
                g.getTeacher().getName() + " " + g.getTeacher().getSurname(),
                g.getSchoolClass().getClassName()
        );
    }

    /**
     * Zwraca nieprzeczytane (nowe) oceny ucznia oraz oznacza je jako przeczytane.
     *
     * @param studentId identyfikator ucznia
     * @return lista nowych ocen
     */
    @Transactional
    public List<NewGradeDTO> getNewGradesForStudent(int studentId) {
        List<Grade> newGrades = gradeRepository.findByStudent_IdAndNotifiedFalse(studentId);

        List<NewGradeDTO> dtos = newGrades.stream()
                .map(g -> new NewGradeDTO(
                        g.getId(),
                        g.getGrade(),
                        g.getType(),
                        g.getIssueDate().toString(),
                        g.getSchoolClass().getClassName()
                ))
                .collect(Collectors.toList());

        newGrades.forEach(g -> g.setNotified(true));
        gradeRepository.saveAll(newGrades);

        return dtos;
    }

    /**
     * Zwraca listę klas, w których nauczyciel wystawił oceny, wraz ze średnią ocen z każdej klasy.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista przedmiotów z obliczonymi średnimi ocenami
     */
    public List<SchoolClassDTO> getClassesForTeacher(int teacherId) {
        List<SchoolClass> classes = gradeRepository.findDistinctClassesByTeacherId(teacherId);
        return classes.stream()
                .map(sc -> new SchoolClassDTO(
                        sc.getId(),
                        sc.getClassName(),
                        gradeRepository.findByTeacher_IdAndSchoolClass_Id(teacherId, sc.getId())
                                .stream().mapToInt(Grade::getGrade).average().orElse(Double.NaN)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę uczniów przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return lista uczniów w postaci DTO
     */
    public List<StudentDTO> getStudentsForGroup(int groupId) {
        return groupMemberRepository.findByUserGroup_Id(groupId).stream()
                .map(GroupMember::getUser)
                .filter(user -> user.getRole() == User.Role.S)
                .map(user -> new StudentDTO(
                        user.getId(),
                        user.getName(),
                        user.getSurname()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca wszystkie oceny ucznia posortowane malejąco po ID.
     *
     * @param studentId identyfikator ucznia
     * @return lista ocen ucznia
     */
    public List<GradeSummaryDTO> getAllGradesForStudent(int studentId) {
        return gradeRepository.findByStudent_IdOrderByIdDesc(studentId).stream()
                .map(g -> new GradeSummaryDTO(
                        g.getId(),
                        g.getGrade(),
                        g.getType(),
                        g.getIssueDate().format(FMT)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę grup, w których nauczyciel wystawił jakiekolwiek oceny.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista grup w postaci DTO
     */
    public List<TeacherGroupDTO> getGroupsForTeacher(int teacherId) {
        List<Integer> groupIds = gradeRepository
                .findDistinctGroupIdsByTeacherId(teacherId);

        return userGroupRepository
                .findAllById(groupIds)
                .stream()
                .map(ug -> new TeacherGroupDTO(ug.getId(), ug.getGroupName()))
                .collect(Collectors.toList());
    }

}
