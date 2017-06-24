package com.websystique.springmvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="APP_LOCATION")
public class Location implements Serializable{

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name="NAME", unique=false, nullable=false)
	private String name;
	
	@NotEmpty
	@Column(name="ADDRESS", nullable=false)
	private String address;
		
	@NotEmpty
	@Column(name="COUNTRY", nullable=false)
	private String country;

	@NotNull
	@Column(name="LATITUDE", nullable=false)
	private Double latitude;

	@NotNull
	@Column(name="LONGITUDE", nullable=false)
	private Double longitude;

	@NotEmpty
	@Column(name="IMAGE_URL", nullable=false)
	private String imageURL;

	@NotEmpty
	@Column(name="TYPE", nullable=false)
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
