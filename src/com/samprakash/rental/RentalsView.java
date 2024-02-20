package com.samprakash.rental;

import java.sql.ResultSet;
import java.util.Scanner;

import com.samprakash.model.Cars;

public class RentalsView {

	private final RentalsViewModel rentalsViewModel;
	private final Scanner scanner;

	public RentalsView() {

		rentalsViewModel = new RentalsViewModel(this);
		scanner = new Scanner(System.in);
	}

	public void bookingACar(int customerID) {

		System.out.println("Enter a Car Name : ");
		String carName = scanner.nextLine();
		System.out.println("Enter a Car Model : ");
		String carModel = scanner.nextLine();
		System.out.println("Enter a Manufacturing Year : ");
		int year = scanner.nextInt();
		scanner.nextLine();
		Cars car = rentalsViewModel.isAvailableNow(carName, carModel, year);
		if (car != null) {

			System.out.println("Enter a Start Date of Rentals : ");
			String startDate = scanner.nextLine();
			System.out.println("Enter a End Date of Rentals : ");
			String endDate = scanner.nextLine();
			rentalsViewModel.bookACar(startDate, endDate, car, customerID);
		} else {
			showView("There is No car with the Given Data : ");
		}
	}

	void showView(String status) {

		System.out.println(status);

	}

	public void returningACar(int customerID) {
			
		System.out.println("Enter a Return Date : ");
		String returnDate = scanner.nextLine();
		System.out.println("Enter a Rental ID : ");
		int rentalID = scanner.nextInt();
		scanner.nextLine();
		ResultSet resultSet = rentalsViewModel.getCustomerRentalDetails(customerID,rentalID);
		if (rentalsViewModel.isCarAlreadyBookedByThisCustomer(resultSet)) {
			
			rentalsViewModel.changeCarAvailability(rentalID, returnDate, resultSet);

		} else {
			showView("There is No bookings available with the Customer ID : " + customerID);
		}
	}

}
