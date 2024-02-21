package com.samprakash.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.samprakash.repository.CarRentalRepository;

public class ShowViewModel {

	private final ShowView showView;

	public ShowViewModel(ShowView showView) {
		this.showView = showView;
	}

	public void getListOfAvailableCars() {

		ResultSet resultSet = CarRentalRepository.getInstance().getlistOfAvailbleCars();
		try {
			showView.viewStatus("+--------------------------------------------------------------------------------------------------------------+");
			showView.viewStatus(String.format("%-50s Car's Details %47s ","|","|"));
			showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+------------------+");
			showView.viewStatus(String.format("| %-10s | %-30s | %-30s | %-10s | %-16s |", "Car_ID", "Make", "Model",
					"Year", "RentalPrice"));
			showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+------------------+");
			while (resultSet.next()) {
				showView.viewStatus(String.format("| %-10s | %-30s | %-30s | %-10d | %-16.2f |",
						resultSet.getInt("car_id"), resultSet.getString("make"), resultSet.getString("Model"),
						resultSet.getInt("Year"), resultSet.getDouble("Rental_Price")));
				showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+------------------+");
			}
			
		} catch (SQLException e) {
			showView.viewStatus("Sql Exceptions : " + e.getMessage());
		}

	}

	public void findCarsBetWeenAmount(double lowAmount, double highAmount) {
		ResultSet resultSet = CarRentalRepository.getInstance().listOfCarsBetweenTheseAmounts(lowAmount, highAmount);
		showCarDetails(resultSet);
	}


	public void filtersCarsByAmount(double amount,int choice) {
		ResultSet resultSet = CarRentalRepository.getInstance().listOfCarsFilteredByAmounts(amount,choice);
		showCarDetails(resultSet);
	}

	public void findCarsByCarNameOrModel(String filter,int choice) {
		ResultSet resultSet = CarRentalRepository.getInstance().listOfCarsByCarNameOrModel(filter,choice);
		showCarDetails(resultSet);
		
		
	}
	public void showCarDetails(ResultSet resultSet) {
		try {
			showView.viewStatus("+-----------------------------------------------------------------------------------------------------------------------------------+");
			showView.viewStatus(String.format("%-53s Car's Details %65s ","|","|"));
			showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+-----------------+---------------------+");

			showView.viewStatus(String.format("| %-10s | %-30s | %-30s | %-10s | %-15s | %-19s |", "Car_ID", "Make",
					"Model", "Year", "RentalPrice", "IS Available"));
			showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+-----------------+---------------------+");
			while (resultSet.next()) {
				showView.viewStatus(String.format("| %-10s | %-30s | %-30s | %-10s | %-15.2f | %-19s |",
						resultSet.getInt("car_id"), resultSet.getString("make"), resultSet.getString("Model"),
						resultSet.getInt("Year"), resultSet.getDouble("Rental_Price"),
						resultSet.getBoolean("is_Available") ? "Yes" : "No"));
				showView.viewStatus("+------------+--------------------------------+--------------------------------+------------+-----------------+---------------------+");
			}
			
		} catch (SQLException e) {
			showView.viewStatus("Sql Exceptions : " + e.getMessage());
		}
	}

	public void findCarsOfTheYear(int year,int choice) {
		ResultSet resultSet = CarRentalRepository.getInstance().listOfCarsFilteredByYears(year,choice);
		showCarDetails(resultSet);
		
		
	}

	public void findCarsBetWeenPrices(int startYear, int endYear) {
		ResultSet resultSet = CarRentalRepository.getInstance().listOfCarsBetweenTheseYears(startYear, endYear);
		showCarDetails(resultSet);
		
	}


}
