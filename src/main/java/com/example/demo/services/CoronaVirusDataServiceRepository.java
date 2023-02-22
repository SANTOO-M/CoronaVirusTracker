package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LocationStates;

@Repository
public interface CoronaVirusDataServiceRepository extends JpaRepository<LocationStates, Integer>{

	List<LocationStates> findBycountry(String countryName);

	@Query(value="select * from location_states order by latest_total_deaths desc limit ?1", nativeQuery = true)
	List<LocationStates> findcountryBylatestTotalDeaths(int limit);


	 
}
