package ru.selsup.jobTestTask.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "documents")
public class Document {
    @Id
    private String doc_id;
    private String doc_status;
    private String doc_type;
    private boolean importRequest;
    private String owner_inn;
    private String participant_inn;
    private String producer_inn;
    private String production_date;
    private String production_type;
    private String reg_date;
    private String reg_number;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "description_id")
    private Description description;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products;

    @Entity
    @Data
    @NoArgsConstructor
    @Table(name = "descriptions")
    public static class Description {
        @Id
        private String participantInn;
    }

    @Entity
    @Data
    @NoArgsConstructor
    @Table(name = "signs")
    public static class Signature {
        @Id
        private String sign;

        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "document_id")
        private Document document;

        public Signature(String sign) {
            this.sign = sign;
        }
    }

    @Entity
    @Data
    @NoArgsConstructor
    @Table(name = "products")
    public static class Product {
        private String certificate_document;
        private String certificate_document_date;
        @Id
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "document_id")
        private Document document;

        public Product(String certificate_document, String certificate_document_date, String certificate_document_number,
                       String owner_inn, String producer_inn, String production_date, String tnved_code, String uit_code,
                       String uitu_code) {
            this.certificate_document = certificate_document;
            this.certificate_document_date = certificate_document_date;
            this.certificate_document_number = certificate_document_number;
            this.owner_inn = owner_inn;
            this.producer_inn = producer_inn;
            this.production_date = production_date;
            this.tnved_code = tnved_code;
            this.uit_code = uit_code;
            this.uitu_code = uitu_code;
        }
    }
}

