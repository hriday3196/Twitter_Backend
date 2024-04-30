package com.example.spring.controller;

import com.example.spring.model.User;
import com.example.spring.model.Comments;
import com.example.spring.model.Posts;
import com.example.spring.repo.UserRepository;
import com.example.spring.repo.CommentsRepository;
import com.example.spring.repo.PostsRepository;

//import java.time.LocalDate;

//import java.time.format.DateTimeFormatter;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.*;

@RestController
public class CommentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody Comments comment) {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        boolean userExists = false;
        for(User user1 : userList) {
            if(Objects.equals(comment.getUserID(), user1.getUserID())){
                userExists = true;
                break;
            }
        }
        if(!userExists){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User does not exist");
        }

        boolean postExists = false;
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        for(Posts post1 : postList) {
            if(Objects.equals(post1.getPostID(), comment.getPostID())){
                postExists = true;
                break;
            }
        }
        if(!postExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Post does not exist");
        }

        commentsRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Comment created successfully");
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> editComment(@RequestBody Comments comment) {
        List<Comments> commentList = new ArrayList<>();
        commentsRepository.findAll().forEach(commentList::add);
        for(Comments c1 : commentList) {
            if(Objects.equals(c1.getCommentID(), comment.getCommentID())){
                commentsRepository.deleteById((long)c1.getCommentID());
                commentsRepository.save(comment);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Comment edited successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Comment does not exist");
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam int commentID) {
        List<Comments> commentList = new ArrayList<>();
        commentsRepository.findAll().forEach(commentList::add);
        for(Comments c1 : commentList) {
            if(Objects.equals(c1.getCommentID(), commentID)){
                commentsRepository.deleteById((long)c1.getCommentID());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Comment deleted");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Comment does not exist");
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam int CommentID) {
        List<Comments> commentList = new ArrayList<>();
        commentsRepository.findAll().forEach(commentList::add);
        for(Comments c1 : commentList) {
            if(Objects.equals(c1.getCommentID(), CommentID)){
                Map<String, Object> object = new HashMap<>();
                object.put("commentID", c1.getCommentID());
                object.put("commentBody", c1.getCommentBody());

                // Fetching user information
                Optional<User> userOptional = userRepository.findById(c1.getUserID());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    Map<String, Object> user_obj = new HashMap<>();
                    user_obj.put("userID", user.getUserID());
                    user_obj.put("name", user.getName());
                    object.put("commentCreator", user_obj);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("User not found for the given comment");
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .body(object);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Comment does not exist");
    }
}
