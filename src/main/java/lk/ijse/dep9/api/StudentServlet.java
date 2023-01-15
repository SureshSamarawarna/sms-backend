package lk.ijse.dep9.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.ijse.dep9.entity.Student;
import lk.ijse.dep9.util.HibernateUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet(name = "StudentServlet", urlPatterns = "/students", loadOnStartup = 1)
public class StudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String contact = req.getParameter("contact");
        Part picture = req.getPart("picture");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Student student = new Student(name, address, contact);
            session.persist(student);
            if (picture != null){
                Path picturePath = Path.of(getServletContext().getRealPath("/"),
                        "pictures", student.getId() + "");
                picture.write(picturePath.toString());
                student.setPicture(picturePath.toString());
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);

            session.getTransaction().commit();
        }
    }
}
