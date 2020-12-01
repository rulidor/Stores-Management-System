package bussinessLayer.Transports.DeliveryPackage;

public class Location {

    private int id;
    private String name;
    private String address;
    private String telNumber;
    private String contactName;
    private String shippingArea;

    public Location(int id, String name, String address, String telNumber, String contactName, String shippingArea) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.contactName = contactName;
        this.shippingArea = shippingArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getShippingArea() {
        return shippingArea;
    }

    public void setShippingArea(String shippingArea) {
        this.shippingArea = shippingArea;
    }

    @Override
    public String toString() {
        return "Location: "+name+'\n'+
                "id='" + id + '\n' +
                "address='" + address + '\n' +
                "telNumber='" + telNumber + '\n' +
                "contactName='" + contactName + '\n' +
                "shippingArea='" + shippingArea+'\n';
                }
}

