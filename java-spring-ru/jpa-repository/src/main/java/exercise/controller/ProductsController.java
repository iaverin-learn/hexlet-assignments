package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping()
    public List<Product> getProducts(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        Sort sort = Sort.by(Sort.Order.asc("price"));    
        int maxFilterValue; 
        if (max == null) {
            maxFilterValue = 999999999;
        }
        else {
            maxFilterValue = max;
        }
        int minFilterValue; 
        if (min == null) {
            minFilterValue = -1;
        } 
        else {
            minFilterValue = min;
        }
        return productRepository.findByPriceBetweenAndSort(minFilterValue, maxFilterValue, sort);
        //     }

        // return productRepository.findAll(sort);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
