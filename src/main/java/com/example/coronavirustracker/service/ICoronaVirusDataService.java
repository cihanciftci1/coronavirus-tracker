package com.example.coronavirustracker.service;

import java.io.IOException;
import java.util.List;

import com.example.coronavirustracker.entities.LocationStats;

public interface ICoronaVirusDataService {
	void fetchVirusData() throws IOException, InterruptedException;
	List<LocationStats> getAll();
}
