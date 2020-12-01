package bussinessLayer.BranchPackage;

import bussinessLayer.DTOPackage.DamagedControllerDTO;

import java.util.HashMap;
import java.util.Map;

public class DamagedController {

    private int branchId;
    private Map<Integer, Integer> quantityById;

    public DamagedController(int branchId) {
       this.branchId = branchId;
    }

    public DamagedController(Map<Integer, Integer> quantityById) {
        this.quantityById = new HashMap<>();
    }

    public Map<Integer, Integer> getQuantityById() {
        return quantityById;
    }

    public void setQuantityById(Map<Integer, Integer> quantityById) {
        this.quantityById = quantityById;
    }


    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public DamagedControllerDTO convertToDTO(){
        return new DamagedControllerDTO(this.branchId, this.quantityById);
    }




}
