-- Run this in SSMS to create countrydb
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'countrydb')
BEGIN
    CREATE DATABASE countrydb;
END
GO
