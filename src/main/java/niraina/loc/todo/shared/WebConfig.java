package niraina.loc.todo.shared;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applique CORS à toutes les routes
                .allowedOrigins("http://localhost:4200")  // Remplacez par l'URL de votre frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Méthodes HTTP autorisées
                .allowedHeaders("*")  // Tous les headers sont autorisés
                .allowCredentials(true);  // Si vous avez besoin d'envoyer des cookies ou des headers d'authentification
    }
}
