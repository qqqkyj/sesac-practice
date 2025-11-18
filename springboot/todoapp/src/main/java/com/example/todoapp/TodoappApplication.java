package com.example.todoapp;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
    }

    //샘플 데이터 생성
    @Bean
    public CommandLineRunner init(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(new TodoDTO(null, "study", "springboot", false));
            todoRepository.save(new TodoDTO(null, "cook", "ramen", true));
            todoRepository.save(new TodoDTO(null, "workout", "gym", false));
        };
    }
}
