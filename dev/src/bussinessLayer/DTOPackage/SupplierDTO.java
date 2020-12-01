package bussinessLayer.DTOPackage;



import java.util.List;

import bussinessLayer.SupplierPackage.Supplier.BillingOptions;

public class SupplierDTO {

    private BillingOptions billingOption;
    private String name;
    private int supplierId;
    private int bankAccountNumber;
    private ContractDTO contractDTO;
    private List<ContactDTO> contactDTOS;

    public SupplierDTO(int supplierId, String name, BillingOptions billingOption, int bankAccountNumber, ContractDTO contractDTO, List<ContactDTO> contactDTOS) {
        this.supplierId = supplierId;
        this.name = name;
        this.billingOption = billingOption;
        this.bankAccountNumber = bankAccountNumber;
        this.contactDTOS = contactDTOS;
        this.contractDTO = contractDTO;
    }

    public SupplierDTO(String name2, int supplierId2, int bankAccountNumber2, String name3, ContractDTO convertToDTO,
			List<ContactDTO> convertContactsToDTO) {
		name = name2;
		supplierId = supplierId2;
		bankAccountNumber = bankAccountNumber2;
		this.contractDTO = convertToDTO;
		contactDTOS = convertContactsToDTO;
		billingOption = BillingOptions.valueOf(name3);
	}

	public BillingOptions getBillingOption() {
        return billingOption;
    }

    public String getName() {
        return name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    @Override
    public String toString() {
        return "\n" + supplierId + "\t" + name + "\t" + billingOption;
    }

    public int getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    /**
     * @return the contactDTOS
     */
    public List<ContactDTO> getContactDTOS() {
        return contactDTOS;
    }

    /**
     * @return the contractDTO
     */
    public ContractDTO getContractDTO() {
        return contractDTO;
    }

	public CatalogDTO getCatalog() {
		return contractDTO.getCatalog();
	}

}