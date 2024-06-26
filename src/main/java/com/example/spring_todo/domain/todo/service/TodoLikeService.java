package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.domain.TodoLike;
import com.example.spring_todo.domain.todo.repository.TodoLikeRepository;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.exception.CustomErrorException;
import com.example.spring_todo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoLikeService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoLikeRepository todoLikeRepository;

    @Transactional
    public void likeTodo(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_USER));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));

        boolean alreadyLiked = todoLikeRepository.existsByTodoIdAndUserId(id, currentUserId);
        if (alreadyLiked) {
            throw new CustomErrorException(ErrorCode.ALREADY_LIKED);
        }

        TodoLike todoLike = new TodoLike();
        todoLike.setTodo(todo);
        todoLike.setUser(currentUser);
        todoLikeRepository.save(todoLike);

        todo.incrementLikes();
        todoRepository.save(todo);
    }

    @Transactional
    public void unlikeTodo(Long id, Long currentUserId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));

        TodoLike todoLike = todoLikeRepository.findByTodoIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO_LIKE));

        todoLikeRepository.delete(todoLike);

        todo.decrementLikes();
        todoRepository.save(todo);
    }
}
