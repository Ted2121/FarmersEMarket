package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import db_access.*;
import db_access.DaoInterfaces.*;
import model.*;


public class FastSearchHelperClass<T extends SearchableByName> 
//implements FastSearchHelperClass
{
	private ContainSubsetDao<T> objectSubsetDaoPart;
	private List<List<T>> tObjectsContainingLetter;
	
	
	public FastSearchHelperClass(ContainSubsetDao<T> objectSubsetDaoPart) {
		this.objectSubsetDaoPart = objectSubsetDaoPart; 
		refreshData();
	}

	public void refreshData() {
		retrieveAllObjectsSubset();
	}

	public List<T> searchUsingThisName(String name) {
		//We set a new list of T which will contain all the providers matching the search
		List<T> matchingTObjects = new ArrayList<T>();
		//We ask a method to get all the providers containing each letter of the providerName
		List<T> tObjectsMatchingTheLetters = sortLetterToMatchingObjects(name);
		//If we have a result
		if(tObjectsMatchingTheLetters != null)
			//For each provider in the list of results
			for(T object : tObjectsMatchingTheLetters) {
				//We get the FullName of this provider, change capitals letter to normals letter, delete white spaces and check if they contained the specific string
				if(object.getStringToSearch().toLowerCase().replace(" ", "").contains(name.toLowerCase().replace(" ", ""))) {
					//If they contain it, we add them to the matching list
					matchingTObjects.add(object);
				}
			}
		//If we don't have any results, we add all the providers
		else matchingTObjects.addAll(tObjectsMatchingTheLetters);
		//Finally, we return all the providers matching the search
		return matchingTObjects;
	}

	private List<T> sortLetterToMatchingObjects(String stringToSort) {
		//This method uses generics and can use either Product or Provider class
		
		//First, we take the string, change capital letters to normal letters and delete white spaces
		String lowerCaseStringWithoutSpaceCharacter = stringToSort.toLowerCase().replaceAll(" ", "");
		//We create a list of lists for the objects we want to sort
		List<List<T>> listOfListOfObjectsContainingTheLetterOfThisName = new ArrayList<List<T>>();
		
		//Retrieving all the lists of objects containing the specified characters of each list
		for(int i=0 ; i<lowerCaseStringWithoutSpaceCharacter.length();i++) {
			//We take 1 character of the string
			char character = lowerCaseStringWithoutSpaceCharacter.charAt(i);
			//We set the index at 0;
			int characterListNumber = 0;
			try {
				//We use a method to retrieve only handled characters (usefull for )
				characterListNumber = settingTheCharacterToAnHandledOne(character);
				
				//If we need to sort Products, and we don't already have a list containing this letter
				if(!listOfListOfObjectsContainingTheLetterOfThisName.contains(tObjectsContainingLetter.get(characterListNumber)))
					//We add the list of this letter to the list of list of letters
					listOfListOfObjectsContainingTheLetterOfThisName.add((List<T>) tObjectsContainingLetter.get(characterListNumber));
					
			} catch (Exception e) {
				//If a Exception occurs during settingTheCharacterToAnHandledOne method, we print the message
				System.out.println(e.getMessage());
			}	
		}
		
		//Then we will make an intersection of all lists to retrieve only products with each letters
		List<T> matchingObjects = null;
		//If we have a list which contains more than 0 list
		if(listOfListOfObjectsContainingTheLetterOfThisName.size()>0)
			//we set the matching object to the first index
			matchingObjects = listOfListOfObjectsContainingTheLetterOfThisName.get(0);
		//For each list in the list of list of object containing a specific letter
		for(List<T> list :  listOfListOfObjectsContainingTheLetterOfThisName) {
			//We make the intersection between the matchingObjects and the list
			matchingObjects.retainAll(list);
		}
		
		//If no objects match the search
		if(matchingObjects == null) {
			//We create a new ArrayList
			matchingObjects = new ArrayList<>();
			//We add all the products in all the products letter lists
			for(List<T> list : tObjectsContainingLetter) {
				matchingObjects.addAll( (Collection<? extends T>) list);
			}		
			
			//Finally, we get rid of duplicates
			matchingObjects = new ArrayList<>(new LinkedHashSet<>(matchingObjects));
		}
		
		//And we return the matchings objects
		return matchingObjects;
	}

	private int settingTheCharacterToAnHandledOne(char character) throws Exception {
		//This methods take a characrete and if it's a non handled charactere will set it to an handled one
		switch(character) {
			case 239 -> character = 105; //� -> i
			case 238 -> character = 105; //� -> i
			case 237 -> character = 105; //� -> i
			case 236 -> character = 105; //� -> i
			case 235 -> character = 101; //� -> e
			case 234 -> character = 101; //� -> e
			case 233 -> character = 101; //� -> e
			case 232 -> character = 101; //� -> e
			case 231 -> character = 99;  //� -> c
		}
		//We retrieve the charactere - 97 to get the index in the list of list
		int numberOfTheListOfThisCharacter = character-97;
		//If the character is not between 0 and 25
		if(numberOfTheListOfThisCharacter < 0 || numberOfTheListOfThisCharacter >= 26) {
			//We throw an Exception
			throw new Exception("Character unhandled by the application: " + character);
		}
		//Finally we return the index of the list
		return numberOfTheListOfThisCharacter;
	}
	
	private List<T> retrieveAllObjectsSubset() {
		//This method takes a class as parameter and retrieve all its subsets
		
		List<T> objectSubsetList = null;
		//If the class id product
		
		//We create a new ArrayList of tObjects
		tObjectsContainingLetter = new ArrayList<List<T>>();
			
		//Then we instanciate a new List for all 26 letter of the alphabete (special charactere should have already been handled)
		for(int i=0;i<26;i++) {
			tObjectsContainingLetter.add(new ArrayList<>());
		}
		
		
		try {
			//Then we use the Dao class to retrieve the subsets
			objectSubsetList = objectSubsetDaoPart.findAllSubset();
			//For each objects of the subset list
			for(T object : objectSubsetList) {
				String lowerCaseObjectWithoutSpaceCharacter = null;
				//We get their name version without capital and blank space
				lowerCaseObjectWithoutSpaceCharacter = object.getStringToSearch().toLowerCase().replace(" ", "");
				
				//If the String is blanck, we can stop the loop
				if(lowerCaseObjectWithoutSpaceCharacter.isBlank()) {
					break;
				}
				//For each charactere of the whole string
				for(int i=0 ; i<lowerCaseObjectWithoutSpaceCharacter.length();i++) {
					//We get the charactere at the next index (start 0)
					char character = lowerCaseObjectWithoutSpaceCharacter.charAt(i);

					//we set the charactere index at 0
					int characterListNumber = 0;
					try {
						//We get the list number of the character
						characterListNumber = settingTheCharacterToAnHandledOne(character);
					} catch (Exception e) {
						//If we get an exception, we print it in the console
						System.out.println(e.getMessage());
					}
					
					List<T> letterList = null;
					//Then we retrieve the list of the letter we are testing
					
					letterList = (List<T>) tObjectsContainingLetter.get(characterListNumber);
					
					//If it doesn't already contains this objects
					if(!letterList.contains(object)) {
						//We add it to the list
						letterList.add(object);
					}
				}
				
				
			}
		
		
		} catch (SQLException e) {
			//If a database excecption occure, we print this line
			System.out.println("Cannot retrieve products subset from database");
		} catch (Exception e) {
			//If an other excecption occure, we print this line
			System.out.println("Cannot retrieve products subset");
		}
		
		if(objectSubsetList == null) {
			return null;
		}
		
		if(objectSubsetList.size() < 31) {
			return objectSubsetList;
		}
		
		return objectSubsetList.subList(0, 30);
	}

}
