package com.dipndeep.crudproject.models;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDto {

   private Long id; // Tambahkan atribut id

   @NotEmpty(message = "Nama Tidak Boleh Kosong")
   private String name;

   @NotEmpty(message = "Brand Tidak Boleh Kosong")
   private String brand;

   @NotEmpty(message = "Kategori Tidak Boleh Kosong")
   private String category;

   @Min(0)
   private double price;

   @Size(min = 10, message = "Deskripsi direkomendasikan setidaknya 10 karakter.")
   @Size(max = 2000, message = "Deskripsi direkomendasikan tidak lebih 2000 karakter.")
   private String description;

   private MultipartFile imageFile;

   private String imageFileName;

   private LocalDateTime createdAt;

   // Getter dan Setter untuk id
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   // Getter dan Setter yang lain (sudah ada)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public MultipartFile getImageFile() {
      return imageFile;
   }

   public void setImageFile(MultipartFile imageFile) {
      this.imageFile = imageFile;
   }

   // Getter dan Setter untuk imageFileName
   public String getImageFileName() {
      return imageFileName;
   }

   public void setImageFileName(String imageFileName) {
      this.imageFileName = imageFileName;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }
}