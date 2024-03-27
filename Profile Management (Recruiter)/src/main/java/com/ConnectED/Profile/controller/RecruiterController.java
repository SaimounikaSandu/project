
package com.ConnectED.Profile.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ConnectED.Profile.model.Recruiter;
import com.ConnectED.Profile.repository.RecruiterRepository;
import com.ConnectED.Profile.service.RecruiterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    @Autowired
    private RecruiterService recruiterService;
   
    @GetMapping("/{email}")
    public ResponseEntity<Recruiter> getFullProfileByEmail(@PathVariable String email) {
        
        Recruiter profile = recruiterService.getByEmail(email);
        if (profile != null) {
        	
            try {
                Blob imageBlob = profile.getImage(); 
                byte[] bytes = imageBlob.getBytes(1, (int) imageBlob.length());
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                profile.setImageBase64(base64Image);
                return new ResponseEntity<>(profile, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    
    
    @PostMapping("/save")
    public ResponseEntity<?> createOrUpdateProfile(
            HttpServletRequest request,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam(value = "profile",required = false) String profileJson
    ) {
        if (profileJson == null) {
           
            return ResponseEntity.badRequest().body("Profile JSON is required.");
        }

        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            Recruiter profile = objectMapper.readValue(profileJson, Recruiter.class);

            if (file != null) { 
                byte[] bytes = file.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
                profile.setImage(imageBlob);
            }

            Recruiter savedProfile = recruiterService.save(profile);
            return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteProfileByEmail(@PathVariable String email) {
    	recruiterService.deleteByEmail(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String email,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam(value = "profile", required = false) String profileJson) {

        if (profileJson == null) {
            return ResponseEntity.badRequest().body("Profile JSON is required.");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Recruiter updatedProfile = objectMapper.readValue(profileJson, Recruiter.class);

            Recruiter existingProfile = recruiterService.getByEmail(email);
            if (existingProfile == null) {
                return ResponseEntity.notFound().build();
            }

            if (file != null && !file.isEmpty()) { 
                byte[] bytes = file.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
                existingProfile.setImage(imageBlob);
            }

            existingProfile.setfirstName(updatedProfile.getfirstName());
            existingProfile.setlastName(updatedProfile.getlastName());
            existingProfile.setbio(updatedProfile.getbio());
            existingProfile.setcity(updatedProfile.getcity());
            existingProfile.setcountry(updatedProfile.getcountry());
            existingProfile.setMob(updatedProfile.getMob());
            existingProfile.setgender(updatedProfile.getgender());
            existingProfile.setjobTitle(updatedProfile.getjobTitle());
            existingProfile.setindustry(updatedProfile.getindustry());
            existingProfile.setlinkedinProfile(updatedProfile.getlinkedinProfile());
            existingProfile.setcompanyWebsite(updatedProfile.getcompanyWebsite());
            existingProfile.setcompanyName(updatedProfile.getcompanyName());
            existingProfile.setstate(updatedProfile.getstate());
            existingProfile.setuserName(updatedProfile.getuserName());
            Recruiter savedProfile = recruiterService.save(existingProfile);
            return new ResponseEntity<>(savedProfile, HttpStatus.OK);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
        }
    }
}
