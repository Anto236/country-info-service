package com.ncba.countryinfo.repository;

import com.ncba.countryinfo.model.entity.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for CountryInfo entity persistence.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface CountryInfoRepository extends JpaRepository<CountryInfo, Long> {

    Optional<CountryInfo> findByIsoCode(String isoCode);

    boolean existsByIsoCode(String isoCode);
}
