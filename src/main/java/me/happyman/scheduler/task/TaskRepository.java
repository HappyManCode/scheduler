package me.happyman.scheduler.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskById(Long Id);

    List<Task> findAllByUserId(Long userId);

}
