package ola.example.product.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ola.example.product.model.Product;

public interface ProductService {
    

    Product saveProduct(String title, String description,String price,String size, MultipartFile file);

    byte[] downloadProductImage(Long id);

    List<Product> getAllProducts(String title);

    void delete(Long id);

  

    Product getProduct(Long id);
    Product UpdataProduct(long id,Product product);

   

    Product updateProducts(Long id, String title, String description,String price,String size);

}
