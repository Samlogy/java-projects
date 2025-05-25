package com.yunusemrenalbant.vehicle_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.sql.*;

@SpringBootApplication
@EnableDiscoveryClient
public class VehicleServiceApplication {
	public static void main(String[] args) {
		createDatabaseIfNotExists("vehicle_service", "root", "123123123", "localhost", 5434);
		SpringApplication.run(VehicleServiceApplication.class, args);
	}

	private static void createDatabaseIfNotExists(String dbName, String user, String password, String host, int port) {
		String url = "jdbc:postgresql://" + host + ":" + port + "/postgres";
		try (Connection conn = DriverManager.getConnection(url, user, password);
			 Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
			if (!rs.next()) {
				stmt.executeUpdate("CREATE DATABASE \"" + dbName + "\"");
				System.out.println("Base de données '" + dbName + "' créée avec succès.");
			} else {
				System.out.println("La base de données '" + dbName + "' existe déjà.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
