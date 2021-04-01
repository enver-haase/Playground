package com.example.application.data.service;

import com.example.application.data.entity.SamplePerson;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import javax.annotation.Nullable;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, Integer> {

}