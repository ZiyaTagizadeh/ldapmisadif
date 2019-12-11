package ldap.misadif.ldap.configurations;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class LoginPageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("file:D:/is/Java/Spring/is/misldap/src/main/resources/static/css/");

        registry
                .addResourceHandler("/templates/admins/**")
                .addResourceLocations("file:D:/is/Java/Spring/is/misldap/src/main/resources/templates/admins/");

        registry
                .addResourceHandler("/templates/admins/layout/**")
                .addResourceLocations("file:D:/is/Java/Spring/is/misldap/src/main/resources/templates/admins/layout/");
    }


}
