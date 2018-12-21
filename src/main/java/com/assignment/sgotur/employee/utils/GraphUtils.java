package com.assignment.sgotur.employee.utils;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphUtils {

	private String neo4jdatabaseurl = "bolt://127.0.0.1:7687";
	private String user = "neo4j";
	private String password = "neo4j";

	public Driver getDriver() {
		Driver driver = null;

		try {
			driver = GraphDatabase.driver(neo4jdatabaseurl, AuthTokens.basic(user, password));
		} catch (Exception ex) {
			System.out.println("Connection exception " + ex);
		}
		return driver;
	}

}
