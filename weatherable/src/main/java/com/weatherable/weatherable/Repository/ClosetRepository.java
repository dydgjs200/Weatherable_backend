package com.weatherable.weatherable.Repository;

import com.weatherable.weatherable.Entity.ClosetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClosetRepository extends JpaRepository<ClosetEntity, Long> {

    @Query(value = "select * from closet where user_id = :userIndex and active = true ORDER BY created_at DESC", nativeQuery = true)
    Optional<List<ClosetEntity>> retrieveAllClothByUserIndex(Long userIndex);

    @Modifying
    @Query(value = "update closet set active = false where id = :id", nativeQuery = true)
    void deleteCloset(Long id);

    @Modifying
    @Query(value = "update closet set like = true where id = :id", nativeQuery = true)
    void likeCloset(long id);

    @Modifying
    @Query(value = "update closet set like = false where id = :id", nativeQuery = true)
    void unlikeCloset(long id);


    Optional<ClosetEntity> getByIdAndActive(Long id, boolean active);
}
