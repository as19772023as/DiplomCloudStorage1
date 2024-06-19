package ru.strebkov.DiplomCloudStorage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.strebkov.DiplomCloudStorage.exception.InputDataException;
import ru.strebkov.DiplomCloudStorage.model.dto.request.FileDataApply;
import ru.strebkov.DiplomCloudStorage.model.entity.StorageFile;
import ru.strebkov.DiplomCloudStorage.model.entity.User;
import ru.strebkov.DiplomCloudStorage.repository.AuthRepository;
import ru.strebkov.DiplomCloudStorage.repository.FileRepository;
import ru.strebkov.DiplomCloudStorage.repository.LoginRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {
    private final FileRepository fileRepository;
    private final AuthRepository authRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public void uploadFile(User user, String fileName, MultipartFile file) {
        try {
            fileRepository.save(new StorageFile(fileName, LocalDateTime.now(), file.getSize(), file.getBytes(), user));
            log.info("Successfully uploaded file. User: {}", user.getUsername());
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new InputDataException("Upload file: Input data exception");
        }
    }

    @Transactional
    public void deleteFile(User user, String filename) {
        fileRepository.deleteByUserAndFilename(user, filename);
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile != null) {
            log.error("Delete file: Input data exception");
            throw new InputDataException("Input data exception");
        }
        log.info("Successfully deleted file. User: {}", user.getUsername());
    }

    @Transactional
    public byte[] downloadFile(User user, String filename) {
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile == null) {
            log.error("Download file: Input data exception");
            throw new InputDataException("Download file: Input data exception");
        }
        log.info("Downloaded file. User: {}", user.getUsername());
        return storageFile.getFileContent();
    }

    @Transactional
    public void updateFilename(User user, String fileName, FileDataApply fileDataApply) {
        fileRepository.updateFilenameByUser(user, fileName, fileDataApply.getFilename());
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, fileName);
        if (storageFile != null) {
            log.error("ERROR: Input data exception");
            throw new InputDataException("ERROR: Input data exception");
        }
        log.info("Successfully updated file name. User: {}", user.getUsername());
    }

    @Transactional
    public List<StorageFile> getAllFiles(User user, Integer limit) {
        log.info("Successfully fetched all files. User: {}", user.getUsername());
        return fileRepository.findAllByUser(user);
    }
}
