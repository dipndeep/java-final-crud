package com.dipndeep.crudproject.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
// import java.util.Date;
import java.util.List;
// import java.sql.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dipndeep.crudproject.models.Product;
import com.dipndeep.crudproject.models.ProductDto;
import com.dipndeep.crudproject.repository.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
   @Autowired
   private ProductsRepository repo;

   @GetMapping({ "", "/" })
   public String showProductList(Model model) {
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
   public String createProduct(
         @Valid @ModelAttribute ProductDto productDto,
         BindingResult result) {
      System.out.println("Request received to create a product.");

      if (productDto.getImageFile().isEmpty()) {
         result.addError(new FieldError("productDto", "imageFile", "Foto Tidak Boleh Kosong"));
      }
      if (result.hasErrors()) {
         System.out.println("Validation errors: " + result.getAllErrors());
         return "products/CreateProduct";
      }

      // ... Proses lainnya ...

      System.out.println("Product created successfully.");
      return "redirect:/products";
   }

   @GetMapping("/edit")
   public String showEditPage(
         Model model,
         @RequestParam int id) {
      try {
         // Ambil data Product dari repository berdasarkan ID
         Product product = repo.findById(id)
               .orElseThrow(() -> new RuntimeException("Product dengan ID " + id + " tidak ditemukan"));

         // Buat ProductDto dan isi dengan data dari Product
         ProductDto productDto = new ProductDto();
         productDto.setId((long) id); // Setel ID di ProductDto
         productDto.setName(product.getName());
         productDto.setBrand(product.getBrand());
         productDto.setCategory(product.getCategory());
         productDto.setPrice(product.getPrice());
         productDto.setDescription(product.getDescription());
         productDto.setImageFileName(product.getImageFileName());

         // Tambahkan ProductDto ke model
         model.addAttribute("productDto", productDto);
      } catch (Exception ex) {
         System.out.println("Exception: " + ex.getMessage());
         return "redirect:/products"; // Redirect jika terjadi error
      }
      return "products/EditProduct"; // Tampilkan halaman edit
   }

   @PostMapping("/edit")
   public String updateProduct(
         Model model,
         @RequestParam int id,
         @Valid @ModelAttribute ProductDto productDto,
         BindingResult result) {
      try {
         // Ambil data product yang ada dari database
         Product product = repo.findById(id)
               .orElseThrow(() -> new RuntimeException("Product dengan ID " + id + " tidak ditemukan"));

         if (result.hasErrors()) {
            model.addAttribute("productDto", productDto);
            return "products/EditProduct";
         }

         // Cek apakah ada file gambar baru yang diunggah
         MultipartFile imageFile = productDto.getImageFile();
         if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            // Hapus gambar lama jika ada
            String oldImageFileName = product.getImageFileName();
            if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
               Path oldImagePath = Paths.get(uploadDir + oldImageFileName);
               try {
                  Files.deleteIfExists(oldImagePath);
               } catch (Exception ex) {
                  System.out.println("Failed to delete old image: " + ex.getMessage());
               }
            }

            // Simpan gambar baru
            String storageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            try (InputStream inputStream = imageFile.getInputStream()) {
               Files.createDirectories(uploadPath);
               Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

            // Update nama file gambar pada produk
            product.setImageFileName(storageFileName);
         }

         // Update field produk lainnya
         product.setName(productDto.getName());
         product.setBrand(productDto.getBrand());
         product.setCategory(productDto.getCategory());
         product.setPrice(productDto.getPrice());
         product.setDescription(productDto.getDescription());

         repo.save(product); // Simpan perubahan ke database
      } catch (Exception ex) {
         System.out.println("Exception : " + ex.getMessage());
      }
      return "redirect:/products";
   }

   @GetMapping("/delete")
   public String deleteProduct(
         @RequestParam int id) {
      try {
         Product product = repo.findById(id).get();

         Path imagePath = Paths.get("public/images/" + product.getImageFileName());
         try {
            Files.delete(imagePath);
         } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
         }
         repo.delete(product);
      } catch (Exception ex) {
         System.out.println("Exception : " + ex.getMessage());
      }
      return "redirect:/products";
   }
}
