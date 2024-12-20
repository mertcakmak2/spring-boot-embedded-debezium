package com.springbootevent.bootstrap;

import com.springbootevent.entity.Post;
import com.springbootevent.repository.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PostPersist {

    @Bean
    ApplicationRunner applicationRunner(PostRepository postRepository) {
        return args -> {
            postRepository.save(Post.builder().content("content-1").title("title-1").build());
        };
    }

}
