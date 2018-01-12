package com.springboot.restapi.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;

import com.springboot.restapi.domain.Product;


@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product,Integer>  {

}
