package test.testingClass;

import model.Order;

public class TestingSaleOrder extends Order{
	double Price;

	public TestingSaleOrder(int id) {
		super(id);
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}
	
	

}
