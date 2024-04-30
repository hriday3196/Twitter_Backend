package com.example.spring.controller;

import com.example.spring.model.User;
import com.example.spring.model.Comments;
import com.example.spring.model.Posts;
import com.example.spring.repo.UserRepository;
import com.example.spring.repo.CommentsRepository;
import com.example.spring.repo.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class PostController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestBody Posts post) {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        for(User userObj : userList) {
            if(Objects.equals(userObj.getUserID(), post.getUserID())){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate now = LocalDate.now();
                post.setDate(now);
                postsRepository.save(post);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Post created successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User does not exist");
    }

//    @PatchMapping("/post")
//    public Object editPost(@RequestBody Posts post) {
//        List<Posts> postList = new ArrayList<>();
//        postsRepository.findAll().forEach(postList::add);
//        for(Posts post1 : postList) {
//            if(Objects.equals(post1.getPostID(), post.getPostID())){
//                postsRepository.deleteById((long)post1.getPostID());
//                postsRepository.save(post);
//                return ("Post edited successfully");
//            }
//        }
//        Map<String, Object> object = new HashMap<>();
//        object.put("Error", "Post does not exist");
//        return object;
//    }
//
//    @DeleteMapping("/post")
//    public Object deletePost(@RequestParam int postID) {
//        List<Posts> postList = new ArrayList<>();
//        postsRepository.findAll().forEach(postList::add);
//        for(Posts post1 : postList) {
//            if(Objects.equals(post1.getPostID(), postID)){
//                postsRepository.deleteById((long)post1.getPostID());
//                return ("Post deleted");
//            }
//        }
//        Map<String, Object> object = new HashMap<>();
//        object.put("Error", "Post does not exist");
//        return object;
//    }


    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@RequestBody Posts post) {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        for(Posts post1 : postList) {
            if(Objects.equals(post1.getPostID(), post.getPostID())){
                postsRepository.deleteById((long)post1.getPostID());
                postsRepository.save(post);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Post edited successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Post does not exist");
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam int postID) {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        for(Posts post1 : postList) {
            if(Objects.equals(post1.getPostID(), postID)){
                postsRepository.deleteById((long)post1.getPostID());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Post deleted");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Post does not exist");
    }

//    @GetMapping("/post")
//    public Object getPost(@RequestParam int postID) {
//        List<Posts> postList = new ArrayList<>();
//        postsRepository.findAll().forEach(postList::add);
//        for(Posts p1 : postList) {
//            if(Objects.equals(p1.getPostID(), postID)){
//                Map<String, Object> object = new HashMap<>();
//                object.put("postID", postID);
//                object.put("postBody", p1.getPostBody());
//                object.put("date", p1.getDate());
//                List<Comments> commentList = new ArrayList<>();
//                commentsRepository.findAll().forEach(commentList::add);
//                List<Object> commentsObj = new ArrayList<>();
//                for(Comments c1 : commentList) {
//                    if(Objects.equals(c1.getPostID(), postID)){
//                        Map<String, Object> commentObj = new HashMap<>();
//                        commentObj.put("commentID", c1.getCommentID());
//                        commentObj.put("commentBody", c1.getCommentBody());
//                        Map<String, Object> smallObj = new HashMap<>();
//                        Optional<User> bookObj = userRepository.findById(c1.getUserID());
//                        if (bookObj.isPresent()) {
//                            User user = bookObj.get();
//                            smallObj.put("name", user.getName());
//                        }
//                        smallObj.put("userID", c1.getUserID());
//                        commentObj.put("commentCreator" ,smallObj);
//                        commentsObj.add(commentObj);
//                    }
//                }
//                object.put("comments", commentsObj);
//                return object;
//            }
//        }
//        Map<String, Object> object = new HashMap<>();
//        object.put("Error", "Post does not exist");
//        return object;
//    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam int postID) {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        for(Posts p1 : postList) {
            if(Objects.equals(p1.getPostID(), postID)){
                Map<String, Object> object = new HashMap<>();
                object.put("postID", postID);
                object.put("postBody", p1.getPostBody());
                object.put("date", p1.getDate());

                // Fetching comments related to the post
                List<Comments> commentList = commentsRepository.findByPostID(postID);
                List<Object> commentsObj = new ArrayList<>();
                for(Comments c1 : commentList) {
                    Map<String, Object> commentObj = new HashMap<>();
                    commentObj.put("commentID", c1.getCommentID());
                    commentObj.put("commentBody", c1.getCommentBody());

                    // Fetching user information for the comment creator
                    Optional<User> userOptional = userRepository.findById(c1.getUserID());
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        Map<String, Object> smallObj = new HashMap<>();
                        smallObj.put("name", user.getName());
                        smallObj.put("userID", c1.getUserID());
                        commentObj.put("commentCreator", smallObj);
                    }

                    commentsObj.add(commentObj);
                }
                object.put("comments", commentsObj);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(object);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Post does not exist");
    }

//    @GetMapping("/getAllPosts")
//    public ResponseEntity<List<Posts>> getAllPosts() {
//        try {
//            List<Posts> postList = new ArrayList<>();
//            postsRepository.findAll().forEach(postList::add);
//
//            if (postList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(postList, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
