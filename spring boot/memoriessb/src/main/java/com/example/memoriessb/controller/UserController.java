package com.example.memoriessb.controller;

import com.example.memoriessb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    /**
     * GET /api/users/{id}  – zwraca name/surname/role/image
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getUser(@PathVariable Integer id) {
        log.info("GET /api/users/{}", id);
        Map<String,Object> dto = userService.getUserDto(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/users/{id}/profile-image  – aktualizuje avatar
     */
    @PutMapping("/{id}/profile-image")
    public ResponseEntity<Void> updateProfileImage(
            @PathVariable Integer id,
            @RequestBody Map<String,String> body
    ) {
        log.info("PUT /api/users/{}/profile-image", id);
        String b64 = body.get("image");
        userService.updateUserImage(id, b64);
        return ResponseEntity.ok().build();
    }
}
