package br.com.hugows.restwithspringboot.services;

import br.com.hugows.restwithspringboot.config.FileStorageConfig;
import br.com.hugows.restwithspringboot.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw ServiceException.builder()
                    .message("Could not create the directory where the uploaded files will be stored")
                    .cause(e)
                    .build();
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw ServiceException.builder()
                        .message("Sorry! Filename contais invalid path sequence " + fileName)
                        .build();
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw ServiceException.builder()
                    .message("Could not store file " + fileName + ". Please try again!")
                    .cause(e)
                    .build();
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }

            throw ServiceException.builder()
                    .message("File not found " + fileName)
                    .build();
        } catch (Exception e) {
            throw ServiceException.builder()
                    .message("File not found " + fileName)
                    .cause(e)
                    .build();
        }
    }

}
