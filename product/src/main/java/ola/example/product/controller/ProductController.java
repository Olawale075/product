package ola.example.product.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import ola.example.product.exception.ResourceNotFoundException;
import ola.example.product.model.Product;
import ola.example.product.repository.ProductRepository;
import ola.example.product.service.ProductService;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductController {
    ProductService service;
    ProductRepository repositories;

    @GetMapping
    public List<Product> getProducts(@RequestParam( value="title",required = false) String title) {
        
        return service.getAllProducts(title);
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(
            @RequestParam("title") String title,
            @RequestParam("description") String description, 
            @RequestParam("price") String price,
            @RequestParam("size") String size,
            @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(service.saveProduct(
                title, description, price, size, file), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadProductImage(@PathVariable("id") Long id) {
        return service.downloadProductImage(id);
    }

    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable("id") Long id) {

        service.delete(id);

    }
 @PutMapping(value = "{id}/update")
    public ResponseEntity<Product> updataProductById(@PathVariable("id") Long id, @RequestParam("title") String title,
            @RequestParam("description") String description, @RequestParam("price") String price,
            @RequestParam("size") String size) {
        Product product = service.updateProducts(id, title, description, price, size);
        return ResponseEntity.ok(product);

    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Product> getId(Long id) {
        Product product = service.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Product> update2(@PathVariable("id") long id, @RequestBody Product product) {
        Product products = repositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cant find id" + id));

        products.setDescription(product.getDescription());
        products.setPrice(product.getPrice());
        products.setSize(product.getSize());
        products.setTitle(product.getTitle());
        repositories.save(products);
        return ResponseEntity.ok(products);
    }

}
