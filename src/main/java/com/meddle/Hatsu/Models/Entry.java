package com.meddle.Hatsu.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "entries")
public class Entry {
   Entry() {
   }

   Entry(Long igdbId, Long userId, int score, short status) {
      this.igdbId = igdbId;
      this.userId = userId;
      this.score = score;
      this.status = status;
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private Long igdbId;

   @NotNull
   private Long userId;

   @Range(min = 0, max = 100)
   private int score;

   // -1: deleted, 0: planning, 1: completed, 2: paused, 4: dropped
   @NotNull
   @Range(min = -1, max = 4)
   private short status;

   @CreationTimestamp
   private LocalDateTime createdAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;

   public Long getId() {
      return id;
   }

   public Long getIgdbId() {
      return igdbId;
   }

   public Long getUserId() {
      return userId;
   }

   public int getScore() {
      return score;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public short getStatus() {
      return status;
   }

   public void setStatus(short status) {
      this.status = status;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

}
