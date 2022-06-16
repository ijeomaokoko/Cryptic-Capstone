package learn.crypticRadio.controllers;

import learn.crypticRadio.models.*;
import learn.crypticRadio.security.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/{appUserName}")
    public ResponseEntity<Role> findByUserName(@PathVariable String appUserName) {

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(appUserName);
        if(appUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // create our UserInfo object and set the fields
        Role userInfo = new Role();
        userInfo.setUsername(appUser.getUsername());
        userInfo.setMessages(appUser.getMessages());
        userInfo.setAppUserId(appUser.getAppUserId());

        // remove the password hash from the user before sending back
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

}
