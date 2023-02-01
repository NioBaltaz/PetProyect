package com.codingdojo.proyecto.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotNull(message = "Debes especificar el nombre")
    @Size(min = 1, max = 50, message = "El nombre debe medir entre 1 y 50")
    private String nombre;

    @NotNull(message = "Debes especificar el código")
    @Size(min = 1, max = 50, message = "El código debe medir entre 1 y 50")
    private String codigo;

    @NotNull(message = "Debes especificar el precio")
    @Min(value = 0, message = "El precio mínimo es 0")
    private Float precio;
    
    @NotNull(message = "Debes especificar la existencia")
    @Min(value = 0, message = "La cantidad mínima es 0")
    private Float existencia;
    
    private String image;
    
	@Column(updatable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created_at;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updated_at;
	
	
	
	
	
	
	
	
	
	
public Product(Long id,
			@NotNull(message = "Debes especificar el nombre") @Size(min = 1, max = 50, message = "El nombre debe medir entre 1 y 50") String nombre,
			@NotNull(message = "Debes especificar el código") @Size(min = 1, max = 50, message = "El código debe medir entre 1 y 50") String codigo,
			@NotNull(message = "Debes especificar el precio") @Min(value = 0, message = "El precio mínimo es 0") Float precio,
			@NotNull(message = "Debes especificar la existencia") @Min(value = 0, message = "La cantidad mínima es 0") Float existencia,
			Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.precio = precio;
		this.existencia = existencia;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Product(String image) {
	super();
	this.image = image;
}

	public Product() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	
    public void restarExistencia(Float cantidad) {
        this.existencia -= cantidad;
    }
	
	public boolean sinExistencia() {
        return this.existencia <= 0;
    }

	public Float getExistencia() {
		return existencia;
	}

	public void setExistencia(Float cantidad) {
		this.existencia = cantidad;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
    
	@PrePersist
    protected void onCreate(){
        this.created_at = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = new Date();
    }
	
}