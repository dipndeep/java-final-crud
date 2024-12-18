package com.dipndeep.crudproject.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String name;
   private String brand;
   private String category;
   private double price;

   @Column(columnDefinition = "TEXT")
   private String description;

   private Date createdAt;  // Gunakan java.sql.Date untuk createdAt
   private String imageFileName;

   // Getter dan setter untuk 'createdAt'
   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;  // Implementasikan setter untuk createdAt
   }

   // Getter dan setter untuk field lainnya
   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

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

   public String getImageFileName() {
      return imageFileName;
   }

   public void setImageFileName(String imageFileName) {
      this.imageFileName = imageFileName;
   }
}