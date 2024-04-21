package ru.selsup.jobTestTask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.selsup.jobTestTask.model.Document;

@Repository
public interface DocumentDao extends JpaRepository<Document, String> {
}
