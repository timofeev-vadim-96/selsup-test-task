package ru.selsup.jobTestTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.selsup.jobTestTask.dao.DescriptionDao;
import ru.selsup.jobTestTask.dao.DocumentDao;
import ru.selsup.jobTestTask.dao.ProductDao;
import ru.selsup.jobTestTask.dao.SignDao;
import ru.selsup.jobTestTask.model.Document;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentDao documentDao;
    private final DescriptionDao descriptionDao;
    private final ProductDao productDao;
    private final SignDao signDao;

    public Document saveDocument(Document document, String sign){
        descriptionDao.save(document.getDescription());
        signDao.save(new Document.Signature(sign));

        List<Document.Product> products = document.getProducts();
        for (Document.Product value : products) {
            Document.Product product = productDao.findById(value
                            .getCertificate_document_number())
                    .orElse(null);
            if (product == null) productDao.save(value);
        }

        return documentDao.save(document);
    }
}
