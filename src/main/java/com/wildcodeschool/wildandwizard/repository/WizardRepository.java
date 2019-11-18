package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.Wizard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WizardRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public List<Wizard> findAll() {

        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM wizard;"
            );
            ResultSet resultSet = statement.executeQuery();

            List<Wizard> wizards = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthday = resultSet.getDate("birthday");
                String birthPlace = resultSet.getString("birth_place");
                String biography = resultSet.getString("biography");
                boolean muggle = resultSet.getBoolean("is_muggle");
                wizards.add(new Wizard(id, firstName, lastName, birthday, birthPlace, biography, muggle));
            }
            return wizards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Wizard findById(Long id) {

        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM wizard WHERE id = ?;"
            );
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthday = resultSet.getDate("birthday");
                String birthPlace = resultSet.getString("birth_place");
                String biography = resultSet.getString("biography");
                boolean muggle = resultSet.getBoolean("is_muggle");
                return new Wizard(id, firstName, lastName, birthday, birthPlace, biography, muggle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wizard> findByLastName(String lastName) {

        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM wizard WHERE last_name LIKE ?;"
            );
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();

            List<Wizard> wizards = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                Date birthday = resultSet.getDate("birthday");
                String birthPlace = resultSet.getString("birth_place");
                String biography = resultSet.getString("biography");
                boolean muggle = resultSet.getBoolean("is_muggle");
                wizards.add(new Wizard(id, firstName, lastName, birthday, birthPlace, biography, muggle));
            }
            return wizards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Wizard save(String firstName, String lastName, Date birthday,
            String birthPlace, String biography, boolean muggle) {

		try {
		 Connection connection = DriverManager.getConnection(
		         DB_URL, DB_USER, DB_PASSWORD
		 );
		 	PreparedStatement statement = connection.prepareStatement(
		         "INSERT INTO wizard (first_name, last_name, birthday, birth_place, biography, is_muggle) VALUES (?, ?, ?, ?, ?, ?)",
		    Statement.RETURN_GENERATED_KEYS
		 );
		 statement.setString(1, firstName);
		 statement.setString(2, lastName);
		 statement.setDate(3, birthday);
		 statement.setString(4, birthPlace);
		 statement.setString(5, biography);
		 statement.setBoolean(6, muggle);
		
		 if (statement.executeUpdate() != 1) {
		     throw new SQLException("failed to insert data");
		 }
		
		 ResultSet generatedKeys = statement.getGeneratedKeys();
		
		 if (generatedKeys.next()) {
		     Long id = generatedKeys.getLong(1);
		     return new Wizard(id, firstName, lastName, birthday,
		             birthPlace, biography, muggle);
		 } else {
		     throw new SQLException("failed to get inserted id");
		 }
		} catch (SQLException e) {
		 e.printStackTrace();
		}
		return null;
		}
}
