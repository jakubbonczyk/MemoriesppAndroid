package com.example.memoriessb.service;


import com.example.memoriessb.DTO.GradeDetailDTO;
import com.example.memoriessb.DTO.GradeRequest;
import com.example.memoriessb.DTO.GradeSummaryDTO;
import com.example.memoriessb.DTO.SchoolClassDTO;
import com.example.memoriessb.etities.*;
import com.example.memoriessb.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberClassRepository groupMemberClassRepository;
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

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
        Optional<GroupMember> groupMember = groupMemberRepository.findByUser_Id(userId);

        if (groupMember.isEmpty()) {
            return List.of();
        }

        Integer groupId = groupMember.get().getUserGroup().getId();

        List<GroupMemberClass> gmcList = groupMemberClassRepository.findByGroupMember_UserGroup_Id(groupId);

        return gmcList.stream()
                .map(gmc -> gmc.getSchoolClass())
                .distinct()
                .map(sc -> new SchoolClassDTO(sc.getId(), sc.getClassName()))
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
}
