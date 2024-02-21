package com.samprakash.search;

import com.samprakash.inputvalidation.InputValidation;

public class ShowView {

	private final ShowViewModel showViewModel;

	public ShowView() {
		this.showViewModel = new ShowViewModel(this);
	}

	public void showListOfAvailableCars() {

		showViewModel.getListOfAvailableCars();

	}

	public void viewStatus(String status) {

		System.out.println(status);
	}

	public void searchByCarNameOrModel(int choice) {

		String filter = InputValidation.getString((choice == 2 ? "Enter a Car Name : " : "Enter a Model Name : ")+"\n"
				+"It Must Be 3 to 15 uppercase or lowercase letters Only","[A-Za-z]{3,15}");
		showViewModel.findCarsByCarNameOrModel(filter, choice);

	}

	public void filtersCarsByAmount(int choice) {
		double amount = InputValidation.getDouble("Enter a Amount : ");
		showViewModel.filtersCarsByAmount(amount, choice);

	}

	public void findCarsBetWeenAmount() {

		double lowAmount = InputValidation.getDouble("Enter a Lowest Amount : ");

		double highAmount = InputValidation.getDouble("Enter a Highest Amount : ");

		showViewModel.findCarsBetWeenAmount(lowAmount, highAmount);

	}

	public void findCarsbetWeenTheYear() {

		int startYear = InputValidation.getInt("Enter a Starting Year : ");
		int endYear = InputValidation.getInt("Enter a Ending Year : ");
		showViewModel.findCarsBetWeenPrices(startYear, endYear);

	}

	public void findCarsOfTheYear(int choice) {

		int year = InputValidation.getInt("Enter a Year : ");
		showViewModel.findCarsOfTheYear(year, choice);
	}

}
