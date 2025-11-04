package com.example.digitalwishlist.Model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "wish")
public class Wish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="wishlist_id")
    private Wishlist wishlist;

    @Column(nullable=false)
    private String title;

    private String url;
    private Double price;
    private String notes;

    @Column(nullable=false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public Wishlist getWishlist() { return wishlist; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public Double getPrice() { return price; }
    public String getNotes() { return notes; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setWishlist(Wishlist wishlist) { this.wishlist = wishlist; }
    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setPrice(Double price) { this.price = price; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
