package ldap.misadif.ldap.controllers;

import ldap.misadif.ldap.models.ActiveDirectoryUser;
import ldap.misadif.ldap.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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

    @GetMapping("/test-login")
    public boolean   testLogin(){
        //email i bura yaz
       ActiveDirectoryUser activeDirectoryUser = userService.findByLogin("shakir.azimli");
       if (Objects.nonNull(activeDirectoryUser)){
           //passwordu bura yaz ldap username pass deyirsen? beli
           ///sahir.azimli nin password un ok
         boolean isAuthenticated =  userService.login(activeDirectoryUser,"Sh0504102050");
         return isAuthenticated;
       }
        return false;
    }

}
