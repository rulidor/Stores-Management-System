package bussinessLayer.DTOPackage;

import java.util.Map;

public class DamagedControllerDTO {

    private int branchId;
    private Map<Integer, Integer> quantityById;

    public DamagedControllerDTO(int branchId, Map<Integer, Integer> quantityById) {
        this.branchId = branchId;
        this.quantityById = quantityById;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Map<Integer, Integer> getQuantityById() {
        return quantityById;
    }

    public void setQuantityById(Map<Integer, Integer> quantityById) {
        this.quantityById = quantityById;
    }
}
