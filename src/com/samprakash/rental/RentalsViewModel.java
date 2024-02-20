package com.samprakash.rental;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.samprakash.model.Cars;
import com.samprakash.repository.CarRentalRepository;

public class RentalsViewModel {

	private final RentalsView rentalsView;

	public RentalsViewModel(RentalsView rentalsView) {

		this.rentalsView = rentalsView;
	}

	public Cars isAvailableNow(String carName, String carModel, int year) {

		ResultSet resultSet = CarRentalRepository.getInstance().isAvailableNow(carName, carModel, year);

		int carID = -1;
		double rentalPrice = -1;
		try {
			if (resultSet.next()) {
				carID = resultSet.getInt("car_id");
				rentalPrice = resultSet.getDouble("rental_price");
			} else {
				return null;
			}

		} catch (SQLException e) {

			rentalsView.showView("Sql Exceptions : " + e.getMessage());
		}

		return new Cars(carID, rentalPrice);
	}

	public void bookACar(String startDate, String endDate, Cars car, int customerID) {

		CarRentalRepository.getInstance().bookACar(startDate, endDate, car, customerID);
	}

	public ResultSet getCustomerRentalDetails(int customerID,int rentalID) {

		return CarRentalRepository.getInstance().getRentalDetails(customerID,rentalID);
	}

	public double isAnyAmountBalance(String returnedDate, ResultSet resultSet) {

		try {
			String startDate = resultSet.getString("rental_start_date");
			String endDate = resultSet.getString("rental_end_date");
			double totalAmount = resultSet.getDouble("total_amount");

			int payedDays = CarRentalRepository.getInstance().getTotalDays(startDate, endDate);
			int carUsedDays = CarRentalRepository.getInstance().getTotalDays(startDate, returnedDate);

			double oneDayRent = totalAmount / payedDays;
			if (payedDays != carUsedDays) {

				if (payedDays > carUsedDays) {
					rentalsView.showView("Take Your Balance : " + (payedDays - carUsedDays) * oneDayRent);
				}
				return (carUsedDays - payedDays) * oneDayRent;
			}

		} catch (SQLException e) {
			rentalsView.showView("Sql Exceptions : " + e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}

	public void changeCarAvailability(int rentalID, String returnDate, ResultSet resultSet) {

		try {

			resultSet.next();

			CarRentalRepository.getInstance().changeAvailabilityOfCar(resultSet.getInt("car_id"), true);

			double balance = isAnyAmountBalance(returnDate, resultSet);
			if (balance > 0.0) {
				rentalsView.showView("You are Using Extra Days So, You need to Pay"
						+ " \nextra rent for Each Days and also given fine amount of 1000 Rs\n Total Fine is :  "
						+ (balance + 1000));
			}
			CarRentalRepository.getInstance().updateRentalsClosingDetails(resultSet, returnDate, balance);

		} catch (

		SQLException e) {
			rentalsView.showView("Sql Exceptions : " + e.getMessage());
		}

	}

	public boolean isCarAlreadyBookedByThisCustomer(ResultSet resultSet) {

		try {

			return resultSet.isBeforeFirst();
		} catch (SQLException e) {
			rentalsView.showView("Sql Exceptions : " + e.getMessage());
		}
		return false;
	}

}
