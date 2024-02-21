package com.samprakash.base;

import com.samprakash.inputvalidation.InputValidation;
import com.samprakash.model.Cars;
import com.samprakash.model.Customers;
import com.samprakash.rental.RentalsView;
import com.samprakash.search.ShowView;

public class BaseView {
	private final BaseViewModel baseViewModel;
	private final ShowView showView;
	private final RentalsView rentalsView;

	public BaseView() {
		baseViewModel = new BaseViewModel(this);
		showView = new ShowView();
		rentalsView = new RentalsView();
	}

	public void init() {

		int choice;
		do {
			System.out.println("+--------------------------------+");
			System.out.printf("%s %-30s %s\n", "|", "1) Admin", "|");
			System.out.printf("%s %-30s %s\n", "|", "2) Customer", "|");
			System.out.printf("%s %-30s %s\n", "|", "3) Exit", "|");
			System.out.println("+--------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");
			switch (choice) {

			case 1 -> {
				String[] inputs = getUserNamePassWord();
				if (baseViewModel.adminVerify(inputs[0], inputs[1])) {
					adminUI();
				}
			}
			case 2 -> {
				customerUI();
			}
			case 3 -> {

			}
			default -> {
				System.out.println("Enter A valid Input from 1 to 3");
			}
			}
		} while (choice != 3);

	}

	private void adminUI() {

		int choice;
		do {
			System.out.println("+----------------------------------------------------+");
			System.out.printf("%s %-50s %s\n", "|", "1) Add a New Car", "|");
			System.out.printf("%s %-50s %s\n", "|", "2) Remove a Car", "|");
			System.out.printf("%s %-50s %s\n", "|", "3) Change Availability Of Car", "|");
			System.out.printf("%s %-50s %s\n", "|", "4) Show List of Customers Present", "|");
			System.out.printf("%s %-50s %s\n", "|", "5) Show No of Cars Currently Using By Customers", "|");
			System.out.printf("%s %-50s %s\n", "|", "6) Show Entire Rent Details", "|");
			System.out.printf("%s %-50s %s\n", "|", "7) LogOut", "|");
			System.out.println("+----------------------------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");
			switch (choice) {

			case 1 -> {

				baseViewModel.addNewCar(getCarDetails());

			}
			case 2, 3 -> {
				int carID = InputValidation.getInt("Enter a Car ID : ");
				if (choice == 2) {
					baseViewModel.removeACar(carID);
				} else {
					boolean isAvailable = InputValidation.getBoolean("Enter y for true n for false");
					baseViewModel.changeAvailabilityOfCar(carID, isAvailable);
				}

			}

			case 4 -> {
				baseViewModel.showListOfCustomers();
			}
			case 5 -> {
				baseViewModel.noOfCarsCurrenltyUsingByCutomer();
			}
			case 6 -> {
				baseViewModel.showEntireRentedDetails();
			}
			case 7 -> {

			}
			default -> {
				
				System.out.print("Enter a Number BetWeen 1 to 7...");
			}

			}
		} while (choice != 7);

	}

	private Cars getCarDetails() {

		int carID = InputValidation.getInt("Enter a Car ID : ");
		String carName = InputValidation.getString("Enter a Car Name : ","[A-Za-z]{3,15}");
		String carModel = InputValidation.getString("Enter a Car Model : ","[A-Za-z]{3,15}");
		int year = InputValidation.getInt("Enter a  Car Manufacturing Year : ");
		double rentalPrice = InputValidation.getDouble("Enter a Rental Price : ");
		boolean isAvailable = InputValidation.getBoolean("Enter Car Availability y for true n for false :  ");
		return new Cars(carID, year, carName, carModel, rentalPrice, isAvailable);
	}

	private String[] getUserNamePassWord() {
		String userName = InputValidation.getString("Enter a UserName : ","^[a-zA-Z][a-zA-Z0-9@#\\-_]{2,14}$");
		String passWord = InputValidation.getString("Enter a Password : ","^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$");
		return new String[] { userName, passWord };
	}

	private void customerUI() {
		int choice;
		do {
			System.out.println("+--------------------------------+");
			System.out.printf("%s %-30s %s\n", "|", "1) Sign up", "|");
			System.out.printf("%s %-30s %s\n", "|", "2) Sign in", "|");
			System.out.printf("%s %-30s %s\n", "|", "3) Exit", "|");
			System.out.println("+--------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");
			switch (choice) {
			case 1 -> {
				Customers customers = getCustomerDetails();
				String[] inputs = getUserNamePassWord();

				baseViewModel.addCustomers(customers, inputs);

			}
			case 2 -> {
				String[] inputs = getUserNamePassWord();
				int customerID = baseViewModel.customerVerify(inputs[0], inputs[1]);
				if (customerID != -1) {
					UserUI(customerID);
				}

			}
			case 3 -> {

			}
			default -> {
				System.out.println("Enter Valid Input from 1 to 3");
			}
			}

		} while (choice != 3);
	}

	private void UserUI(int customerID) {

		int choice;
		do {
			System.out.println("+--------------------------------+");
			System.out.printf("%s %-30s %s\n", "|", "1) Show List Of Available Cars", "|");
			System.out.printf("%s %-30s %s\n", "|", "2) Search Car By Name", "|");
			System.out.printf("%s %-30s %s\n", "|", "3) Search Car By Model", "|");
			System.out.printf("%s %-30s %s\n", "|", "4) Search Car By Year", "|");
			System.out.printf("%s %-30s %s\n", "|", "5) Search Car By RentalPrice", "|");
			System.out.printf("%s %-30s %s\n", "|", "6) Booking a Car", "|");
			System.out.printf("%s %-30s %s\n", "|", "7) Returning a Car", "|");
			System.out.printf("%s %-30s %s\n", "|", "8) Log Out", "|");
			System.out.println("+--------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");
			switch (choice) {

			case 1 -> {
				showView.showListOfAvailableCars();
			}
			case 2, 3 -> {
				showView.searchByCarNameOrModel(choice);
			}

			case 4 -> {
				searchByCarYear();
			}
			case 5 -> {
				searchByCarRentalPrice();
			}
			case 6 -> {
				rentalsView.bookingACar(customerID);
			}
			case 7 -> {
				rentalsView.returningACar(customerID);
			}
			case 8 -> {

			}
			default -> {
				showStatus("Enter a Number BetWeen 1 to 8");
			}
			}
		} while (choice != 8);

	}

	private void searchByCarRentalPrice() {
		int choice;
		do {
			System.out.println("+------------------------------------------+");
			System.out.printf("%s %-40s %s\n", "|", "1) Find Cars Made Below a Certain Amout", "|");
			System.out.printf("%s %-40s %s\n", "|", "2) Find Cars Made Above a Certain Amount", "|");
			System.out.printf("%s %-40s %s\n", "|", "3) Find Cars Made Between Two Amounts", "|");
			System.out.printf("%s %-40s %s\n", "|", "4) Exit", "|");
			System.out.println("+------------------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");

			switch (choice) {

			case 1, 2 -> {

				showView.filtersCarsByAmount(choice);

			}

			case 3 -> {

				showView.findCarsBetWeenAmount();

			}
			case 4 -> {

			}
			default -> {
				showStatus("Enter a Number BetWeen 1 to 4");
			}
			}
		} while (choice != 4);

	}

	private void searchByCarYear() {

		int choice;
		do {
			System.out.println("+------------------------------------------+");
			System.out.printf("%s %-40s %s\n", "|", "1) Find Cars Made Before a Certain Year", "|");
			System.out.printf("%s %-40s %s\n", "|", "2) Find Cars Made After a Certain Year", "|");
			System.out.printf("%s %-40s %s\n", "|", "3) Find Cars Made Between Two Years", "|");
			System.out.printf("%s %-40s %s\n", "|", "4) Exit", "|");
			System.out.println("+------------------------------------------+");
			choice = InputValidation.getInt("Enter Any One of the Given Option");
			switch (choice) {

			case 1, 2 -> {
				showView.findCarsOfTheYear(choice);

			}

			case 3 -> {
				showView.findCarsbetWeenTheYear();
			}
			case 4 -> {

			}
			default -> {
				showStatus("Enter a Number BetWeen 1 to 4");
			}
			}

		} while (choice != 4);

	}

	private Customers getCustomerDetails() {

		String firstName = InputValidation.getString("Enter a First Name : ","[A-Za-z]{3,15}");
		String lastName = InputValidation.getString("Enter a Last Name : ","[A-Za-z]{3,15}");
		String emailID = InputValidation.getString("Enter a Email-ID : ","^[a-z][a-zA-Z0-9_+&*-]{2,14}@gmail\\.com$");
		String phoneNumber = InputValidation.getString("Enter a Phone Number  : ","(\\+91)?[6-9][0-9]{9}|(\\+91)[6-9][0-9]{9}");
		String drivingLicence = InputValidation.getString("Enter a Driving Licence  : ","^[A-Z][A-Z0-9]{9}$");
		return new Customers(firstName, lastName, emailID, phoneNumber, drivingLicence);
	}

	public void showStatus(String status) {
		System.out.println(status);
	}
}
