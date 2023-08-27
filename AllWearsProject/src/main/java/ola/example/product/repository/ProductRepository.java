package ola.example.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import ola.example.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
  
  List<Product> findByTitle(String title);

}