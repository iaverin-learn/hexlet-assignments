package exercise.controller;

import java.util.List;
import java.util.stream.Collectors;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.BadRequestException;
import exercise.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.repository.CategoryRepository;
import exercise.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @Autowired
    private CategoryRepository categoryRepository;
    
    @GetMapping("")
    public List<ProductDTO> getProducts() {
        var products = productRepository.findAll();
        List<ProductDTO> productsOut = products.stream().map(productMapper::map).collect(Collectors.toList());
        
        return productsOut;
    }
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        var product  = productRepository.findById(id).get();
     
        return productMapper.map(product);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductCreateDTO productData) {

        var product = productMapper.map(productData);
        var newCategoryId = productData.getCategoryId();
    
        var category = categoryRepository.findById(newCategoryId).orElseThrow( () -> new BadRequestException("dfdf"));
        
        product.setCategory(category);
        
        return productMapper.map(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO updatedProduct) {
        var product = productRepository.findById(id).get();

        var newCategoryId = updatedProduct.getCategoryId();
        if (newCategoryId != null) {
            var category = categoryRepository.findById(newCategoryId.get()).get();
            product.setCategory(category);
        }
        

        productMapper.update(updatedProduct, product);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    // END
}
