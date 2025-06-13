package com.meddle.Hatsu.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "entries")
public class Entry {
   public Entry() {
   }

   public Entry(Long playerId, Long igdbId, short status) {
      this.playerId = playerId;
      this.igdbId = igdbId;
      this.status = status;
   }

   public Entry(Long playerId, Long igdbId, Integer score, short status) {
      this.playerId = playerId;
      this.igdbId = igdbId;
      this.status = status;
      this.score = score;
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private Long igdbId;

   @Column(nullable = false)
   private Long playerId;

   @Range(min = 0, max = 100)
   @Column(nullable = true)
   private Integer score;

   // 0: planning, 1: playing, 2: completed, 3:paused 4: dropped
   @Range(min = 0, max = 4)
   @Column(nullable = false)
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

   public Long getPlayerId() {
      return playerId;
   }

   public Integer getScore() {
      return score;
   }

   public void setScore(Integer score) {
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
