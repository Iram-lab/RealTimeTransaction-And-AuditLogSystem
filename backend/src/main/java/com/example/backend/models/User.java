package com.example.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;

    private String name;
    private int amount;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
