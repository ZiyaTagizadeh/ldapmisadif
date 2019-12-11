package ldap.misadif.ldap.configurations;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.naming.Context;
import java.util.Hashtable;

@Configuration
@PropertySource("classpath:application.properties")
public class LdapConfiguration {

    @Value("${spring.ldap.urls}")
    private String ldapURI;

    @Value("${spring.ldap.username}")
    private String ldapUser;

    @Value("${spring.ldap.password}")
    private String ldapUserPassword;


    @Bean("ldapConfig")
    Hashtable<String,String> hashtable(){
        Hashtable env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURI);
        env.put( Context.SECURITY_PRINCIPAL, ldapUser);
        env.put( Context.SECURITY_CREDENTIALS, ldapUserPassword);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        return env;
    }

    @Bean("ldapURI")
    String getLdapURI(){
        return ldapURI;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
