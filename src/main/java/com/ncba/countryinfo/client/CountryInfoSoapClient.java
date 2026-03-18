package com.ncba.countryinfo.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * SOAP client for the CountryInfoService.
 * Calls CountryISOCode and FullCountryInfo operations.
 */
@Slf4j
@Component
public class CountryInfoSoapClient {

    private static final String NAMESPACE = "http://www.oorsprong.org/websamples.countryinfo";
    private static final String COUNTRY_ISO_CODE_REQUEST = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="%s">
              <soap:Body>
                <web:CountryISOCode>
                  <web:sCountryName>%s</web:sCountryName>
                </web:CountryISOCode>
              </soap:Body>
            </soap:Envelope>
            """;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String soapEndpointUrl;

    public CountryInfoSoapClient(@Value("${countryinfo.soap.url:http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso}") String soapEndpointUrl) {
        this.soapEndpointUrl = soapEndpointUrl;
    }

    /**
     * Fetches the ISO country code for a given country name.
     *
     * @param countryName country name (e.g. Tanzania)
     * @return ISO code (e.g. TZ) or empty if not found
     */
    public String getCountryIsoCode(String countryName) {
        try {
            log.info("Fetching ISO code for country: {}", countryName);
            String escapedName = escapeXml(countryName);
            String soapRequest = COUNTRY_ISO_CODE_REQUEST.formatted(NAMESPACE, escapedName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_XML);

            HttpEntity<String> entity = new HttpEntity<>(soapRequest, headers);
            String response = restTemplate.postForObject(soapEndpointUrl, entity, String.class);

            String isoCode = extractIsoCode(response);
            log.info("ISO code for {}: {}", countryName, isoCode);
            return isoCode != null ? isoCode : "";
        } catch (Exception e) {
            log.error("Failed to fetch ISO code for {}: {}", countryName, e.getMessage());
            throw new SoapClientException("Failed to fetch country ISO code: " + e.getMessage(), e);
        }
    }

    private String extractIsoCode(String soapResponse) {
        if (soapResponse == null) return null;
        int start = soapResponse.indexOf("CountryISOCodeResult");
        if (start == -1) return null;
        start = soapResponse.indexOf(">", start) + 1;
        int end = soapResponse.indexOf("<", start);
        return end > start ? soapResponse.substring(start, end).trim() : null;
    }

    private String escapeXml(String value) {
        if (value == null) return "";
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
