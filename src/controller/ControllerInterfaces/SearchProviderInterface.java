package controller.ControllerInterfaces;

import java.util.List;

import model.Customer;
import model.Provider;

public interface SearchProviderInterface {
	List<Provider> searchProviderUsingThisName(String providerName);
}
