package lk.ijse.dep9.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String realPath = sce.getServletContext().getRealPath("/");
        try {
            Path picturePath = Path.of(realPath, "pictures");
            if (!Files.exists(picturePath)) Files.createDirectory(picturePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
