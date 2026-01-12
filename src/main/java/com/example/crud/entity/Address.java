package com.example.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String city;
	private String state;

	// ===== NEW: ManyToOne relationship to User =====
//    @ManyToOne
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private User user;

	// Constructors
	public Address() {
	}

	public Address(String city, String state, User user) {
		this.city = city;
		this.state = state;
		this.user = user;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}