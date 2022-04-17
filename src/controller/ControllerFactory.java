package controller;

import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;

public class ControllerFactory {
	private ControllerFactory() {}
	
	public static CreatePurchaseOrderController getCreatePurchaseOrderController() {
		return new CreatePurchaseOrderControllerImplementation();
	}
}
