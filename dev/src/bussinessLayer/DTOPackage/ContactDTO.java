package bussinessLayer.DTOPackage;

public class ContactDTO {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;

	public ContactDTO( String firstName, String lastName, String phonNumber, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phonNumber;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return phoneNumber + "\t" + firstName
				+ "\t\t" + lastName + "\t\t" + address;
	}
}