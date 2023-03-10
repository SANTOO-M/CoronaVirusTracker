package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServiceRepository;
import com.example.demo.services.CoronaVirusDataServices;

@Controller
public class HomeController {

	@Autowired
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
	 
	@RequestMapping(path="/collectChartData/{id}",produces = {"application/json"})
	@ResponseBody
	public Optional<LocationStates> collectChartDataByCountryID(@PathVariable("id") int countryid,Model model){
		System.out.println("View Chart Data by Country ID here...");
		Optional<LocationStates> locationStates=repository.findById(countryid);
		return locationStates;
	}
	
	@RequestMapping(path = "/collectChartData/country/{name}",produces = {"application/json"})
	@ResponseBody
	public List<LocationStates> collectChartDataByCountryName(@PathVariable("name") String countryName,Model model)
	{
		System.out.println("View Chart Data by Country Name here...");
		List<LocationStates> locationstates=repository.findBycountry(countryName);
		return locationstates;
	}
	
	
	@RequestMapping(path = "/collectChartData/top={count}",produces ={"application/json"})
	@ResponseBody 
	public List<LocationStates> collectChartDataByCountryTop(@PathVariable("count") int count,Model model){
		System.out.println("View Chart Data by total deaths ..."); 
		List<LocationStates> locationstates=repository.findcountryBylatestTotalDeaths(count); 
		return locationstates; 
	}
	 
	
	@RequestMapping(value="/ViewChart",method = RequestMethod.GET)
	public ModelAndView viewChart() {
		return new ModelAndView("ViewChart").addObject("myURL",new String("http://localhost:8082/collectChartData"));
	}

	@GetMapping("/ViewChart/top={count}")
	public String ViewChartByTopDeathCase(@PathVariable("count") int count,Model m){
		m.addAttribute("count",count);
		m.addAttribute("myURL", "http://localhost:8082/collectChartData/top="+count);
		return "ViewChart";
	}
	
	
	@GetMapping("/ViewChart/country/{name}")
	public String ViewChartByCountryName(@PathVariable("name") String name,Model m) {
		m.addAttribute("name", name);
		m.addAttribute("myURL", "http://localhost:8082/collectChartData/country/"+name);
		return "ViewChart";
	}

}






















