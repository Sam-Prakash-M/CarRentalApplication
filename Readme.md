## Overview

The Car Rental System is a software application designed to manage the operations of a car rental service. It provides functionalities for both administrators and customers to book, manage, and return rental cars. The system is developed using Java programming language and incorporates object-oriented design principles.

## Features

### Administrator Features
- **User Management**: Administrators can manage user accounts, including adding new customers, verifying customers, and managing customer details.
- **Car Management**: Administrators can add new cars to the system, remove existing cars, and update car availability.
- **Rental Management**: Administrators can view rental history, manage rental bookings, and update rental details.

### Customer Features
- **Car Booking**: Customers can search for available cars based on various criteria such as car name, model, year, and rental price. They can then book a car for a specified rental period.
- **Rental Return**: Customers can return rental cars and view rental details such as rental ID, start date, end date, and total amount.


# Car Rental System Class Diagram

## Classes and their Responsibilities

### BaseView
- Manages user interface and input/output operations.
- Collaborates with `BaseViewModel`, `ShowView`, `RentalsView`, and `InputValidation`.
- Methods:
  - `init()`
  - `adminUI()`
  - `getCarDetails()`
  - `getUserNamePassWord()`
  - `customerUI()`
  - `UserUI(customerID)`
  - `searchByCarRentalPrice()`
  - `searchByCarYear()`
  - `getCustomerDetails()`
  - `showStatus(status)`

### ShowView
- Handles displaying available cars and search results.
- Collaborates with `ShowViewModel` and `InputValidation`.
- Methods:
  - `showListOfAvailableCars()`
  - `viewStatus(status)`
  - `searchByCarNameOrModel(choice)`
  - `filtersCarsByAmount(choice)`
  - `findCarsBetweenAmount()`
  - `findCarsBetweenTheYear()`
  - `findCarsOfTheYear(choice)`

### RentalsView
- Manages rental operations such as booking and returning cars.
- Collaborates with `RentalsViewModel` and `InputValidation`.
- Methods:
  - `bookingACar(customerID)`
  - `returningACar(customerID)`

### BaseViewModel
- Manages business logic and database interactions.
- Collaborates with `BaseView` and `InputValidation`.
- Methods:
  - `adminVerify(userName, passWord)`
  - `addCustomers(customers, userNameAndPassword)`
  - `customerVerify(userName, passWord)`
  - `addNewCar(carDetails)`
  - `removeACar(carID)`
  - `showListOfCustomers()`
  - `showCustomerDetails(resultSet)`
  - `changeAvailabilityOfCar(carID, isAvailable)`
  - `showEntireRentedDetails()`
  - `noOfCarsCurrentlyUsingByCustomer()`

### InputValidation
- Validates user input.
- Used by `BaseView`, `ShowView`, and `RentalsView`.
- Methods:
  - `getInt(input)`
  - `getDouble(input)`
  - `getBoolean(input)`
  - `getString(input, regex)`

### Cars, Customers, Rentals
- Model classes representing car, customer, and rental entities.

## Relationships
- `BaseView` has a one-to-one relationship with `BaseViewModel`, `ShowView`, `RentalsView`, and `InputValidation`.
- `ShowView` has a one-to-one relationship with `ShowViewModel`.
- `RentalsView` has a one-to-one relationship with `RentalsViewModel`.
- `BaseViewModel` has a one-to-many relationship with `Cars`, `Customers`, and `Rentals`.

# Class Diagram for Car Rental Management System

## Class Descriptions

### CarRentalRepository
- Manages database operations related to car rental functionality.
- Methods:
  - `getInstance()`
  - `getListOfAvailableCars()`
  - `listOfCarsBetweenTheseAmounts(lowAmount, highAmount)`
  - `listOfCarsFilteredByAmounts(amount, choice)`
  - `listOfCarsByCarNameOrModel(filter, choice)`
  - `listOfCarsFilteredByYears(year, choice)`
  - `listOfCarsBetweenTheseYears(startYear, endYear)`
  - `isAvailableNow(carName, carModel, year)`
  - `getTotalDays(startDate, endDate)`
  - `bookACar(startDate, endDate, car, customerID)`
  - `changeAvailabilityOfCar(carID, availability)`
  - `getRentalDetails(customerID, rentalID)`
  - `updateRentalsClosingDetails(resultSet, actualReturnDate, balance)`
  - `adminVerify(userName, passWord)`
  - `addCustomer(customers, userNameAndPassword)`
  - `customerVerify(userName, passWord)`
  - `addNewCar(carDetails)`
  - `isCarPresentAndNotUsingByAnyCustomer(carID)`
  - `showListOfCustomers()`
  - `isAvailableNow(carID)`
  - `getAvailability(carID)`
  - `getAllRentHistory()`
  - `getAllRentalDetails()`

### CarRentalService
- Provides services related to car rental operations.
- Collaborates with `CarRentalRepository`.

### CarRentalAdminPanel
- Represents the administrative panel for managing car rental operations.
- Collaborates with `CarRentalRepository`.

### CarRentalCustomerPanel
- Represents the customer panel for interacting with car rental services.
- Collaborates with `CarRentalRepository`.

### Working Video
- 