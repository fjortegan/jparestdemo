package es.iesrafaelalberti.daw.dwes.jparestdemo.controllers;

import es.iesrafaelalberti.daw.dwes.jparestdemo.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> imageTest(@RequestParam("id") String id,
                                            @RequestParam("entity") String entity,
                                            @RequestParam("file") MultipartFile file) {

        try {
            String packageName = this.getClass().getPackageName();
            Class<?> entityClass = Class.forName(packageName.substring(0, packageName.lastIndexOf("."))
                                   + ".model." + entity);
            imageService.imageStore(file, entityClass, Long.parseLong(id));
        } catch (IOException e) {
            return new ResponseEntity<>("Error en archivo", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new EntityNotFoundException(entity);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/download/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable("name") String name) {
        Path targetPath = Paths.get("./images/" +
                name)
                .normalize();
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
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
