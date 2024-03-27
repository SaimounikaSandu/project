
package com.ConnectED.Profile.model;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "profilemanagement_Recruiter")
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Lob
    @JsonSerialize(using = BlobSerializer.class)
    private Blob image;
    
    @Column(unique = true)
    private String email;
    
    
    private String firstName;
    private String lastName;
    private String gender;
    private String bio;
    private String companyName;
    private String Mob;
    private String jobTitle;
    private String industry;
    private String userName;
    private String companyWebsite;
    private String linkedinProfile;
    private String city;
    private String state;
    private String country;
    
    
    @Transient
    private String imageBase64;

    public String getImageBase64() {
        if (image != null) {
            try {
                byte[] bytes = image.getBytes(1, (int) image.length());
                return Base64.getEncoder().encodeToString(bytes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getgender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getbio() {
        return bio;
    }

    public void setbio(String bio) {
        this.bio = bio;
    }

    public String getcompanyName() {
        return companyName;
    }

    public void setcompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String Mob) {
        this.Mob = Mob;
    }

    public String getjobTitle() {
        return jobTitle;
    }

    public void setjobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getindustry() {
        return industry;
    }

    public void setindustry(String industry) {
        this.industry = industry;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getcompanyWebsite() {
        return companyWebsite;
    }

    public void setcompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getlinkedinProfile() {
        return linkedinProfile;
    }
    public void setlinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }
    
    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }
    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }
    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }
    
    
    
    
}

