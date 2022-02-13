package com.example.coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.coronavirustracker.entities.LocationStats;
import com.example.coronavirustracker.repo.IRepo;

@Service
public class CoronaVirusDataService implements ICoronaVirusDataService {
	@Autowired
	IRepo repository;
	
	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private int totalCases;
	public int getTotalCases() {
		return totalCases;
	}

	@PostConstruct //This annotation provides application to start this method. 
	@Scheduled(cron = "0 0 1 * * ? ") // Corona virus data is updating every day so application should call this method every day. It's scheduled to 1 AM every day.
	public void fetchVirusData() throws IOException, InterruptedException {
		repository.deleteAll();
		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		
		//withFirstRecordAsHeader is deprecated. This format should be used.
		CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT)
		        .setHeader()
		        .setSkipHeaderRecord(true)
		        .build();
		
		StringReader csvBodyReader=new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVParser.parse(csvBodyReader, format);
		for (CSVRecord record : records) {
			LocationStats locationStats=new LocationStats();
		    
			if(!record.get("Province/State").isEmpty()) {
		    	locationStats.setState(record.get("Province/State"));
		    }
		    
		    locationStats.setCountry(record.get("Country/Region"));
		    int lastestCases=Integer.parseInt(record.get(record.size()-1));
		    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
		    locationStats.setLatestCases(lastestCases);
		    locationStats.setDiffFromPrevDay(lastestCases-prevDayCases);
		    repository.save(locationStats);
		}
		
		int total = 0;
		for(LocationStats stat:repository.findAll()) {
			total+=stat.getLatestCases();
		}
		totalCases=total;
	}

	@Override
	public List<LocationStats> getAll() {
		return repository.findAll();
	}
	
}
