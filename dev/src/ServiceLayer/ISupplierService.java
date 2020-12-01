package ServiceLayer;

import java.util.List;
import bussinessLayer.DTOPackage.*;

public interface ISupplierService {
    

    /**
     * Get Supplier's info
     * @param supplierId The supplier ID
     * @return SupplierDTO with all the necessary data
     */
    public ResponseT<SupplierDTO> getSupplierInfo(String supplierId);
    /**
     * Add Supplier to DB and RAM
     * @param supplierName The Supplier's name
     * @param supplierId - The Supplier's Id
     * @param bankAccount - Bank account number
     * @param bilingOptions - Which payment methods the store pays to the supplier 
     * @param isDeliver - Supplier has deliveries or not
     * @return if success "Done", else error message
     */
    public Response AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver);

    /**
     * Updates supplier's bank account
     * @param supplierId The supplier ID
     * @param bankAccount The new supplier's bank account
     * @return if success "Done", else error message
     */
    public Response updateSupplierBankAccount(int supplierId, int bankAccount);

    /**
     * Updates supplier's Name
     * @param supplierId The supplier ID
     * @param name The new supplier's Name
     * @return if success "Done", else error message
     */
    public Response updateSupplierName(int supplierId, String name);

    /**
     * Adds a new contact to supplier
     * @param supplierId - The supplierID
     * @param firstName
     * @param lastName
     * @param phoneNum - the phone number acts also as ID
     * @param address
     * @return if success "Done", else error message
     */
    public Response addContact(int supplierId, String firstName, String lastName, String phoneNum, String address);

    /**
     * Deletes certain contact from supplier
     * @param supplierId - The supplier ID
     * @param phoneNum - Acts as an ID
     * @return if success "Done", else error message
     */
    public Response deleteContact(int supplierId, String phoneNum);

    /**
     * Updates supplier's contact
     * @param supplierId - The supplier ID
     * @param updated - An array that holds updated details of contact
     * @param phoneNumber - The phoneNum
     * @return if success "Done", else error message
     */
    public Response updateContact(int supplierId, String[] updated, String phoneNumber);

    /**
     * Update if supplier can deliever products or not
     * @param supplierId - The supplier ID
     * @param isDeliver - boolean that states if he is delivering 
     * or not
     * @return if success "Done", else error message
     */
    public Response updateContractIsDeliver(int supplierId, boolean isDeliver);

    /**
     * Updates supplier's billing option
     * @param supplierId - The supplier ID
     * @param bilingOption - The new billing option
     * @return if success "Done", else error message
     */
    public Response updateBillingOptions(int supplierId, String bilingOption);

    /**
     * 
     * @param supplierId
     * @param catalogItemId
     * @param min
     * @param max
     * @param priceafterDisc
     * @return
     */
    public Response UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc);

    /**
     * Removes supplier
     * @param SupplierId - The supplier ID
     * @return if success "Done", else error message
     */
    public Response removeSupplier(int SupplierId);

    /**
     * Adds catalogItem to contract
     * @param supplierId - The supplier's ID
     * @param itemId - The item ID
     * @param catalogItemId - The catalog item ID
     * @param price - The default price per unit without discount
     * @return if success "Done", else error message
     */
    public Response addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price);

    /**
     * 
     * @param supplierId - The supplier ID
     * @param catalogItemId - The catalog item ID
     * @return if success "Done", else error message
     */
    public Response deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId);

    /**
     * Get  all suppliers details
     * @return if success returns list of SupplierDTO which contains
     * all the suppliers details
     */
    public ResponseT<List<SupplierDTO>> getSuppliersInfo();

    /**
     * Get catalog details
     * @param supplierId - The supplier ID
     * @return if success returns CatalogDTO with all the details of the catalog
     */
    public ResponseT<CatalogDTO> getCatalog(int supplierId);

    /**
     * Add regular day deliveries of supplier
     * @param constDayDeli - An array which holds the days of delivery
     * @param supplierId - The supplier ID
     * @return if success "Done", else error message
     */
    public Response addConstDeliveryDays(String[] constDayDeli, int supplierId);

    /**
     * Get all supplier's contacts
     * @param supplierId - The supplier ID
     * @return if success returns list of ContactDTO, else error message
     */
    public ResponseT<List<ContactDTO>> getContactsList(int supplierId);

    /**
     * Get supplier contract's details
     * @param supplierId - The supplier ID
     * @return if success ContractDTO else, error message
     */
    public ResponseT<ContractDTO> getContractDetails(int supplierId);

    //TODO
    /**
     * 
     * @param supplierId
     * @param catalogItemId
     * @return
     */
    public Response cleanRangeListItemFromMap(int supplierId, int catalogItemId);
    
    //TODO
    /**
     * 
     * @param supplierId
     * @return
     */
    public Response isSupplierExist(String supplierId);

    public Response loadFirstSuppliers();
    
}