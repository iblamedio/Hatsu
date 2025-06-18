package com.meddle.Hatsu.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
   public Game() {

   }

   public Game(Long id, String name, String imageUrl, String summary) {
      this.id = id;
      this.name = name;
      this.imageUrl = imageUrl;
      this.summary = summary;
   }

   @Id
   private Long id;

   private String name;

   private String imageUrl;

   private String summary;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public String getSummary() {
      return summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

}
