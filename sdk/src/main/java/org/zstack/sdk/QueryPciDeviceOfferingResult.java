package org.zstack.sdk;

public class QueryPciDeviceOfferingResult {
    public java.util.List<PciDeviceOfferingInventory> inventories;
    public void setInventories(java.util.List<PciDeviceOfferingInventory> inventories) {
        this.inventories = inventories;
    }
    public java.util.List<PciDeviceOfferingInventory> getInventories() {
        return this.inventories;
    }

    public java.lang.Long total;
    public void setTotal(java.lang.Long total) {
        this.total = total;
    }
    public java.lang.Long getTotal() {
        return this.total;
    }

}
