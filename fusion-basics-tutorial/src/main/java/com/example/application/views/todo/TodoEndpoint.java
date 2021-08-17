package com.example.application.views.todo;

import java.util.List;

import com.example.application.db.Todo;
import com.example.application.db.TodoRepo;
import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;

@Endpoint
@AnonymousAllowed
public class TodoEndpoint {

  private TodoRepo repo;

  public TodoEndpoint(TodoRepo repo) {
    this.repo = repo;
  }

  public List<Todo> getTodos() {
    return repo.findAll();
  }

  public Todo saveTodo(Todo todo) {
    return repo.save(todo);
  }
}
