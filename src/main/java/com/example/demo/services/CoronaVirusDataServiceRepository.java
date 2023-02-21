package com.example.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.LocationStates;

public interface CoronaVirusDataServiceRepository extends JpaRepository<LocationStates, Integer>{

}
