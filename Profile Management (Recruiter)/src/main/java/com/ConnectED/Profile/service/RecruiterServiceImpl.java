
package com.ConnectED.Profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ConnectED.Profile.model.Recruiter;
import com.ConnectED.Profile.repository.RecruiterRepository;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public Recruiter save(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }

    @Override
    public Recruiter getByEmail(String email) {
        return recruiterRepository.findByEmail(email);
    }

    @Override
    public Recruiter updateByEmail(String email) {
        Recruiter recruiter = recruiterRepository.findByEmail(email);
        if (recruiter != null) {
            // Implement your update logic here
        }
        return null;
    }

    @Override
    public void deleteByEmail(String email) {
        Recruiter recruiter = recruiterRepository.findByEmail(email);
        if (recruiter != null) {
            recruiterRepository.delete(recruiter);
        }
    }
}
