package com.weatherable.weatherable.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "follow")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FollowEntity {
    @Id
    private String id;
    private String userid;
    private ArrayList<String> followingId;
}
