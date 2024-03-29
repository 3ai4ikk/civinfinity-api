package ua.zai4ik.restFirst;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST")
                .allowCredentials(true); // Важно установить true, если запросы отправляются с учетом учетных данных
    }
}
