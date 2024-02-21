package com.samprakash.inputvalidation;

import java.util.Scanner;

public class InputValidation {

	private static final Scanner scanner = new Scanner(System.in);

	private InputValidation() {

	}

	public static int getInt(String input) {

		while (true) {
			System.out.println(input);
			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				scanner.nextLine();
				return choice;
			}
			scanner.nextLine();
			System.out.println("Enter a Valid Input...\nAgain");
		}

	}

	public static double getDouble(String input) {

		while (true) {
			System.out.println(input);
			if (scanner.hasNextDouble()) {
				double choice = scanner.nextDouble();
				scanner.nextLine();
				return choice;
			}
			scanner.nextLine();
			System.out.println("Enter a Valid Input...\nAgain");
		}

	}
	public static boolean getBoolean(String input) {

		while (true) {
			System.out.println(input);
			char ch = scanner.nextLine().toLowerCase().charAt(0);
			if (ch=='y') {
				return true;
			}
			else if(ch =='n') {
				return false;
			}
			System.out.println("Enter a Valid Input...\nAgain");
		}

	}


	public static String getString(String input,String regex) {

		while (true) {
			System.out.println(input);
			String value = scanner.nextLine();
			if(value.matches(regex)) {
				return value;
			}
			System.out.println("Enter a Valid Input...\nAgain");
		}

	}

}
