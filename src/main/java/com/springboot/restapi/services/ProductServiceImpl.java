package com.springboot.restapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.restapi.domain.Product;
import com.springboot.restapi.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

 private final Logger logger =LoggerFactory.getLogger(this.getClass());
	
	private ProductRepository productRepo;
	
		@Autowired
	    public void setProductRepository(ProductRepository productRepository) {
	        this.productRepo = productRepository;
	    }

	
	public Iterable<Product> listAllProducts() {
		 
		logger.debug("listAllProducts called");
		return productRepo.findAll();
	}

	public Product getProductById(Integer id) {

		logger.debug("getProductById called");
		return productRepo.findOne(id);
	}

	public Product saveProduct(Product product) {

		logger.debug("saveProduct called");
		return productRepo.save(product);
	}

	public void deleteProduct(Integer id) {
	
		logger.debug("deleteProduct called");
		productRepo.delete(id);
		
	}

	
}
