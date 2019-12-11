package ldap.misadif.ldap.configurations;


import ldap.misadif.ldap.models.ActiveDirectoryUser;
import ldap.misadif.ldap.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    @Qualifier("userActiveDirectoryService")
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username =authentication.getName();
       String password = authentication.getCredentials().toString();
       if (Objects.isNull(username) || Objects.isNull(password))
           return null;

       //bu check eleyir ki user movcuddur mu
        // movcuddusa user i getirir
      ActiveDirectoryUser activeDirectoryUser = userService.findByLogin(username);

      if (Objects.isNull(activeDirectoryUser))
          return null;

      // bu da login eleyir hemin movcud usr e gore
      boolean isAuthenticated = userService.login(activeDirectoryUser,password);

      if (isAuthenticated){
          return new UsernamePasswordAuthenticationToken(username,password,null);
      }

        return null;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
