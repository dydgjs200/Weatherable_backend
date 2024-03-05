package com.weatherable.weatherable.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String userid;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "Text", nullable = false)
    private String content;
    private String image_path;


    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "boardentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();
}
