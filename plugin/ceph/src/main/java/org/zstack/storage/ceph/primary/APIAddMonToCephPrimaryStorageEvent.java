package org.zstack.storage.ceph.primary;

import org.zstack.header.message.APIEvent;
import org.zstack.header.rest.RestResponse;
import org.zstack.header.storage.primary.PrimaryStorageState;
import org.zstack.header.storage.primary.PrimaryStorageStatus;

import java.sql.Timestamp;
import java.util.Collections;

/**
 * Created by frank on 8/6/2015.
 */
@RestResponse(allTo = "inventory")
public class APIAddMonToCephPrimaryStorageEvent extends APIEvent {
    private CephPrimaryStorageInventory inventory;

    public APIAddMonToCephPrimaryStorageEvent() {
    }

    public APIAddMonToCephPrimaryStorageEvent(String apiId) {
        super(apiId);
    }

    public CephPrimaryStorageInventory getInventory() {
        return inventory;
    }

    public void setInventory(CephPrimaryStorageInventory inventory) {
        this.inventory = inventory;
    }
 
    public static APIAddMonToCephPrimaryStorageEvent __example__() {
        APIAddMonToCephPrimaryStorageEvent event = new APIAddMonToCephPrimaryStorageEvent();

        CephPrimaryStorageInventory ps = new CephPrimaryStorageInventory();
        ps.setName("My Ceph Backup Storage");
        ps.setDescription("Public Ceph Backup Storage");
        ps.setCreateDate(new Timestamp(System.currentTimeMillis()));
        ps.setLastOpDate(new Timestamp(System.currentTimeMillis()));
        ps.setType("Ceph");
        CephPrimaryStorageMonInventory mon = new CephPrimaryStorageMonInventory();
        mon.setMonUuid(uuid());
        mon.setMonAddr("10.0.1.2");
        ps.setMons(Collections.singletonList(mon));
        ps.setState(PrimaryStorageState.Enabled.toString());
        ps.setStatus(PrimaryStorageStatus.Connected.toString());
        ps.setAvailableCapacity(924L * 1024L * 1024L);
        ps.setTotalCapacity(1024L * 1024L * 1024L);
        ps.setAttachedClusterUuids(Collections.singletonList(uuid()));

        return event;
    }

}