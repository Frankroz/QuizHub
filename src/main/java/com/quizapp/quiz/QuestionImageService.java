package com.quizapp.quiz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class QuestionImageService {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    private static final Set<String> ALLOWED_EXTENSIONS
            = Set.of("jpg", "jpeg", "png", "webp");

    private final Path uploadDirectory;

    public QuestionImageService(
            @Value("${quiz.upload-dir:uploads/question-images}") String uploadDirectory) {

        this.uploadDirectory = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.uploadDirectory);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not create question image directory.",
                    e
            );
        }
    }

    public String save(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Question images must be 5 MB or smaller."
            );
        }

        String originalName = StringUtils.cleanPath(
                file.getOriginalFilename() == null
                ? ""
                : file.getOriginalFilename()
        );

        String extension = getExtension(originalName);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG, and WebP images are supported."
            );
        }

        try {
            byte[] content = file.getBytes();

            if (!isValidImageContent(content, extension)) {
                throw new IllegalArgumentException(
                        "The uploaded file is not a valid supported image."
                );
            }

            String filename = UUID.randomUUID()
                    + "."
                    + extension;

            Path destination = uploadDirectory.resolve(filename)
                    .normalize();

            if (!destination.startsWith(uploadDirectory)) {
                throw new IllegalArgumentException(
                        "Invalid image filename."
                );
            }

            Files.write(destination, content);

            return "/question-images/" + filename;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not save question image.",
                    e
            );
        }
    }

    public void delete(String imagePath) {

        if (imagePath == null || imagePath.isBlank()) {
            return;
        }

        String prefix = "/question-images/";

        if (!imagePath.startsWith(prefix)) {
            return;
        }

        String filename = imagePath.substring(prefix.length());

        // Only allow a single filename, never a path.
        if (filename.isBlank()
                || filename.contains("/")
                || filename.contains("\\")
                || filename.equals(".")
                || filename.equals("..")) {
            return;
        }

        try {
            Path target = uploadDirectory.resolve(filename)
                    .normalize();

            if (target.startsWith(uploadDirectory)) {
                Files.deleteIfExists(target);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not delete question image.",
                    e
            );
        }
    }

    private String getExtension(String filename) {

        int dot = filename.lastIndexOf('.');

        if (dot < 0 || dot == filename.length() - 1) {
            return "";
        }

        return filename.substring(dot + 1)
                .toLowerCase(Locale.ROOT);
    }

    private boolean isValidImageContent(
            byte[] bytes,
            String extension) {

        if (bytes.length < 12) {
            return false;
        }

        boolean jpeg
                = (bytes[0] & 0xFF) == 0xFF
                && (bytes[1] & 0xFF) == 0xD8
                && (bytes[2] & 0xFF) == 0xFF;

        boolean png
                = (bytes[0] & 0xFF) == 0x89
                && bytes[1] == 0x50
                && bytes[2] == 0x4E
                && bytes[3] == 0x47
                && bytes[4] == 0x0D
                && bytes[5] == 0x0A
                && bytes[6] == 0x1A
                && bytes[7] == 0x0A;

        boolean webp
                = bytes[0] == 'R'
                && bytes[1] == 'I'
                && bytes[2] == 'F'
                && bytes[3] == 'F'
                && bytes[8] == 'W'
                && bytes[9] == 'E'
                && bytes[10] == 'B'
                && bytes[11] == 'P';

        return switch (extension) {
            case "jpg", "jpeg" ->
                jpeg;
            case "png" ->
                png;
            case "webp" ->
                webp;
            default ->
                false;
        };
    }
}
