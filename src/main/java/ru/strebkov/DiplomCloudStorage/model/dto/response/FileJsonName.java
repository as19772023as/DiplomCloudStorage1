package ru.strebkov.DiplomCloudStorage.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileJsonName {
    private String filename;
    private Long size;
}
