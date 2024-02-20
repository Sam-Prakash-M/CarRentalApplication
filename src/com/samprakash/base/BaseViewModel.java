package com.samprakash.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.samprakash.model.Cars;
import com.samprakash.model.Customers;
import com.samprakash.repository.CarRentalRepository;

public class BaseViewModel {
	private final BaseView baseView;

	public BaseViewModel(BaseView baseView) {
		this.baseView = baseView;
	}

	public boolean adminVerify(String userName, String passWord) {
		return CarRentalRepository.getInstance().adminVerify(userName, passWord);

	}

	public void addCustomers(Customers customers) {
		CarRentalRepository.getInstance().addCustomer(customers);
		baseView.showStatus("New Customer Added SuccessFully...");

	}

	public int customerVerify(String userName, String passWord) {
		return CarRentalRepository.getInstance().customerVerify(userName, passWord);

	}

	public void addNewCar(Cars carDetails) {

		if (!CarRentalRepository.getInstance().isAvailableNow(carDetails.carID())) {
			CarRentalRepository.getInstance().addNewCar(carDetails);
			baseView.showStatus("New Car Added SuccessFully...");
		} else {
			baseView.showStatus("Car  is Already Presented with this ID : " + carDetails.carID());
		}

	}

	public void removeACar(int carID) {

		if (CarRentalRepository.getInstance().isCarPresentAndNotUsingByAnyCustomer(carID)) {

			baseView.showStatus("Car Deleted SuccessFully...");
		}

	}

	public void showListOfCustomers() {

		ResultSet resultSet = CarRentalRepository.getInstance().showListOfCustomers();

		try {
			if (!resultSet.isBeforeFirst()) {
				baseView.showStatus("No Cars are Currrently Available");
				return;
			}
			showCustomerDetails(resultSet);

		} catch (SQLException e) {
			baseView.showStatus("Sql Exception..." + e.getMessage());
		}
	}

	public void showCustomerDetails(ResultSet resultSet) {
		try {
			baseView.showStatus(
					"+-------------+----------------------+----------------------+----------------------+----------------------+-------------------------+");
			baseView.showStatus(String.format("| %-10s | %-20s | %-20s | %-20s | %-20s | %-23s |", "customer_id",
					"first_name", "last_name", "email", "phone_number", "driving_license_number"));
			baseView.showStatus(
					"+-------------+----------------------+----------------------+----------------------+----------------------+-------------------------+");
			while (resultSet.next()) {
				baseView.showStatus(String.format("| %-11d | %-20s | %-20s | %-20s | %-20s | %-23s |",
						resultSet.getInt("customer_id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"),
						resultSet.getString("phone_number"), resultSet.getString("driving_license_number")));
				baseView.showStatus(
						"+-------------+----------------------+----------------------+----------------------+----------------------+-------------------------+");
			}
			
		} catch (SQLException e) {
			baseView.showStatus("Sql Exceptions : " + e.getMessage());
		}
	}

	public void changeAvailabilityOfCar(int carID, boolean isAvailable) {
		if (CarRentalRepository.getInstance().getAvailability(carID) != isAvailable) {
			CarRentalRepository.getInstance().changeAvailabilityOfCar(carID, isAvailable);
			baseView.showStatus("Availability Changes to : " + (isAvailable ? "Available" : "Not Available"));
		} else {
			baseView.showStatus("Current Status and Given status is Same");
		}

	}

	public void showEntireRentedDetails() {
		ResultSet resultSet = CarRentalRepository.getInstance().getAllRentHistory();

		try {
			if (resultSet.isBeforeFirst()) {
				baseView.showStatus(
						"+-------------------------------------------------------------------------------"
						+ "-----------------------------------------------------------------------------"
						+ "----------------------------------------+");
				baseView.showStatus(String.format("| %80s Rental History %-98s |","",""));
				baseView.showStatus(
						"+-----------------+------------+------------+---------------------------+-------"
						+ "---------------+----------------------+---------------------------+----------"
						+ "-----------------+----------------------+");
				baseView.showStatus(
						String.format("| %-15s | %-10s | %-10s | %-25s | %-20s | %-20s | %-25s | %-25s | %-20s |",
								"customer_id", "Car_ID", "Rental_ID", "Rental_Price/per_Day", "Rental_Start_Date",
								"Rental_End_Date", "Rental_Period_Amount", "Actual_Returned_Date", "Final_SettleMent"));
				baseView.showStatus(
						"+-----------------+------------+------------+---------------------------+-------"
						+ "---------------+----------------------+---------------------------+----------"
						+ "-----------------+----------------------+");

				while (resultSet.next()) {
					baseView.showStatus(String.format(
							"| %-15d | %-10d | %-10d | %-25.2f | %-20s | %-20s | %-25.2f | %-25s | %-20.2f |",
							resultSet.getInt("customer_id"), resultSet.getInt("Car_ID"), resultSet.getInt("Rental_ID"),
							resultSet.getDouble("rental_price"), resultSet.getString("rental_start_date"),
							resultSet.getString("rental_end_date"), resultSet.getDouble("total_amount"),
							resultSet.getString("actual_return_date"), resultSet.getDouble("final_receiced_amount")));
					baseView.showStatus(
							"+-----------------+------------+------------+---------------------------+-------"
							+ "---------------+----------------------+---------------------------+----------"
							+ "-----------------+----------------------+");				
					}
				
			} else {
				baseView.showStatus("There is No History of Bookings..");
			}
		}

		catch (SQLException e) {
			baseView.showStatus("SQl Exception.." + e.getMessage());
		}

	}

	public void noOfCarsCurrenltyUsingByCutomer() {
		ResultSet resultSet = CarRentalRepository.getInstance().getAllRentalDetails();
		try {
			if(resultSet.isBeforeFirst()) {
				baseView.showStatus(
						"+-------------------------------------------------------------------------------"
						+ "------------------------------------------------------------------+");
				baseView.showStatus(String.format("| %57s Rental Details %-70s |","",""));
				baseView.showStatus(
						"+-----------------+------------+------------+---------------------------+-------"
						+ "---------------+----------------------+---------------------------+");
				baseView.showStatus(
						String.format("| %-15s | %-10s | %-10s | %-25s | %-20s | %-20s | %-25s |",
								"customer_id", "Car_ID", "Rental_ID", "Rental_Price/per_Day", "Rental_Start_Date",
								"Rental_End_Date", "Rental_Period_Amount"));
				baseView.showStatus(
						"+-----------------+------------+------------+---------------------------+-------"
						+ "---------------+----------------------+---------------------------+");

				while (resultSet.next()) {
					baseView.showStatus(String.format(
							"| %-15d | %-10d | %-10d | %-25.2f | %-20s | %-20s | %-25.2f |",
							resultSet.getInt("customer_id"), resultSet.getInt("Car_ID"), resultSet.getInt("Rental_ID"),
							resultSet.getDouble("rental_price"), resultSet.getString("rental_start_date"),
							resultSet.getString("rental_end_date"), resultSet.getDouble("total_amount")));
					baseView.showStatus(
							"+-----------------+------------+------------+---------------------------+-------"
							+ "---------------+----------------------+---------------------------+");		
					}
				
			}
			else {
				baseView.showStatus("There is No Cars Bookings Currently By Any Customer..");
			}
		}
		catch(SQLException e) {
			baseView.showStatus("SQl Exception.." + e.getMessage());
		}
		
	}
}
