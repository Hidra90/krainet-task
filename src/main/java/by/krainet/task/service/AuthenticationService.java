package by.krainet.task.service;

import by.krainet.task.model.PersonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService {

    public Long getCurrentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails principal = (PersonDetails) auth.getPrincipal();
            return principal.person().getId();
        } catch (Exception e) {
            log.error("Error getting user ID ",  e);
            throw e;
        }
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ADMIN") ||
                                grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
