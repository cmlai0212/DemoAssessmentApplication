package com.example.DemoAssessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "client")
public class Client {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    private String name;
    private String newIC;
    private String email;
    
    public Client() {}    
    
	public Client(long id, String name, String newIC, String email) {		
		this.id = id;
		this.name = name;
		this.newIC = newIC;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@NotNull
	@Size(max=255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
	@Size(max=20)
	public String getNewIC() {
		return newIC;
	}
	public void setNewIC(String newIC) {
		this.newIC = newIC;
	}
	
	@Size(max=100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
        
}
