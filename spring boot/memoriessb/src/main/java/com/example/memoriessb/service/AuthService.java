package com.example.memoriessb.service;

import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.DTO.RegisterUserRequest;
import com.example.memoriessb.etities.*;
import com.example.memoriessb.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * Serwis odpowiedzialny za autoryzację i rejestrację użytkowników.
 * Obsługuje logowanie na podstawie danych logowania oraz tworzenie nowych kont użytkowników.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final SensitiveDataRepository sensitiveDataRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberClassRepository groupMemberClassRepository;

    /**
     * Loguje użytkownika na podstawie loginu i hasła.
     *
     * @param login    login użytkownika (np. e-mail)
     * @param password niezaszyfrowane hasło
     * @return dane użytkownika w postaci {@link LoginResponse}
     * @throws IllegalArgumentException jeśli login nie istnieje lub hasło jest nieprawidłowe
     */
    public LoginResponse login(String login, String password) {
        SensitiveData data = sensitiveDataRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy login"));

        if (!passwordEncoder.matches(password, data.getPassword())) {
            throw new IllegalArgumentException("Nieprawidłowe hasło");
        }

        User user = data.getUser();
        String b64 = user.getImage() == null ? null : Base64.getEncoder().encodeToString(user.getImage());

        List<GroupMember> members = groupMemberRepository.findAllByUser_Id(user.getId());
        String className = (members != null && !members.isEmpty() && members.get(0).getUserGroup() != null)
                ? members.get(0).getUserGroup().getGroupName()
                : null;


        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                b64,
                className
        );
    }


    /**
     * Rejestruje nowego użytkownika w systemie.
     * Tworzy użytkownika, jego dane logowania oraz przypisuje do grupy, jeśli podano identyfikator grupy.
     *
     * @param request dane rejestracyjne użytkownika
     * @throws IllegalArgumentException jeśli login jest już zajęty lub nie istnieje wskazana grupa
     */
    public void registerUser(RegisterUserRequest request) {
        log.debug("registerUser() request = {}", request);
        try {
            if (sensitiveDataRepository.findByLogin(request.getLogin()).isPresent()) {
                log.warn("Register failed: login '{}' already exists", request.getLogin());
                throw new IllegalArgumentException("Podany login jest już zajęty");
            }

            User user = new User();
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setRole(request.getRole());
            user = userRepository.save(user);
            log.info("Created User id={}, name={} {}", user.getId(), user.getName(), user.getSurname());

            SensitiveData data = new SensitiveData();
            data.setLogin(request.getLogin());
            data.setPassword(passwordEncoder.encode(request.getPassword()));
            data.setUser(user);
            sensitiveDataRepository.save(data);
            log.info("Created SensitiveData for userId={}", user.getId());

            Integer gid = request.getGroupId();
            if (gid != null) {
                log.debug("Assigning userId={} to groupId={}", user.getId(), gid);
                UserGroup group = userGroupRepository.findById(gid)
                        .orElseThrow(() -> new IllegalArgumentException("Nie ma takiej grupy: " + gid));
                GroupMember gm = new GroupMember();
                gm.setUserGroup(group);
                gm.setUser(user);
                groupMemberRepository.save(gm);
                log.info("User id={} added to Group id={}", user.getId(), group.getId());
            }

        } catch (Exception ex) {
            log.error("Error in registerUser(): {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
