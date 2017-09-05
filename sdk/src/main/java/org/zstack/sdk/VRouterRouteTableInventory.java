package org.zstack.sdk;

public class VRouterRouteTableInventory  {

    public java.lang.String uuid;
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }
    public java.lang.String getUuid() {
        return this.uuid;
    }

    public java.lang.String name;
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getName() {
        return this.name;
    }

    public java.lang.String description;
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.String getDescription() {
        return this.description;
    }

    public java.sql.Timestamp createDate;
    public void setCreateDate(java.sql.Timestamp createDate) {
        this.createDate = createDate;
    }
    public java.sql.Timestamp getCreateDate() {
        return this.createDate;
    }

    public java.sql.Timestamp lastOpDate;
    public void setLastOpDate(java.sql.Timestamp lastOpDate) {
        this.lastOpDate = lastOpDate;
    }
    public java.sql.Timestamp getLastOpDate() {
        return this.lastOpDate;
    }

    public java.util.List<VirtualRouterVRouterRouteTableRefInventory> attachedRouterRefs;
    public void setAttachedRouterRefs(java.util.List<VirtualRouterVRouterRouteTableRefInventory> attachedRouterRefs) {
        this.attachedRouterRefs = attachedRouterRefs;
    }
    public java.util.List<VirtualRouterVRouterRouteTableRefInventory> getAttachedRouterRefs() {
        return this.attachedRouterRefs;
    }

    public java.util.List<VRouterRouteEntryInventory> routeEntries;
    public void setRouteEntries(java.util.List<VRouterRouteEntryInventory> routeEntries) {
        this.routeEntries = routeEntries;
    }
    public java.util.List<VRouterRouteEntryInventory> getRouteEntries() {
        return this.routeEntries;
    }

}
