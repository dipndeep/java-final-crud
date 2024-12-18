package com.dipndeep.crudproject.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dipndeep.crudproject.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
   
}
