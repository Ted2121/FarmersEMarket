package controller;

import controller.ControllerImplementation.CRUDProductInformationControllerImplementation;
import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerImplementation.CreateSaleOrderControllerImplementation;
import controller.ControllerInterfaces.CRUDProductInformationController;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import controller.ControllerInterfaces.CreateSaleOrderController;

public class ControllerFactory {
	private ControllerFactory() {}
	
	public static CreatePurchaseOrderController getCreatePurchaseOrderController() {
		return new CreatePurchaseOrderControllerImplementation();
	}

	public static CreateSaleOrderController getCreateSaleOrderController() {
		return new CreateSaleOrderControllerImplementation();
	}
	
	public static CRUDProductInformationController getCRUDProductInformationController() {
		return new CRUDProductInformationControllerImplementation();
	}
}
