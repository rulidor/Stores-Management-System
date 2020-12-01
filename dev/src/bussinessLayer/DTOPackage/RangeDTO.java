package bussinessLayer.DTOPackage;


public class RangeDTO {
    private int max;
    private int min;

    public RangeDTO(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}

