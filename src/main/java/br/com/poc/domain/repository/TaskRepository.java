package br.com.poc.domain.repository;

import br.com.poc.domain.Task;

import java.util.List;

public interface TaskRepository {
    void save(Task task) throws Exception;

    List<Task> listAll() throws Exception;
}
