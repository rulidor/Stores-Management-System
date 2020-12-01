package bussinessLayer.Transports.DeliveryPackage;

public class Truck {

    private String id;
    private String model;
    private double netoWeight;
    private double totalWeight;
    private boolean isUsed;

    public Truck(String id, String model, double netoWeight, double totalWeight) {
        this.id = id;
        this.model = model;
        this.netoWeight = netoWeight;
        this.totalWeight = totalWeight;
        isUsed = false;
    }
    public Truck(String id, String model, double netoWeight, double totalWeight,boolean isUsed) {
        this.id = id;
        this.model = model;
        this.netoWeight = netoWeight;
        this.totalWeight = totalWeight;
        this.isUsed = isUsed;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getNetoWeight() {
        return netoWeight;
    }

    public void setNetoWeight(double netoWeight) {
        this.netoWeight = netoWeight;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed() {
        isUsed = true;
    }

    public void setNotUsed() {
        isUsed = false;
    }

    @Override
    public String toString() {
        return "Truck " +
                "id='" + id + '\n' +
                "model='" + model + '\n' +
                "netoWeight=" + netoWeight +'\n'+
                "totalWeight=" + totalWeight +'\n';
    }
}
