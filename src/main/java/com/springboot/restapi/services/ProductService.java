package com.springboot.restapi.services;

import com.springboot.restapi.domain.Product;

public interface ProductService {
	
	   	Iterable<Product> listAllProducts();

	    Product getProductById(Integer id);

	    Product saveProduct(Product product);

	    void deleteProduct(Integer id);

}
