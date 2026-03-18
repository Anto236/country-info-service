package com.ncba.countryinfo.service;

import com.ncba.countryinfo.client.CountryInfoSoapClient;
import com.ncba.countryinfo.dto.CountryFullInfoResponse;
import com.ncba.countryinfo.dto.CountryInfoResponse;
import com.ncba.countryinfo.dto.CountryNameResponse;
import com.ncba.countryinfo.dto.CountryUpdateRequest;
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
     * Receives country name, normalizes it, fetches ISO code and full info from SOAP, persists to database.
     * Flow: name → CountryISOCode → FullCountryInfo → save
     */
    public CountryNameResponse processCountryName(String name) {
        String normalizedName = normalizeCountryName(name);
        String isoCode = soapClient.getCountryIsoCode(normalizedName);
        if (isoCode == null || isoCode.isBlank()) {
            return new CountryNameResponse(normalizedName, "", null);
        }

        CountryInfo fullInfo = soapClient.getFullCountryInfo(isoCode);
        if (fullInfo == null) {
            return new CountryNameResponse(normalizedName, isoCode, null);
        }

        CountryInfo saved = saveOrUpdate(fullInfo);
        log.info("Saved country {} (id={}) to database", saved.getName(), saved.getId());
        return new CountryNameResponse(normalizedName, isoCode, saved.getId());
    }

    /**
     * Upserts country info: updates existing record by isoCode, or inserts new.
     */
    private CountryInfo saveOrUpdate(CountryInfo countryInfo) {
        return countryInfoRepository.findByIsoCode(countryInfo.getIsoCode())
                .map(existing -> {
                    existing.setName(countryInfo.getName());
                    existing.setCapitalCity(countryInfo.getCapitalCity());
                    existing.setPhoneCode(countryInfo.getPhoneCode());
                    existing.setContinentCode(countryInfo.getContinentCode());
                    existing.setCurrencyIsoCode(countryInfo.getCurrencyIsoCode());
                    existing.setCountryFlag(countryInfo.getCountryFlag());
                    return countryInfoRepository.save(existing);
                })
                .orElseGet(() -> countryInfoRepository.save(countryInfo));
    }

    /**
     * Fetches full country information from SOAP FullCountryInfo by ISO code.
     *
     * @param isoCode ISO country code (e.g. KE, TZ)
     * @return CountryFullInfoResponse with name, capital, currency, etc.
     */
    public CountryFullInfoResponse getFullCountryInfo(String isoCode) {
        var entity = soapClient.getFullCountryInfo(isoCode);
        if (entity == null) {
            return null;
        }
        return CountryFullInfoResponse.fromEntity(entity);
    }

    /**
     * Finds all country info records.
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
     * Updates country info by ID.
     *
     * @return updated CountryInfo, or empty if not found
     */
    public Optional<CountryInfo> updateById(Long id, CountryUpdateRequest request) {
        return countryInfoRepository.findById(id)
                .map(existing -> {
                    existing.setName(request.name());
                    existing.setCapitalCity(nullToEmpty(request.capitalCity()));
                    existing.setPhoneCode(nullToEmpty(request.phoneCode()));
                    existing.setContinentCode(nullToEmpty(request.continentCode()));
                    existing.setCurrencyIsoCode(nullToEmpty(request.currencyIsoCode()));
                    existing.setCountryFlag(nullToEmpty(request.countryFlag()));
                    return countryInfoRepository.save(existing);
                });
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
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
