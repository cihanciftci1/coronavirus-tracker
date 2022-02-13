package com.example.coronavirustracker.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stats")
public class LocationStats {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column
	private String state = "No State";
	@Column
	private String country;
	@Column
	private int latestCases;
	@Column
	private int diffFromPrevDay;
	
	
	
	public LocationStats() {}
	public LocationStats(String state, String country, int latestTotalCases) {
		super();
		this.state = state;
		this.country = country;
		this.latestCases = latestTotalCases;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestCases() {
		return latestCases;
	}
	public void setLatestCases(int latestTotalCases) {
		this.latestCases = latestTotalCases;
	}
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestCases=" + latestCases
				+ "]";
	}
	
	
	
}
