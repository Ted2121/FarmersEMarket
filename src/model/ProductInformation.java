package model;

public class ProductInformation {
	private int locationCode;
	private int quantity;
	private Product product;
	
	public ProductInformation(int locationCode, int quantity, Product product) {
		super();
		this.locationCode = locationCode;
		this.quantity = quantity;
		this.product = product;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
