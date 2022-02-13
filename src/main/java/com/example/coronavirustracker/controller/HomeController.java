package com.example.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusDataService dataService;
	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("datas", dataService.getAll());
		model.addAttribute("totalCases", dataService.getTotalCases());
		return "home";
	}
}
