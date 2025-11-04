package com.example.digitalwishlist.Model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, unique=true)
    private String slug;

    @Column(nullable=false)
    private boolean publicList = false;

    @Column(nullable=false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    public Long getId() { return id; }
    public User getOwner() { return owner; }
    public String getTitle() { return title; }
    public String getSlug() { return slug; }
    public boolean isPublicList() { return publicList; }
    public Instant getCreatedAt() { return createdAt; }
    public List<Wish> getWishes() { return wishes; }

    public void setId(Long id) { this.id = id; }
    public void setOwner(User owner) { this.owner = owner; }
    public void setTitle(String title) { this.title = title; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setPublicList(boolean publicList) { this.publicList = publicList; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setWishes(List<Wish> wishes) { this.wishes = wishes; }
}
