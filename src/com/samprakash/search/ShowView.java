package com.samprakash.search;

import java.util.Scanner;

public class ShowView {

	private final Scanner scanner;
	private final ShowViewModel showViewModel;

	public ShowView() {
		this.scanner = new Scanner(System.in);
		this.showViewModel = new ShowViewModel(this);
	}

	public void showListOfAvailableCars() {

		showViewModel.getListOfAvailableCars();

	}

	public void viewStatus(String status) {

		System.out.println(status);
	}

	public void searchByCarNameOrModel(int choice) {
			
		System.out.println(choice == 2 ? "Enter a Car Name : ": "Enter a Model Name : ");
		String filter = scanner.nextLine();
		showViewModel.findCarsByCarNameOrModel(filter,choice);
		
	}

	

	public void filtersCarsByAmount(int choice) {
		System.out.println("Enter a Amount : ");
		float amount = scanner.nextFloat();
		scanner.nextLine();
		showViewModel.filtersCarsByAmount(amount,choice);

	}

	

	public void findCarsBetWeenAmount() {
		System.out.println("Enter a Lowest Amount : ");
		float lowAmount = scanner.nextFloat();
		scanner.nextLine();
		System.out.println("Enter a Highest Amount : ");
		float highAmount = scanner.nextFloat();
		scanner.nextLine();
		showViewModel.findCarsBetWeenAmount(lowAmount, highAmount);

	}



	public void findCarsbetWeenTheYear() {
		
		System.out.println("Enter a Starting Year : ");
		int startYear = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter a Ending Year : ");
		int endYear = scanner.nextInt();
		scanner.nextLine();
		showViewModel.findCarsBetWeenPrices(startYear, endYear);
		
	}

	public void findCarsOfTheYear(int choice) {
		
		System.out.println("Enter a Year : ");
		int year = scanner.nextInt();
		scanner.nextLine();
		showViewModel.findCarsOfTheYear(year,choice);
	}

}
