package ldap.misadif.ldap.services;

import ldap.misadif.ldap.models.ActiveDirectoryUser;

public interface IUserService {
    ActiveDirectoryUser findByLogin(String login);
    boolean login(ActiveDirectoryUser activeDirectoryUser, String password);
}
