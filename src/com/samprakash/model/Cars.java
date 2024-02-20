package com.samprakash.model;

public record Cars(int carID, int year, 
		String make, String model, double rentalPrice, boolean isAvailable) {

	public Cars(int carID, double rentalPrice) {
		this(carID,9999,"","",rentalPrice,false);
		
	}

}
