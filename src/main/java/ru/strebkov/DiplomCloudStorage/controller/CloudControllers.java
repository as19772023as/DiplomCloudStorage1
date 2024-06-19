package ru.strebkov.DiplomCloudStorage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.strebkov.DiplomCloudStorage.model.dto.request.FileDataApply;
import ru.strebkov.DiplomCloudStorage.model.dto.request.LoginAuth;
import ru.strebkov.DiplomCloudStorage.model.dto.response.FileJsonName;
import ru.strebkov.DiplomCloudStorage.model.dto.response.GetToken;
import ru.strebkov.DiplomCloudStorage.model.entity.StorageFile;
import ru.strebkov.DiplomCloudStorage.model.entity.User;
import ru.strebkov.DiplomCloudStorage.service.AuthService;
import ru.strebkov.DiplomCloudStorage.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CloudControllers {
    private final AuthService authService;
    private final FileService fileService;

    @PostMapping(value = "/file")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file) {
        User user = authService.getUserFromToken(authToken);
        fileService.uploadFile(user, filename, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User user = authService.getUserFromToken(authToken);
        fileService.deleteFile(user, filename);
        return ResponseEntity.ok("File deleted successfully");
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User user = authService.getUserFromToken(authToken);
        byte[] file = fileService.downloadFile(user, filename);
        return ResponseEntity.ok(file);
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFilename(@RequestHeader("auth-token") String authToken,
                                            @RequestParam("filename") String filename,
                                            @RequestBody FileDataApply fileDataApply) {
        User user = authService.getUserFromToken(authToken);
        fileService.updateFilename(user, filename, fileDataApply);
        return ResponseEntity.ok("File name updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileJsonName>> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        User user = authService.getUserFromToken(authToken);
        List<StorageFile> storageFiles = fileService.getAllFiles(user, limit);
        List<FileJsonName> fileJsonNames = storageFiles.stream()
                .map(storageFile -> new FileJsonName(storageFile.getFilename(), storageFile.getSize()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileJsonNames);
    }
}