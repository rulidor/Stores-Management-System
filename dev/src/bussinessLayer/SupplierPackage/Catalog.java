package bussinessLayer.SupplierPackage;

import java.util.ArrayList;
import java.util.List;

import bussinessLayer.DTOPackage.CatalogDTO;
import bussinessLayer.DTOPackage.CatalogItemDTO;

public class Catalog {
    private List<CatalogItem> items;

    public Catalog() {
        items = new ArrayList<>();
    }

    public Catalog(List<CatalogItem> items) {
        this.items = items;
    }

    public Catalog(CatalogDTO catalog) {
        items = new ArrayList<CatalogItem>();
        covertListToBuis(catalog.getCatalogItems());
    }

    private void covertListToBuis(List<CatalogItemDTO> catalogItems) {
        for (CatalogItemDTO catalogItemDTO : catalogItems) {
            items.add(new CatalogItem(catalogItemDTO));
        }
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void addItemsToCatalogByList(List<CatalogItem> itemsList) {
        for (int i = 0; i < itemsList.size(); i++) {
            items.add(itemsList.get(i));
        }
    }

    public void addItemToCatalog(CatalogItem item) {
        if (!items.contains(item))
            items.add(item);
    }

    public void removItemFromList(CatalogItem item) throws Exception {
        if (items.contains(item)) {
            items.remove(item);
            return;
        }
        throw new Exception("the item do not exist");
    }


    public CatalogItem getCatalogItem(int catalogItemId) throws Exception {
        for (CatalogItem catalogItem : items) {
            if (catalogItem.getCatalogItemId() == catalogItemId)
                return catalogItem;

        }
        throw new Exception("the catalog-item do not found");
    }

    public String toString() {
        String s = "";
        if (items.isEmpty()) {
            try {
                throw new Exception("catalog is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (CatalogItem catalogItem : items) {
            s = s + "\n" + catalogItem.toString();
        }
        return s;
    }

    public int getcatalogItemIdByItemId(Integer itemId) {
        for (CatalogItem catalogItem : this.items)
        {
            if (catalogItem.getItemId() == itemId)
            {
                return catalogItem.getCatalogItemId();
            }
        }
        return -1;
    }

	public CatalogDTO convertToDTO() {
		return new CatalogDTO(convertListToDTO());
	}

	private List<CatalogItemDTO> convertListToDTO() {
		ArrayList<CatalogItemDTO> list = new ArrayList<CatalogItemDTO>();
		for (CatalogItem catalogItem : items) {
			list.add(catalogItem.converToDTO());
		}
		
		return list;
	}
}
