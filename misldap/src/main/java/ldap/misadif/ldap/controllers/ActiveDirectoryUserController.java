package ldap.misadif.ldap.controllers;

import ldap.misadif.ldap.models.ActiveDirectoryUser;
import ldap.misadif.ldap.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "http://172.23.9.15:4200")
@RestController
@RequestMapping("/ldap")
public class ActiveDirectoryUserController {
    @Autowired
    @Qualifier("userActiveDirectoryService")
    private IUserService userService;

    @GetMapping("/{login}")
    public ResponseEntity findByLogin(@PathVariable String login ){
        return ResponseEntity.ok(userService.findByLogin(login));
    }

    @GetMapping("/login_control/{login}/{passw}")
    public String testLogin(@PathVariable String login, @PathVariable String passw){
        //email i bura yaz
        System.out.println("---------------");
        System.out.println("login=="+login);
        System.out.println("Password="+passw);
        boolean isAuthenticated=false;
       ActiveDirectoryUser activeDirectoryUser = userService.findByLogin(login);
        System.out.println("-------------------");
        System.out.println("activeDirectoryUser"+activeDirectoryUser.getLdapLogin());
       if (Objects.nonNull(activeDirectoryUser)){
           //passwordu bura yaz ldap username pass deyirsen? beli
           ///sahir.azimli nin password un ok
          isAuthenticated =  userService.login(activeDirectoryUser,passw);
       //  return isAuthenticated;
       }
       String a="isgogged";
       a="{\""+"pswcontr" + "\":" +isAuthenticated+"}"; // json  generate  edirem  burda  ok
        return   a;
    }



}
