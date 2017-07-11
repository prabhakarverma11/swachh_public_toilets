package com.websystique.springmvc.model;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "APP_ADMIN_VERIFICATION")
public class AdminVerification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @Column(name = "PHONE", nullable = true)
    private String phone;

    @NotEmpty
    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "LOCATION_TYPE", nullable = true)
    private String locationType;

    @Column(name = "ULB_NAME", nullable = true)
    private String ULBName;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCATION_ID", nullable = false)
    private Location location;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getULBName() {
        return ULBName;
    }

    public void setULBName(String ulbName) {
        this.ULBName = ulbName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        location.setAddress(WordUtils.capitalize(location.getAddress()));
        this.location = location;
    }
}
