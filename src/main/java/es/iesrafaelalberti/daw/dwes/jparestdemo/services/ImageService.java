package es.iesrafaelalberti.daw.dwes.jparestdemo.services;

import es.iesrafaelalberti.daw.dwes.jparestdemo.model.Student;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.StudentRepository;
import es.iesrafaelalberti.daw.dwes.jparestdemo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class ImageService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean imageStore(MultipartFile file, Class entity, Long id) throws IOException {
        try {
            Object myObject = entityManager.find(entity, id);

            String myFileName = entity.getSimpleName() + "_" + id.toString() + "_" + file.getOriginalFilename();

            Path targetPath = Paths.get("./images/" +
                    myFileName)
                    .normalize();
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Method method = entity.getMethod("setImageUrl", String.class);
            method.invoke(myObject,"/download/" + myFileName);
            entityManager.persist(entity.cast(myObject));
        } catch (Exception e) {
            throw new EntityNotFoundException(entity.getSimpleName() + ": " + id.toString());
        }

        return true;
    }
/*
    public Optional<Resource> getImage(String name) {
        Path targetPath = Paths.get("./images/" +
                                    name).normalize();

        try {
            Resource resource = new UrlResource(targetPath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(targetPath);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }*/
}
