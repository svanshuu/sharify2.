package com.shayarify.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayarify.dto.CommentDTO;
import com.shayarify.dto.PostDTO;
import com.shayarify.dto.UserDTO;
import com.shayarify.exceptions.UserException;
import com.shayarify.model.Hashtag;
import com.shayarify.model.Post;
import com.shayarify.model.User;
import com.shayarify.repository.HashtagRepository;
import com.shayarify.repository.PostRepository;
import com.shayarify.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);

        // Handle hashtags (find or create)
        Set<Hashtag> hashtagEntities = new HashSet<>();
        if (post.getHashtags() != null) {
            for (Hashtag h : post.getHashtags()) {
                String tag = h.getTag().trim().toLowerCase();
                Hashtag existing = hashtagRepository.findByTag(tag).orElse(null);
                if (existing != null) {
                    hashtagEntities.add(existing);
                } else {
                    Hashtag newHashtag = new Hashtag();
                    newHashtag.setTag(tag);
                    hashtagEntities.add(hashtagRepository.save(newHashtag));
                }
            }
        }
        newPost.setHashtags(hashtagEntities);

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getUser().getId() != user.getId()) {
            throw new Exception("you can't delete another user post");
        }

        postRepository.delete(post);
        return "post deleted successfully";
    }

    @Override
    public List<PostDTO> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findPostByUserId(userId);
        User currentUser = userService.findUserById(userId);

        return posts.stream()
            .map(post -> {
                UserDTO userDTO = new UserDTO(
                    post.getUser().getId(),
                    post.getUser().getFirstName(),
                    post.getUser().getLastName(),
                    post.getUser().getEmail(),
                    post.getUser().isTermsAccepted(),
                    post.getUser().getFollowers(),
                    post.getUser().getFollowings(),
                    post.getUser().getGender(),
                    post.getUser().getAvatar()
                );

                List<UserDTO> likedDTOs = post.getLiked().stream()
                    .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.isTermsAccepted(),
                        user.getFollowers(),
                        user.getFollowings(),
                        user.getGender(),
                        user.getAvatar()
                    ))
                    .collect(Collectors.toList());

                List<CommentDTO> commentDTOs = post.getComments().stream()
                    .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        new UserDTO(
                            comment.getUser().getId(),
                            comment.getUser().getFirstName(),
                            comment.getUser().getLastName(),
                            comment.getUser().getEmail(),
                            comment.getUser().isTermsAccepted(),
                            comment.getUser().getFollowers(),
                            comment.getUser().getFollowings(),
                            comment.getUser().getGender(),
                            comment.getUser().getAvatar()
                        )
                    ))
                    .collect(Collectors.toList());

                List<String> hashtags = post.getHashtags() == null
                    ? List.of()
                    : post.getHashtags().stream()
                        .map(Hashtag::getTag)
                        .collect(Collectors.toList());

                boolean isSaved = currentUser.getSavedPost().stream()
                    .anyMatch(saved -> saved.getId().equals(post.getId()));

                return new PostDTO(
                    post.getId(),
                    post.getCaption(),
                    post.getImage(),
                    post.getVideo(),
                    userDTO,
                    likedDTOs,
                    post.getCreatedAt(),
                    commentDTOs,
                    hashtags,
                    isSaved
                );
            })
            .collect(Collectors.toList());
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> opt = postRepository.findById(postId);

        if (opt.isEmpty()) {
            throw new Exception("post not found with id " + postId);
        }

        return opt.get();
    }

    @Override
    public List<PostDTO> findAllPost() {
        List<Post> posts = postRepository.findAll();
        // Optional: You may want to pass the current user here if available for isSaved detection.
        // Otherwise, fallback to false for all.
        return posts.stream()
            .map(post -> {
                UserDTO userDTO = new UserDTO(
                    post.getUser().getId(),
                    post.getUser().getFirstName(),
                    post.getUser().getLastName(),
                    post.getUser().getEmail(),
                    post.getUser().isTermsAccepted(),
                    post.getUser().getFollowers(),
                    post.getUser().getFollowings(),
                    post.getUser().getGender(),
                    post.getUser().getAvatar()
                );

                List<UserDTO> likedDTOs = post.getLiked().stream()
                    .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.isTermsAccepted(),
                        user.getFollowers(),
                        user.getFollowings(),
                        user.getGender(),
                        user.getAvatar()
                    ))
                    .collect(Collectors.toList());

                List<CommentDTO> commentDTOs = post.getComments().stream()
                    .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        new UserDTO(
                            comment.getUser().getId(),
                            comment.getUser().getFirstName(),
                            comment.getUser().getLastName(),
                            comment.getUser().getEmail(),
                            comment.getUser().isTermsAccepted(),
                            comment.getUser().getFollowers(),
                            comment.getUser().getFollowings(),
                            comment.getUser().getGender(),
                            comment.getUser().getAvatar()
                        )
                    ))
                    .collect(Collectors.toList());

                // Map hashtags to List<String>
                List<String> hashtags = post.getHashtags() == null
                    ? List.of()
                    : post.getHashtags().stream()
                        .map(Hashtag::getTag)
                        .collect(Collectors.toList());

                boolean isSaved = false; // Can't detect saved state without current user

                return new PostDTO(
                    post.getId(),
                    post.getCaption(),
                    post.getImage(),
                    post.getVideo(),
                    userDTO,
                    likedDTOs,
                    post.getCreatedAt(),
                    commentDTOs,
                    hashtags,
                    isSaved
                );
            })
            .collect(Collectors.toList());
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
        } else {
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }

        return postRepository.save(post);
    }

    @Override
    public List<PostDTO> getSavedPosts(Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        List<Post> savedPosts = user.getSavedPost();
        return savedPosts.stream().map(post -> {
            UserDTO userDTO = new UserDTO(
                post.getUser().getId(),
                post.getUser().getFirstName(),
                post.getUser().getLastName(),
                post.getUser().getEmail(),
                post.getUser().isTermsAccepted(),
                post.getUser().getFollowers(),
                post.getUser().getFollowings(),
                post.getUser().getGender(),
                post.getUser().getAvatar()
            );

            List<UserDTO> likedDTOs = post.getLiked().stream()
                .map(likedUser -> new UserDTO(
                    likedUser.getId(),
                    likedUser.getFirstName(),
                    likedUser.getLastName(),
                    likedUser.getEmail(),
                    likedUser.isTermsAccepted(),
                    likedUser.getFollowers(),
                    likedUser.getFollowings(),
                    likedUser.getGender(),
                    likedUser.getAvatar()
                ))
                .collect(Collectors.toList());

            List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(comment -> new CommentDTO(
                    comment.getId(),
                    comment.getContent(),
                    new UserDTO(
                        comment.getUser().getId(),
                        comment.getUser().getFirstName(),
                        comment.getUser().getLastName(),
                        comment.getUser().getEmail(),
                        comment.getUser().isTermsAccepted(),
                        comment.getUser().getFollowers(),
                        comment.getUser().getFollowings(),
                        comment.getUser().getGender(),
                        comment.getUser().getAvatar()
                    )
                ))
                .collect(Collectors.toList());

            List<String> hashtags = post.getHashtags() == null
                ? List.of()
                : post.getHashtags().stream()
                    .map(h -> h.getTag())
                    .collect(Collectors.toList());

            boolean isSaved = true;

            return new PostDTO(
                post.getId(),
                post.getCaption(),
                post.getImage(),
                post.getVideo(),
                userDTO,
                likedDTOs,
                post.getCreatedAt(),
                commentDTOs,
                hashtags,
                isSaved
            );
        }).collect(Collectors.toList());
    }
}