package com.samprakash.rental;

import java.sql.ResultSet;

import com.samprakash.inputvalidation.InputValidation;
import com.samprakash.model.Cars;

public class RentalsView {

	private final RentalsViewModel rentalsViewModel;
	

	public RentalsView() {

		rentalsViewModel = new RentalsViewModel(this);
	}

	public void bookingACar(int customerID) {


		String carName = InputValidation.getString("Enter a Car Name : \n"
				+ "It Must Be 3 to 15 uppercase or lowercase letters Only","[A-Za-z]{3,15}");
		String carModel = InputValidation.getString("Enter a Car Model : \n"
				+ "It Must Be 3 to 15 uppercase or lowercase letters Only","[A-Za-z]{3,15}");
		int year = InputValidation.getInt("Enter a Manufacturing Year : ");
		Cars car = rentalsViewModel.isAvailableNow(carName, carModel, year);
		if (car != null) {
			String startDate = InputValidation.getString("Enter a Start Date of Rentals : \n"
					+ "The year consists of exactly four digits.\r\n"
					+ "The month is between 01 and 12.\r\n"
					+ "The day is valid for the corresponding month (taking into account leap years).","^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
			
			String endDate =  InputValidation.getString("Enter a End Date of Rentals : \n"
					+ "The year consists of exactly four digits.\r\n"
					+ "The month is between 01 and 12.\r\n"
					+ "The day is valid for the corresponding month (taking into account leap years).","^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
			rentalsViewModel.bookACar(startDate, endDate, car, customerID);
		} else {
			showView("There is No car with the Given Data : ");
		}
	}

	void showView(String status) {

		System.out.println(status);

	}

	public void returningACar(int customerID) {
		
		String returnDate = InputValidation.getString("Enter a Return Date : \n"
				+ "The year consists of exactly four digits.\r\n"
				+ "The month is between 01 and 12.\r\n"
				+ "The day is valid for the corresponding month (taking into account leap years).","^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
		int rentalID = InputValidation.getInt("Enter a Rental ID : ");
		ResultSet resultSet = rentalsViewModel.getCustomerRentalDetails(customerID,rentalID);
		if (rentalsViewModel.isCarAlreadyBookedByThisCustomer(resultSet)) {
			
			rentalsViewModel.changeCarAvailability(rentalID, returnDate, resultSet);

		} else {
			showView("There is No bookings available with the Customer ID : " + customerID);
		}
	}

}
