package me.happyman.scheduler.appuser;

import lombok.AllArgsConstructor;
import me.happyman.scheduler.registration.token.ConfirmationToken;
import me.happyman.scheduler.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email '%s' not found";
    private final static String EMAIL_ALREADY_TAKEN_MSG = "Email '%s' already taken";
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public void addNewUser(AppUser appUser) {
        Optional<AppUser> userOptional = appUserRepository.findAppUserByEmail(appUser.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException(String.format(USER_NOT_FOUND_MSG, appUser.getEmail()));
        }

        appUserRepository.save(appUser);
    }

    public void deleteUser(Long userId) {
        boolean exists = appUserRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }

        appUserRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findAppUserByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            // TODO: if the user account is not confirmed - resend the activation email to the email

            throw new IllegalStateException(String.format(EMAIL_ALREADY_TAKEN_MSG, appUser.getEmail()));
        }

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
