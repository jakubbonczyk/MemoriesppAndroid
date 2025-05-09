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

    public List<SchoolClassDTO> getSubjectsForStudent(int userId) {
        // 1) znajdź GroupMember
        GroupMember gm = groupMemberRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Brak członkostwa w grupie"));

        // 2) klasy z tej grupy
        List<SchoolClass> classes = groupMemberClassRepository
                .findByGroupMember_UserGroup_Id(gm.getUserGroup().getId())
                .stream()
                .map(GroupMemberClass::getSchoolClass)
                .distinct()
                .collect(Collectors.toList());

        // 3) dla każdej klasy policz średnią
        return classes.stream()
                .map(sc -> {
                    // pobierz wszystkie oceny ucznia z tej klasy
                    List<Grade> grades = gradeRepository
                            .findByStudent_IdAndSchoolClass_IdOrderByIdDesc(userId, sc.getId());

                    // policz średnią
                    double avg = grades.stream()
                            .mapToInt(Grade::getGrade)
                            .average()
                            .orElse(Double.NaN);

                    Double average = Double.isNaN(avg) ? null : avg;
                    return new SchoolClassDTO(sc.getId(), sc.getClassName(), average);
                })
                .collect(Collectors.toList());
    }


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

    @Transactional
    public List<NewGradeDTO> getNewGradesForStudent(int studentId) {
        // 1) pobierz niepowiadomione oceny
        List<Grade> newGrades = gradeRepository.findByStudent_IdAndNotifiedFalse(studentId);

        // 2) zmapuj na DTO
        List<NewGradeDTO> dtos = newGrades.stream()
                .map(g -> new NewGradeDTO(
                        g.getId(),
                        g.getGrade(),
                        g.getType(),
                        g.getIssueDate().toString(),
                        g.getSchoolClass().getClassName()
                ))
                .collect(Collectors.toList());

        // 3) oznacz jako powiadomione
        newGrades.forEach(g -> g.setNotified(true));
        gradeRepository.saveAll(newGrades);

        return dtos;
    }

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





    public List<TeacherGroupDTO> getGroupsForTeacher(int teacherId) {
        // 1) wyciągnij pośrednio z ocen unikalne ID grup
        List<Integer> groupIds = gradeRepository
                .findDistinctGroupIdsByTeacherId(teacherId);

        // 2) pobierz te grupy z bazy i zamapuj na DTO
        return userGroupRepository
                .findAllById(groupIds)
                .stream()
                .map(ug -> new TeacherGroupDTO(ug.getId(), ug.getGroupName()))
                .collect(Collectors.toList());
    }

}
