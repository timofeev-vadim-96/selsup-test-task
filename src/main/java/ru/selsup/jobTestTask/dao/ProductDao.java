package ru.selsup.jobTestTask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.selsup.jobTestTask.model.Document;

public interface ProductDao extends JpaRepository<Document.Product, String> {
}
