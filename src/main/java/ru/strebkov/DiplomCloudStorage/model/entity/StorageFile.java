package ru.strebkov.DiplomCloudStorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "diploma1", name = "files")
public class StorageFile {

    @Id
    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public StorageFile(User user, String test) {
        this.user = user;
    }

}
