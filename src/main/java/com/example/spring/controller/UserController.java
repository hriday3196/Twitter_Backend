package com.example.spring.controller;

import com.example.spring.model.User;
import com.example.spring.model.Comments;
import com.example.spring.model.Posts;
import com.example.spring.repo.UserRepository;
import com.example.spring.repo.CommentsRepository;
import com.example.spring.repo.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.time.LocalDate;

//import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CommentsRepository commentsRepository;

//    @PostMapping("/signup")
//    public Object signup(@RequestBody User user) {
//        try {
//            //
//            List<User> userList = new ArrayList<>();
//            userRepository.findAll().forEach(userList::add);
//            for(User userObj : userList) {
//                if(Objects.equals(userObj.getEmail(), user.getEmail())){
//                    Map<String, Object> object = new HashMap<>();
//                    object.put("Error", "Forbidden, Account already exists");
//                    return object;
//                }
//
//            }
//            userRepository.save(user);
//            return("Account Creation Successful");
//        } catch (Exception e) {
//            return ("server error");
//        }
//    }
//
//
//
//    @PostMapping("/login")
//    @ResponseBody
//    public Object login(@RequestBody User user) {
//        try {
//            List<User> userList = new ArrayList<>();
//            userRepository.findAll().forEach(userList::add);
//            for(User userObj : userList) {
//                if(Objects.equals(userObj.getEmail(), user.getEmail())){
//                    if(Objects.equals(userObj.getPassword(), user.getPassword())){
//                        return ("Login successful");
//                    }else {
//                        Map<String, Object> object = new HashMap<>();
//                        object.put("Error", "Username/Password incorrect");
//                        return object;
//                    }
//                }
//
//            }
//            Map<String, Object> object = new HashMap<>();
//            object.put("Error", "User does not exist");
//            return object;
//        } catch (Exception e) {
//            return ("Server Error");
//        }
//    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(userList::add);
            for(User userObj : userList) {
                if(Objects.equals(userObj.getEmail(), user.getEmail())){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Forbidden, Account already exists");
                }
            }
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Account Creation Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(userList::add);
            for(User userObj : userList) {
                if(Objects.equals(userObj.getEmail(), user.getEmail())){
                    if(Objects.equals(userObj.getPassword(), user.getPassword())){
                        return ResponseEntity.status(HttpStatus.OK)
                                .body("Login successful");
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Username/Password incorrect");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server Error");
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public Object user(@RequestParam int userID) {
        //int id = book.getId();
        Optional<User> bookObj = userRepository.findById(userID);
        if (bookObj.isPresent()) {
            User user = bookObj.get();
            Map<String, Object> object = new HashMap<>();
            object.put("userID", user.getUserID());
            object.put("name", user.getName());
            object.put("email", user.getEmail());
            return object;

        } else {
            Map<String, Object> object = new HashMap<>();
            object.put("Error", "User does not exist");
            return object;
        }
    }
//
//
//    @PostMapping("/updateUser/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
//        try {
//            Optional<User> userData = userRepository.findById(id);
//            if (userData.isPresent()) {
//                User updatedUserData = userData.get();
//                updatedUserData.setEmail(user.getEmail());
//                updatedUserData.setPassword(user.getPassword());
//
//                User userObj = userRepository.save(updatedUserData);
//                return new ResponseEntity<>(userObj, HttpStatus.CREATED);
//            }
//
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/user")
//    public ResponseEntity<?> user(@RequestParam int userID) {
//        Optional<User> userOptional = userRepository.findById(userID);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("User does not exist");
//        }
//    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updateUser) {
        try {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                User existingUser = userData.get();
                existingUser.setEmail(updateUser.getEmail());
                existingUser.setPassword(updateUser.getPassword());

                User savedUser = userRepository.save(existingUser);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(savedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server Error");
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sdkjfsnd")
    public Object k() {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        List<Object> ans = new ArrayList<>();
        for(Posts p1 : postList){
            Map<String, Object> object = new HashMap<>();
            object.put("postID", p1.getPostID());
            object.put("postBody", p1.getPostBody());
            object.put("date", p1.getDate());
            List<Comments> commentList = new ArrayList<>();
            commentsRepository.findAll().forEach(commentList::add);
            List<Object> commentsObj = new ArrayList<>();
            for(Comments c1 : commentList) {
                if(Objects.equals(c1.getPostID(), p1.getPostID())){
                    Map<String, Object> commentObj = new HashMap<>();
                    commentObj.put("commentID", c1.getCommentID());
                    commentObj.put("commentBody", c1.getCommentBody());
                    Map<String, Object> smallObj = new HashMap<>();
                    Optional<User> bookObj = userRepository.findById(c1.getUserID());
                    if (bookObj.isPresent()) {
                        User user = bookObj.get();
                        smallObj.put("name", user.getName());
                    }
                    smallObj.put("userID", c1.getUserID());
                    commentObj.put("commentCreator" ,smallObj);
                    commentsObj.add(commentObj);
                }
            }
            object.put("comments", commentsObj);
            ans.add(object);
        }
        Collections.reverse(ans);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

    @GetMapping("/kdjsfads")
    public Object test() {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        List<Object> ans = new ArrayList<>();
        for(Posts p1 : postList){
            Map<String, Object> object = new HashMap<>();
            object.put("postID", p1.getPostID());
            object.put("postBody", p1.getPostBody());
            object.put("date", p1.getDate());
            List<Comments> commentList = new ArrayList<>();
            commentsRepository.findAll().forEach(commentList::add);
            List<Object> commentsObj = new ArrayList<>();
            for(Comments c1 : commentList) {
                if(Objects.equals(c1.getPostID(), p1.getPostID())){
                    Map<String, Object> commentObj = new HashMap<>();
                    commentObj.put("commentID", c1.getCommentID());
                    commentObj.put("commentBody", c1.getCommentBody());
                    Map<String, Object> smallObj = new HashMap<>();
                    Optional<User> bookObj = userRepository.findById(c1.getUserID());
                    if (bookObj.isPresent()) {
                        User user = bookObj.get();
                        smallObj.put("name", user.getName());
                    }
                    smallObj.put("userID", c1.getUserID());
                    commentObj.put("commentCreator" ,smallObj);
                    commentsObj.add(commentObj);
                }
            }
            object.put("comments", commentsObj);
            ans.add(object);
        }
        Collections.reverse(ans);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }


    @GetMapping("/kdsjvadf")
    public Object sdkasdf() {
        List<Posts> postList = new ArrayList<>();
        postsRepository.findAll().forEach(postList::add);
        List<Object> ans = new ArrayList<>();
        for(Posts p1 : postList){
            Map<String, Object> object = new HashMap<>();
            object.put("postID", p1.getPostID());
            object.put("postBody", p1.getPostBody());
            object.put("date", p1.getDate());
            List<Comments> commentList = new ArrayList<>();
            commentsRepository.findAll().forEach(commentList::add);
            List<Object> commentsObj = new ArrayList<>();
            for(Comments c1 : commentList) {
                if(Objects.equals(c1.getPostID(), p1.getPostID())){
                    Map<String, Object> commentObj = new HashMap<>();
                    commentObj.put("commentID", c1.getCommentID());
                    commentObj.put("commentBody", c1.getCommentBody());
                    Map<String, Object> smallObj = new HashMap<>();
                    Optional<User> bookObj = userRepository.findById(c1.getUserID());
                    if (bookObj.isPresent()) {
                        User user = bookObj.get();
                        smallObj.put("name", user.getName());
                    }
                    smallObj.put("userID", c1.getUserID());
                    commentObj.put("commentCreator" ,smallObj);
                    commentsObj.add(commentObj);
                }
            }
            object.put("comments", commentsObj);
            ans.add(object);
        }
        Collections.reverse(ans);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

//    @GetMapping("/")
//    public Object feed() {
//        List<Posts> postList = new ArrayList<>();
//        postsRepository.findAll().forEach(postList::add);
//        List<Object> ans = new ArrayList<>();
//        for(Posts p1 : postList){
//            Map<String, Object> object = new HashMap<>();
//            object.put("postID", p1.getPostID());
//            object.put("postBody", p1.getPostBody());
//            object.put("date", p1.getDate());
//            List<Comments> commentList = new ArrayList<>();
//            commentsRepository.findAll().forEach(commentList::add);
//            List<Object> commentsObj = new ArrayList<>();
//            for(Comments c1 : commentList) {
//                if(Objects.equals(c1.getPostID(), p1.getPostID())){
//                    Map<String, Object> commentObj = new HashMap<>();
//                    commentObj.put("commentID", c1.getCommentID());
//                    commentObj.put("commentBody", c1.getCommentBody());
//                    Map<String, Object> smallObj = new HashMap<>();
//                    Optional<User> bookObj = userRepository.findById(c1.getUserID());
//                    if (bookObj.isPresent()) {
//                        User user = bookObj.get();
//                        smallObj.put("name", user.getName());
//                    }
//                    smallObj.put("userID", c1.getUserID());
//                    commentObj.put("commentCreator" ,smallObj);
//                    commentsObj.add(commentObj);
//                }
//            }
//            object.put("comments", commentsObj);
//            ans.add(object);
//        }
//        Collections.reverse(ans);
//        return new ResponseEntity<>(ans, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<Object>> getAllUsers() {
//        try {
//            List<User> userList = new ArrayList<>();
//            userRepository.findAll().forEach(userList::add);
//
//            if (userList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            List<Object> ans = new ArrayList<>();
//            for(User user : userList) {
//                Map<String, Object> object = new HashMap<>();
//                object.put("userID", user.getUserID());
//                object.put("name", user.getName());
//                object.put("email", user.getEmail());
//                ans.add(object);
//            }
//            return new ResponseEntity<>(ans, HttpStatus.OK);
//        } catch(Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/")
    public ResponseEntity<List<Object>> feed() {
        try {
            List<Posts> postList = new ArrayList<>();
            postsRepository.findAll().forEach(postList::add);

            if (postList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Object> ans = new ArrayList<>();
            for(Posts p1 : postList){
                Map<String, Object> object = new HashMap<>();
                object.put("postID", p1.getPostID());
                object.put("postBody", p1.getPostBody());
                object.put("date", p1.getDate());

                List<Comments> commentList = new ArrayList<>();
                commentsRepository.findAll().forEach(commentList::add);
                List<Object> commentsObj = new ArrayList<>();
                for(Comments c1 : commentList) {
                    if(Objects.equals(c1.getPostID(), p1.getPostID())){
                        Map<String, Object> commentObj = new HashMap<>();
                        commentObj.put("commentID", c1.getCommentID());
                        commentObj.put("commentBody", c1.getCommentBody());

                        Map<String, Object> smallObj = new HashMap<>();
                        Optional<User> userObj = userRepository.findById(c1.getUserID());
                        if (userObj.isPresent()) {
                            User user = userObj.get();
                            smallObj.put("name", user.getName());
                        }
                        smallObj.put("userID", c1.getUserID());
                        commentObj.put("commentCreator", smallObj);
                        commentsObj.add(commentObj);
                    }
                }
                object.put("comments", commentsObj);
                ans.add(object);
            }

            Collections.reverse(ans);
            return new ResponseEntity<>(ans, HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Object>> getAllUsers() {
        try {
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(userList::add);

            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<Object> ans = new ArrayList<>();
            for(User user : userList) {
                Map<String, Object> object = new HashMap<>();
                object.put("userID", user.getUserID());
                object.put("name", user.getName());
                object.put("email", user.getEmail());
                ans.add(object);
            }

            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
