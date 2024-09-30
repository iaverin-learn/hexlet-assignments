package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    ProductMapper productMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductCreateDTO productData) {
        // TODO: process POST request
        var product = productMapper.map(productData);
        product = productRepository.save(product);
        return productMapper.map(product);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id) {
        var product = productRepository
                .findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("Product not found"));
        return productMapper.map(product);
    }

    @GetMapping()
    public List<ProductDTO> getProducts() {
        var products = productRepository.findAll();
        return products.stream().map(productMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ProductDTO updateProductById(@PathVariable long id, @RequestBody ProductUpdateDTO productData) {
        var product = productRepository.findById(id).get();

        productMapper.update(productData, product);
        productRepository.save(product);

        
        return productMapper.map(product);
    }
    
    // END
}
