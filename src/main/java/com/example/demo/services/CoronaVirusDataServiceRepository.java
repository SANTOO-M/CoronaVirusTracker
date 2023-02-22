package com.example.demo.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LocationStates;

@Repository
public interface CoronaVirusDataServiceRepository extends JpaRepository<LocationStates, Integer>{

	LocationStates findBycountry(String countryName);

	/*
	 * List<LocationStates> findcountryBylatestTotalDeath(int count);
	 */
}
