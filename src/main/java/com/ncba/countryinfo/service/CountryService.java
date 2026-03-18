package com.ncba.countryinfo.service;

import com.ncba.countryinfo.client.CountryInfoSoapClient;
import com.ncba.countryinfo.dto.CountryNameResponse;
import com.ncba.countryinfo.model.entity.CountryInfo;
import com.ncba.countryinfo.repository.CountryInfoRepository;
import com.ncba.countryinfo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for country-related business logic.
 */
@Slf4j
@Service
public class CountryService {

    private final CountryInfoRepository countryInfoRepository;
    private final CountryInfoSoapClient soapClient;

    public CountryService(CountryInfoRepository countryInfoRepository, CountryInfoSoapClient soapClient) {
        this.countryInfoRepository = countryInfoRepository;
        this.soapClient = soapClient;
    }

    /**
     * Normalizes a country name to sentence case.
     * e.g. "kenya" -> "Kenya", "united states" -> "United States"
     */
    public String normalizeCountryName(String name) {
        log.info("Normalizing country name: {}", name);
        String normalized = StringUtils.toSentenceCase(name);
        log.info("Normalized to: {}", normalized);
        return normalized;
    }

    /**
     * Receives country name, normalizes it, fetches ISO code from SOAP API.
     */
    public CountryNameResponse processCountryName(String name) {
        String normalizedName = normalizeCountryName(name);
        String isoCode = soapClient.getCountryIsoCode(normalizedName);
        return new CountryNameResponse(normalizedName, isoCode);
    }

    /**
     * Finds all country info records (for CRUD - Phase 7).
     */
    public List<CountryInfo> findAll() {
        return countryInfoRepository.findAll();
    }

    /**
     * Finds country info by ID.
     */
    public Optional<CountryInfo> findById(Long id) {
        return countryInfoRepository.findById(id);
    }

    /**
     * Saves or updates country info (for persistence - Phase 6).
     */
    public CountryInfo save(CountryInfo countryInfo) {
        return countryInfoRepository.save(countryInfo);
    }

    /**
     * Deletes country info by ID.
     */
    public void deleteById(Long id) {
        countryInfoRepository.deleteById(id);
    }
}
