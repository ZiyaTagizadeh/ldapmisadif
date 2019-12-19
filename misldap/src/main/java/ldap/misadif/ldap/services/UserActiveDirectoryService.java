package ldap.misadif.ldap.services;

import ldap.misadif.ldap.models.ActiveDirectoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

@Service
public class UserActiveDirectoryService implements IUserService {
    @Autowired
    @Qualifier("ldapURI")
    private String ldapUrl;

    @Autowired
    @Qualifier("ldapConfig")
    private Hashtable<String,String> ldapEnv;

    @Override
    public ActiveDirectoryUser findByLogin(String login) {
        SearchControls searchCtls = new SearchControls();
        String returnedAtts[] = {"sAMAccountName", "userPrincipalName", "employeeId", "displayName", "mail","userAccountControl","memberOf",};



        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchFilter = "(&(objectClass=person)(sAMAccountName=" + login + "))";
        String searchBase = "OU=ADIF_LPOB,DC=adif,DC=local";

        try {
            DirContext ldapContext = new InitialDirContext(ldapEnv);
            NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);

            if (!answer.hasMore()){
                return null;
            }
            SearchResult sr = answer.next();
            String fullDn = sr.getNameInNamespace();
            Attributes attrs = sr.getAttributes();


            if (attrs.get("sAMAccountName") != null && attrs.get("userPrincipalName") != null){

                ActiveDirectoryUser activeDirectoryUser = new ActiveDirectoryUser();
                activeDirectoryUser.setLogin((String) attrs.get("sAMAccountName").get());
                activeDirectoryUser.setLdapLogin((String) attrs.get("userPrincipalName").get());

                if (attrs.get("displayName") != null)
                    activeDirectoryUser.setFullName((String) attrs.get("displayName").get());
                else
                    activeDirectoryUser.setFullName(null);

                if (attrs.get("mail") != null)
                    activeDirectoryUser.setMail((String) attrs.get("mail").get());
                else
                    activeDirectoryUser.setMail((String) attrs.get("userPrincipalName").get());



                //userAccountControl attribute in ldap indicates if user is blocked(enabled or not) or not
                //if i get it right if the attribute equals 66048 it means that it's enabled or not blocked whatsoever
                if (attrs.get("userAccountControl") != null)
                    activeDirectoryUser.setIsEnabled((attrs.get("userAccountControl").get()).equals("66048"));


                String[] entryPathParts = fullDn.split(",");
                if(entryPathParts.length >= 3) {
                    String department = entryPathParts[2].replace("OU=", "");
                    activeDirectoryUser.setDepartment(department);
                }
                else
                    activeDirectoryUser.setDepartment(null);

                answer.close();
                ldapContext.close();
                return activeDirectoryUser;
            } else {
                answer.close();
                ldapContext.close();
                return null;
            }
        }
        catch (NamingException e) {
            return null;
        }


    }

    @Override
    public boolean login(ActiveDirectoryUser activeDirectoryUser, String password) {
        Hashtable<String, String> userLoginEnv = new Hashtable<>(11);
        userLoginEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        userLoginEnv.put(Context.PROVIDER_URL, ldapUrl);
        userLoginEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        userLoginEnv.put(Context.SECURITY_PRINCIPAL, activeDirectoryUser.getLdapLogin());
        userLoginEnv.put(Context.SECURITY_CREDENTIALS, password);
        try {
            DirContext ldapContext = new InitialDirContext(userLoginEnv);
            ldapContext.close();
            return true;
        }
        catch (NamingException e){
            return false;
        }

    }
}

