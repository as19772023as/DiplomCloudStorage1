package ru.strebkov.DiplomCloudStorage.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class FileDataApply {

    private String filename;

    @JsonCreator
    public FileDataApply(String filename) {
        this.filename = filename;
    }
}
