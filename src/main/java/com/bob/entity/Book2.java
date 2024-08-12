package com.bob.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "book2")
public class Book2 {


    @Id
    private String id;

    private String name;
    private String author;
    private String price;

    public Book2() {
    }

	public Book2(String id, String name, String author, String price) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book2 [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + "]";
	}

    
}
