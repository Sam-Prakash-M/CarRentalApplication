package com.samprakash.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.samprakash.model.Cars;
import com.samprakash.model.Customers;

public class CarRentalRepository {

	private static final CarRentalRepository SINGLE_OBJECT = new CarRentalRepository();
	private final Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	private CarRentalRepository() {
		Connection tempConnection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/carrentalmanagementsystem";
			String userName = "root";
			String passWord = "SamPrakash@2295";

			tempConnection = DriverManager.getConnection(url, userName, passWord);
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception: " + e.getMessage());
		} catch (SQLException s) {
			System.out.println("SQL Connection Error: " + s.getMessage());
		} finally {
			connection = tempConnection;
		}

	}

	public static CarRentalRepository getInstance() {
		return SINGLE_OBJECT;
	}

	public ResultSet getlistOfAvailbleCars() {

		String query = "SELECT * FROM Cars WHERE is_Available = ? ORDER BY car_ID";
		 preparedStatement = null;
		 resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;

	}

	public ResultSet listOfCarsBetweenTheseAmounts(float lowAmount, float highAmount) {
		String query = "SELECT * FROM Cars WHERE rental_price BETWEEN ? AND ? ORDER BY rental_price,car_ID";
	     preparedStatement = null;
		 resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setFloat(1, lowAmount);
			preparedStatement.setFloat(2, highAmount);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;

	}

	public ResultSet listOfCarsFilteredByAmounts(float amount, int choice) {
		String query = "SELECT * FROM Cars WHERE rental_price " + (choice == 1 ? "<" : ">")
				+ " ? ORDER BY rental_price,car_ID";
		 preparedStatement = null;
		 resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setFloat(1, amount);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;

	}

	public ResultSet listOfCarsByCarNameOrModel(String filter, int choice) {
		String query = "SELECT * FROM Cars WHERE " + (choice == 2 ? "make" : "model") + " like ? ORDER BY "
				+ (choice == 2 ? "make" : "model") + ",car_ID";
		preparedStatement = null;
		resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, filter + "%");

			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	public ResultSet listOfCarsFilteredByYears(int year, int choice) {
		String query = "SELECT * FROM Cars WHERE year " + (choice == 1 ? "<" : ">") + " ? ORDER BY year,car_ID";
		preparedStatement = null;
	    resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, year);

			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	public ResultSet listOfCarsBetweenTheseYears(int startYear, int endYear) {
		String query = "SELECT * FROM Cars WHERE year BETWEEN ? AND ? ORDER BY year,car_ID";
		preparedStatement = null;
		resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, startYear);
			preparedStatement.setInt(2, endYear);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	public ResultSet isAvailableNow(String carName, String carModel, int year) {

		String query = "SELECT * FROM Cars WHERE make = ? AND model = ? AND year = ? AND " + "is_Available = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, carName);
			preparedStatement.setString(2, carModel);
			preparedStatement.setInt(3, year);
			preparedStatement.setBoolean(4, true);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	public int getTotalDays(String startDate, String endDate) {
		int totalDays = 0;
		String query = "SELECT DATEDIFF(?,?) AS days";
		preparedStatement = null;
		resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, endDate);
			preparedStatement.setString(2, startDate);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			totalDays = resultSet.getInt("days");

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return totalDays;

	}

	public void bookACar(String startDate, String endDate, Cars car, int customerID) {

		changeAvailabilityOfCar(car.carID(),false);
		int totalDays = getTotalDays(startDate, endDate);

		preparedStatement = null;

		String query = "INSERT INTO rentals " + "(car_id,customer_id,rental_start_date,rental_end_date,total_amount) "
				+ "values(?,?,?,?,?)";

		preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, car.carID());
			preparedStatement.setInt(2, customerID);
			preparedStatement.setString(3, startDate);
			preparedStatement.setString(4, endDate);
			preparedStatement.setDouble(5, totalDays * car.rentalPrice());
			System.out.println(preparedStatement.executeUpdate() + " Rows Affected ...");

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

	}

	public void changeAvailabilityOfCar(int carID,boolean availability) {
		String query = "UPDATE Cars SET is_Available = ? WHERE car_id = ?";
		preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, availability);
			preparedStatement.setInt(2, carID);

			System.out.println(preparedStatement.executeUpdate() + " Rows Affected...");

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

	}

	public ResultSet getRentalDetails(int customerID,int rentalID) {
		String query = "SELECT * FROM rentals WHERE rental_id = ? AND customer_id = ? AND actual_return_date IS ? "
				+ "AND final_receiced_amount IS ?";
		preparedStatement = null;
		resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, rentalID);
			preparedStatement.setInt(2, customerID);
			preparedStatement.setNull(3, Types.INTEGER);
			preparedStatement.setNull(4, Types.INTEGER);

			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	

	public void updateRentalsClosingDetails(ResultSet resultSet,String actualReturnDate,double balance) {
		String query = "UPDATE Rentals SET actual_return_date = ?, final_receiced_amount = ? "
				+ "WHERE rental_id = ?";
		 preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, actualReturnDate);
			preparedStatement.setDouble(2,resultSet.getDouble("total_amount")+ balance);
			preparedStatement.setInt(3, resultSet.getInt("rental_id"));

			System.out.println(preparedStatement.executeUpdate() + " Rows Affected...");

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}
		
	}

	public boolean adminVerify(String userName, String passWord) {
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT UserName,PassWord FROM admins");
			resultSet.next();
			if (resultSet.getString("userName").equals(userName) && resultSet.getString("passWord").equals(passWord)) {
				System.out.println("Admin Verified SuccessFully....");
				return true;
			}
			System.out.println("UserName or PassWord is InValid....");

		} catch (SQLException se) {
			System.out.println("SQl Exception Occurs : ");

		}
		return false;
		
	}

	public void addCustomer(Customers customers) {
		try {
			String query = "INSERT INTO Customers (first_name,last_name,email,phone_number,userName,passWord,driving_license_number) "
					+ "VALUES(?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, customers.getFirstName());
			preparedStatement.setString(2, customers.getLastName());
			preparedStatement.setString(3, customers.getEmailID());
			preparedStatement.setString(4, customers.getPhoneNo());
			preparedStatement.setString(5, customers.getUserName());
			preparedStatement.setString(6, customers.getPassWord());

			preparedStatement.setString(7, customers.getDrivingLicence());
			int rows = preparedStatement.executeUpdate();
			System.out.println(rows + " Rows affected ");
		} catch (SQLException e) {
			System.out.println("Insert Query Exception ....");

		}
		
	}

	public int customerVerify(String userName, String passWord) {
		int customerID = -1;
		try {
			String query = "SELECT * FROM Customers WHERE UserName = ? AND passWord = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, passWord);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customerID = resultSet.getInt("customer_id");
				System.out.println("Customer Verified SuccessFully....");

			} else {
				System.out.println("User Doesn't Already Exists...");
			}

		} catch (SQLException e) {
			System.out.println("Select Query Exception...");
			e.printStackTrace();

		}
		return customerID;
		
	}

	public void addNewCar(Cars carDetails) {
		try {
			String query = "INSERT INTO cars VALUES (?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, carDetails.carID());
			preparedStatement.setString(2, carDetails.make());
			preparedStatement.setString(3, carDetails.model());
			preparedStatement.setInt(4, carDetails.year());
			preparedStatement.setDouble(5, carDetails.rentalPrice());
			preparedStatement.setBoolean(6, carDetails.isAvailable());
			int rows = preparedStatement.executeUpdate();
			System.out.println(rows + " Rows affected ");
		} catch (SQLException e) {
			System.out.println("Insert Query Exception ....");

		}
		
	}

	public boolean isCarPresentAndNotUsingByAnyCustomer(int carID) {
		
		preparedStatement = null;
		resultSet = null;
		 
		try {
			String query ="SELECT car_id FROM cars WHERE car_id = ? AND is_Available = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, carID);
			preparedStatement.setBoolean(2,true);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				System.out.println("There is No Car Presented According to Your Details : ");
				return false;
			}
			changeAvailabilityOfCar(carID, false);  
		}
		catch(SQLException e) {
			System.out.println("Select Query Exception ....");
		}
		
		
		
		return true;
	}

	public ResultSet showListOfCustomers() {
		
		String query = "SELECT * FROM Customers";
		Statement statement = null;
		resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	
	public boolean isAvailableNow(int carID) {
		String query = "SELECT car_id FROM cars WHERE car_id = ?";
		preparedStatement = null;
		resultSet = null;
		 
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, carID);
			resultSet = preparedStatement.executeQuery();
			
			return resultSet.isBeforeFirst();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return false;
	}

	public boolean getAvailability(int carID) {
		String query = "SELECT is_available FROM cars WHERE car_id = ?";
		preparedStatement = null;
		resultSet = null;
		 
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, carID);
			resultSet = preparedStatement.executeQuery();
			  
			if(!resultSet.isBeforeFirst()) {
				
				System.out.println("There is No Cars available with CarID  : "+carID);
				return false;
			}
			  
			resultSet.next();
			return resultSet.getBoolean("is_available");

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return false;
	}

	public ResultSet getAllRentHistory() {
		
		String query = "SELECT * FROM cars INNER JOIN rentals ON cars.car_id = rentals.car_id";
		Statement statement = null;
		resultSet = null;
		 
		try {
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(query);
			  
		
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	public ResultSet getAllRentalDetails() {
		String query = "SELECT * FROM cars INNER JOIN rentals ON cars.car_id = rentals.car_id "
				+ "where actual_return_date IS  ? AND  final_receiced_amount IS  ?";
	   preparedStatement = null;
		resultSet = null;
		 
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setNull(1, Types.INTEGER);
			preparedStatement.setNull(2, Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			  
		
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
		}

		return resultSet;
	}

	
}
