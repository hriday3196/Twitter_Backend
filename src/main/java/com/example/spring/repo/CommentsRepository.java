package com.example.spring.repo;
import com.example.spring.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>  {
    List<Comments> findByPostID(int postID);
}
