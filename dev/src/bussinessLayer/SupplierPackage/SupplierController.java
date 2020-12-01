package bussinessLayer.SupplierPackage;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import DataAccessLaye.Repo;
import bussinessLayer.DTOPackage.*;

public class SupplierController {

		/**
	 * Update Supplier using his whole structure
	 * 
	 * @param s Supplier object transfered with all the info needed
	 * @throws SQLException
	 */
	private void updateSupplier(Supplier s) throws SQLException {
		Repo.getInstance().updateSupplier(s.convertToDTO());
	}

	/**
	 * Get business supplier by ID
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	public Supplier getSupplierById(int supplierId) throws Exception {
		return new Supplier(getSupplierInfo(supplierId));
	}

	public void AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) throws Exception {
		Repo.getInstance().insertSupplier(new bussinessLayer.SupplierPackage.Supplier(supplierName,
				supplierId, bankAccount, Supplier.BillingOptions.valueOf(bilingOptions), isDeliver));
	}

	public void updateSupplierBankAccount(int supplierId, int bankAccount)throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.setBankAccountNumber(bankAccount);
		updateSupplier(s);
	}

	public void updateSupplierName(int supplierId, String name) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.setName(name);
		updateSupplier(s);

	}

	public void addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) throws Exception {
		ContactDTO contactDTO = new ContactDTO(firstName,lastName,phoneNum,address);
		Repo.getInstance().insertContact(supplierId, contactDTO);
	}

	public void deleteContact(int supplierId, String phoneNumber) throws Exception {
/*
		getSupplierById(supplierId);// TODO SUPPLIER ID NOT NECESSARY?!
*/
		Repo.getInstance().deleteContact(phoneNumber,supplierId);
	}

	public void updateContact(int supplierId, String[] updated, String phoneNumber) throws Exception {
		ContactDTO contactDTO = new ContactDTO(updated[0],updated[1],updated[2],updated[3]);
		Repo.getInstance().updateContact(phoneNumber,supplierId,contactDTO);
		
	}

	public void updateContractIsDeliver(int supplierId, boolean isDeliver) throws Exception {
		Supplier supplier = getSupplierById(supplierId);
		supplier.setDeliverContrect(isDeliver);
		ContractDTO contractDTO =  supplier.convertToDTO().getContractDTO();
		Repo.getInstance().deleteConstDelivery(supplierId);
		Repo.getInstance().updateContract(contractDTO);
	}

	public void updateBillingOptions(int supplierId, String bilingOption) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
		supplier.updateBilingOptions(bilingOption);
		updateSupplier(supplier);
	}

	public void UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) throws Exception {
		Repo.getInstance().insertRange(new RangeDTO(min,max), supplierId, catalogItemId, priceafterDisc);

	}

	public void removeSupplier(int supplierId) throws Exception {
		Repo.getInstance().deleteSupplierById(supplierId);

	}

	public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) throws Exception {
		Repo.getInstance().insertCatalogItem(new CatalogItemDTO(catalogItemId,Repo.getInstance().getItemDescription(itemId),price, itemId), supplierId);
	}

	public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) throws Exception {
		Repo.getInstance().deleteCatalogItem(supplierId, catalogItemId);
	}

	public List<bussinessLayer.DTOPackage.SupplierDTO> getSuppliersInfo() throws SQLException {
		return Repo.getInstance().getAllSuppliers();
	}

	public CatalogDTO getCatalog(int supplierId) throws Exception {
		return Repo.getInstance().getCatalog(supplierId);
	}

	public void addConstDeliveryDays(String[] constDayDeli, int supplierId) throws Exception {
		List<DayOfWeek> list = new ArrayList<DayOfWeek>();
		for(int i=0; i < constDayDeli.length;i++ ) {
			list.add(DayOfWeek.valueOf(constDayDeli[i]));
		}
		if(!Repo.getInstance().getContract(supplierId).getIsDeliver()) throw new Exception("Supplier does not have deliveries");
		Repo.getInstance().deleteConstDelivery(supplierId);
		Repo.getInstance().insertDeliveryDays(new DeliveryDaysDTO(list), supplierId);
	}

	public List<ContactDTO> getContactsList(int supplierId) throws Exception {
		return Repo.getInstance().getAllContactBySupplier(supplierId);
	}

	public bussinessLayer.DTOPackage.ContractDTO getContractDetails(int supplierId) throws Exception {
		return Repo.getInstance().getContract(supplierId);
	}

	public void isSupplierExist(String supplierId) throws Exception {
		getSupplierInfo(Integer.valueOf(supplierId));
	}

	public bussinessLayer.DTOPackage.SupplierDTO getSupplierInfo(int supplierId) throws Exception {
		return Repo.getInstance().getSupplierById(supplierId);
	}

	public void cleanRangeListItemFromMap(int supplierId, int catalogItemId) throws Exception {
		Repo.getInstance().deleteAllRangesByContractId(supplierId, catalogItemId);
	}

	public void loadFirstData() throws Exception {
		AddSupplier("Tnuva", 1, 123, "CASH", true);
		AddSupplier("Pharm", 2, 456, "CHECK", false);
		String[] s2 = {"THURSDAY","MONDAY"};
		addConstDeliveryDays(s2, 1);
		
		//TNUVA ITEMS
		addCatalogItemToCatalogInContract(1, 1, 10, 3.5);//MILK
		addCatalogItemToCatalogInContract(1, 2, 11, 7.5);//CHEESE
		
		//PHARM ITEMS
		addCatalogItemToCatalogInContract(2, 3, 20, 4.5); //SHAMPOO
		addCatalogItemToCatalogInContract(2, 4, 21, 4.5);//CONDITIONER
		addCatalogItemToCatalogInContract(2, 5, 22, 0.5);//BUN
		addCatalogItemToCatalogInContract(2, 1, 23, 3.5);//MILK
		
		//CONTACTS
		addContact(1, "Yuval", "Hambulbal", "034353073", "BeerSheva");
		addContact(2, "Dora", "Buzz", "087234784", "Haifa");
		
		//MILK AGREEMENT WITH TNUVA
		UpdateMap(1, 10, 1, 70, 3.5);
		UpdateMap(1, 10, 71, -1, 3);
		
		//CHEESE AGREEMENT WITH TNUVA
		UpdateMap(1, 11, 1, 30, 7.5);
		UpdateMap(1, 11, 31, -1, 5);
		
		//SHAMPOO AGREEMENT WITH PHARM
		UpdateMap(2, 20, 1, 10, 4.5);
		UpdateMap(2, 20, 11, 30, 4);
		UpdateMap(2, 20, 31, -1, 3.5);
		
		//CONDITIONER AGREEMENT WITH PHARM
		UpdateMap(2, 21, 1, 10, 4.5);
		UpdateMap(2, 21, 21, 50, 4);
		UpdateMap(2, 21, 51, -1, 3.5);
		
		//BUN AGREEMENT WITH PHARM
		UpdateMap(2, 22, 1, 100, 0.5);
		UpdateMap(2, 22, 101, -1, 0.3);
		
		//MILK AGREEMENT WITH PHARM
		UpdateMap(2, 23, 1, 50, 3.7);
		UpdateMap(2, 23, 51, 100, 3.5);
		UpdateMap(2, 23, 101, -1, 2.5);
	
	}

}
