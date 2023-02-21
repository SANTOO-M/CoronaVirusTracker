package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServiceRepository;
import com.example.demo.services.CoronaVirusDataServices;

@Controller
public class HomeController {

	CoronaVirusDataServices crnService;

	@Autowired
	CoronaVirusDataServiceRepository repository;

	@Autowired
	public void setCrnService(CoronaVirusDataServices crnService) {
		this.crnService = crnService;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<LocationStates> allstates = crnService.getAllstates();
		int totalDeathsReported = allstates.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates", allstates);
		model.addAttribute("totalDeathsReported", totalDeathsReported);
		repository.saveAll(allstates);
		return "home";
	}

	@RequestMapping(path="/collectChartData",produces = {"application/json"})
	@ResponseBody
	public List<LocationStates> collectCharData(Model model){
		System.out.println("View chart data here...");
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates", allstates);
		model.addAttribute("totalDeathsReported", totalDeathsReported);
		return allstates;
	}
	 
	@RequestMapping(path="/collectCharData/{id}",produces = {"application/json"})
	@ResponseBody
	public Optional<LocationStates> collectChartDataByCountryID(@PathVariable("id") int countryid,Model model){
		System.out.println("View Chart Data by Country ID here...");
		Optional<LocationStates> locationStates=repository.findById(countryid);
		return locationStates;
	}
	
	@RequestMapping(path = "/collectChartData/country={name}",produces = {"application/json"})
	@ResponseBody
	{
		
	}
}
