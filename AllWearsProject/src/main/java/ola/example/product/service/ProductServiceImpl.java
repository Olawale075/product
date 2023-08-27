package ola.example.product.service;

import static org.apache.http.entity.ContentType.IMAGE_BMP;
//import static org.apache.http.entity.ContentType.Image;
import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ola.example.product.config.BucketName;
import ola.example.product.exception.ResourceNotFoundException;
import ola.example.product.model.Product;
import ola.example.product.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileStore fileStore;
    private final ProductRepository repository;

    @Override
    public Product saveProduct(String title, String description,String price,String size, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_JPEG.getMimeType(),
                IMAGE_GIF.getMimeType()
                ).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Product in the database
        String path = String.format("%s/%s", BucketName.Product_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Product product = Product.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .price(price)
                .size(size)
                .imageFileName(fileName)
                .build();
        repository.save(product);
        // return repository.findByTitle(product.getTitle());
        return product;
    }

    @Override
    public byte[] downloadProductImage(Long id) {
        Product product = repository.findById(id).get();
        return fileStore.download(product.getImagePath(), product.getImageFileName());
    }

    @Override
    public List<Product> getAllProducts( String title) {
        
    if (Strings.isEmpty(title)) {
       
         List<Product> products = new ArrayList<>();
        repository.findAll().forEach(products::add);
        System.out.println(title);
        return products;
        } 
      else  if(Strings.isNotBlank(title))  {
  List<Product> products = 
            repository.findByTitle(title);
            return products ;
        } 
       
        { 
            List<Product> products = new ArrayList<>(); 
            repository.findByTitle(title);
            return products ;}
    }


    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);;
    }

  

    @Override
    public Product getProduct(Long id) {
        // TODO Auto-generated method stub
        Product product =repository.findById(id).get();

        return product;    }

  

    @Override
    public Product updateProducts(Long id, String title, String description,String price,String size) {
        final  Product product = this.repository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Employee not exist with id" + id));
  
product.setTitle(title);
product.setPrice(price);
product.setDescription(description);
product.setSize(size);
repository.save(product);
 return product;
  
    }

    @Override
    public Product UpdataProduct(long id, Product product) {
      Product products=repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cant find id" + id));
    
      products.setDescription(product.getDescription());
      products.setPrice(product.getPrice());
      product.setSize(product.getSize());
      product.setTitle(product.getTitle());
      return repository.save(products);
     
    }



      }
     
   


