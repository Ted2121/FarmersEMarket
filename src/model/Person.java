package model;

public abstract class Person implements SearchableByName{
    private int id;
    private String firstName;
    private String lastName;
    private final String fullName;
    private String city;
    private String country;
    

    public Person(String firstName, String lastName, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.fullName = firstName + " " + lastName;
        this.country = country;
    }

    public Person(int id, String firstName, String lastName, String city, String country ) {
    	this(firstName, lastName, city, country);
    	this.id = id;
    }

    public Person(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName + " " + lastName;
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    // use the full name instead where possible
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

	@Override
	public String toString() {
		return this.getFullName();
	}
    
	@Override
	public String getStringToSearch() {
		return getFullName();
	}
    
}
