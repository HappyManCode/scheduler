package me.happyman.scheduler.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public List<AppUser> getUsers() {
        return appUserService.getUsers();
    }

    @PostMapping
    public void registerNewUser(@RequestBody AppUser appUser) {
        appUserService.addNewUser(appUser);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        appUserService.deleteUser(userId);
    }
}
