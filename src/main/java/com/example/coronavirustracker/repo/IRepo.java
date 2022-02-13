package com.example.coronavirustracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coronavirustracker.entities.LocationStats;

public interface IRepo extends JpaRepository<LocationStats, Integer>{

}
