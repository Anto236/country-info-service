# SoapUI Setup for CountryInfoService

This guide walks through setting up SoapUI to test the SOAP web service used by the country-info-service.

## Prerequisites

- [SoapUI](https://www.soapui.org/downloads/soapui/) (or ReadyAPI) installed
- WSDL URL: `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL`

## Step 1: Install SoapUI

1. Download SoapUI from [SmartBear](https://www.soapui.org/downloads/soapui/)
2. Install using the installer for your OS
3. Launch SoapUI

## Step 2: Import the WSDL Project

1. **File → New SOAP Project**
2. **Project Name:** `CountryInfoService`
3. **Initial WSDL:** `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL`
4. Click **OK**

## Step 3: Verify Operations

The service exposes several operations. For this case study, focus on:

### CountryISOCode
- **Input:** `sCountryName` (e.g. "Kenya", "Tanzania")
- **Output:** `countryIsoCodeResult` (2-letter ISO code, e.g. "KE", "TZ")

### FullCountryInfo
- **Input:** `sCountryISOCode` (e.g. "KE", "TZ")
- **Output:** `FullCountryInfoResult` with:
  - `sISOCode`, `sName`, `sCapitalCity`, `sPhoneCode`
  - `sContinentCode`, `sCurrencyISOCode`, `sCountryFlag`
  - `Languages` (array)

## Step 4: Test CountryISOCode

1. Expand **CountryISOCode** under the service
2. Double-click **Request 1**
3. Set the request body:
   ```xml
   <sCountryName>Kenya</sCountryName>
   ```
4. Click the **Run** (green play) button
5. Check the response for `CountryISOCodeResult`: should be `KE`

## Step 5: Test FullCountryInfo

1. Expand **FullCountryInfo**
2. Double-click **Request 1**
3. Set the request body:
   ```xml
   <sCountryISOCode>KE</sCountryISOCode>
   ```
4. Click **Run**
5. Response should include full country details (Nairobi, 254, KES, etc.)

## WSDL Reference

| Item | Value |
|------|-------|
| WSDL URL | `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL` |
| SOAP Endpoint | `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso` |
| Namespace | `http://www.oorsprong.org/websamples.countryinfo` |
