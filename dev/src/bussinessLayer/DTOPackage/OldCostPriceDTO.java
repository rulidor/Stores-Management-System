package bussinessLayer.DTOPackage;

public class OldCostPriceDTO {

    private int itemId;
    private int counter; //costCounter
    private double price;

    public OldCostPriceDTO(int itemId, int counter, double price) {
        this.itemId = itemId;
        this.counter = counter;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
