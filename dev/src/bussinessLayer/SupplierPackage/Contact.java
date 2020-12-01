package bussinessLayer.SupplierPackage;

import bussinessLayer.DTOPackage.ContactDTO;

public class Contact {

    private String firstName;
    private String lastName;
    private String phonNumber;
    private String address;



    public Contact(String firstName, String lastName, String phonNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonNumber = phonNumber;
        this.address = address;

    }

    public Contact() {
        this.address = "";
        this.firstName = "";
        this.lastName = "";
        this.phonNumber = "";
    }

    public Contact(ContactDTO contactDTO) {
        firstName = contactDTO.getFirstName();
        lastName = contactDTO.getLastName();
        phonNumber = contactDTO.getPhoneNumber();
        address = contactDTO.getAddress();
	}

	public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getPhonNumber() {
        return phonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        if (!firstName.equals(""))
            this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (!lastName.equals(""))
            this.lastName = lastName;
    }

    public void setPhonNumber(String phonNumber) {
        if (!phonNumber.equals(""))
            this.phonNumber = phonNumber;
    }

    public void setAddress(String address) {
        if (!address.equals(""))
            this.address = address;

    }

    public String toString() {
        return  "contactName: " + this.firstName + " " + this.lastName + " ," + "contactPhoneNUM: " + this.phonNumber + " ," + "contact address: " + this.getAddress();
    }

	public ContactDTO convertToDTO() {
		return new ContactDTO(firstName, lastName, phonNumber, address);
	}
}
