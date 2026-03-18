package com.ncba.countryinfo.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing country information from the SOAP FullCountryInfo response.
 * Maps to the tCountryInfo structure from the CountryInfoService WSDL.
 */
@Entity
@Table(name = "country_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CountryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "iso_code", unique = true, nullable = false)
    private String isoCode;

    @Column(nullable = false)
    private String name;

    @Column(name = "capital_city")
    private String capitalCity;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "continent_code")
    private String continentCode;

    @Column(name = "currency_iso_code")
    private String currencyIsoCode;

    @Column(name = "country_flag")
    private String countryFlag;
}
