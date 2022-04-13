package model;

public class ProductInformation {
	private int locationCode;
	private int quantity;
	private int id;
	
	public ProductInformation(int locationCode, int quantity, int id) {
		super();
		this.locationCode = locationCode;
		this.quantity = quantity;
		this.id = id;
	}

	public int getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(int locationCode) {
		this.locationCode = locationCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
