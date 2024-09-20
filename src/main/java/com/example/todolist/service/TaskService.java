    package com.example.todolist.service;

    import com.example.todolist.model.Task;
    import com.example.todolist.repository.TaskRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class TaskService {

        @Autowired
        private TaskRepository taskRepository;

        public List<Task> getAllTasks() {
            return taskRepository.findAll();
        }

        public Task addTask(Task task) {
            return taskRepository.save(task);
        }

        public Task updateTask(Long id, Task updatedTask) {
            Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDeadline(updatedTask.getDeadline());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }

        public void deleteTask(Long id) {
            taskRepository.deleteById(id);
        }
    }
