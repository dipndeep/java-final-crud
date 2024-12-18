package com.dipndeep.crudproject.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.dipndeep.crudproject.models.Product;
import com.dipndeep.crudproject.models.ProductDto;
import com.dipndeep.crudproject.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
   @Autowired
   private ProductsRepository repo;

   @GetMapping({"","/"})
   public String showProductList (Model model) {
      List<Product> products = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
      model.addAttribute("products", products);
      return "products/index";
   }

   @GetMapping("/create")
   public String showCreatePage(Model model) {
      ProductDto productDto = new ProductDto();
      model.addAttribute("productDto", productDto);
      return "products/CreateProduct";
   }

   @PostMapping("/create")
   public String createProduct (
   @Valid @ModelAttribute ProductDto productDto,
   BindingResult result
   ) {
      if (productDto.getImageFile().isEmpty()) {
      result.addError(new FieldError("productDto", "imageFile", "Foto Tidak Boleh Kosong"));
      }
      if (result.hasErrors()) {
         return "products/CreateProduct";
      }

      MultipartFile image = productDto.getImageFile();
      java.util.Date createdAt = new java.util.Date(); // Gunakan java.util.Date
      String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
      try {
         String uploadDir = "public/images/";
         Path uploadPath = Paths.get(uploadDir);

         if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
         }
         try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
            StandardCopyOption.REPLACE_EXISTING);
         }
      } catch (Exception ex) {
         System.out.println("Exception : " + ex.getMessage());
      }

      Product product = new Product();
      product.setName(productDto.getName());
      product.setBrand(productDto.getBrand());
      product.setCategory(productDto.getCategory());
      product.setPrice(productDto.getPrice());
      product.setDescription(productDto.getDescription());

      // Mengonversi java.util.Date menjadi java.sql.Date
      product.setCreatedAt(new java.sql.Date(createdAt.getTime())); // Pastikan nilai createdAt di-set
      product.setImageFileName(storageFileName);
      repo.save(product);

      return "redirect:/products";
   }
}
