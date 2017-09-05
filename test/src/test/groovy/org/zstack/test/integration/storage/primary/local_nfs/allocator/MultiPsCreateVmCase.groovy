package org.zstack.test.integration.storage.primary.local_nfs.allocator

import org.zstack.sdk.*
import org.zstack.test.integration.storage.StorageTest
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.SubCase
import org.zstack.utils.data.SizeUnit

/**
 * Created by lining on 2017-06-27.
 */
class MultiPsCreateVmCase extends SubCase {
    EnvSpec env

    @Override
    void setup() {
        useSpring(StorageTest.springSpec)
    }

    @Override
    void environment() {
        env = env {
            instanceOffering {
                name = "instanceOffering"
                memory = SizeUnit.GIGABYTE.toByte(1)
                cpu = 1
            }

            diskOffering {
                name = "diskOffering"
                diskSize = SizeUnit.GIGABYTE.toByte(100)
            }

            diskOffering {
                name = "diskOffering2"
                diskSize = SizeUnit.GIGABYTE.toByte(203)
            }

            diskOffering {
                name = "diskOffering3"
                diskSize = SizeUnit.GIGABYTE.toByte(102)
            }

            sftpBackupStorage {
                name = "sftp"
                url = "/sftp"
                username = "root"
                password = "password"
                hostname = "localhost"

                image {
                    name = "image"
                    url = "http://zstack.org/download/test.qcow2"
                }
            }

            zone {
                name = "zone"
                description = "test"

                cluster {
                    name = "cluster"
                    hypervisorType = "KVM"

                    kvm {
                        name = "kvm"
                        managementIp = "localhost"
                        username = "root"
                        password = "password"
                        totalCpu = 88
                        totalMem = SizeUnit.GIGABYTE.toByte(100)
                    }

                    attachPrimaryStorage("nfs")
                    attachPrimaryStorage("nfs2")
                    attachL2Network("l2")
                }

                nfsPrimaryStorage {
                    name = "nfs"
                    url = "172.20.0.1:/nfs_root"
                    totalCapacity = SizeUnit.GIGABYTE.toByte(101)
                    availableCapacity = SizeUnit.GIGABYTE.toByte(101)
                }

                nfsPrimaryStorage {
                    name = "nfs2"
                    url = "172.20.0.2:/nfs_root"
                    totalCapacity = SizeUnit.GIGABYTE.toByte(101)
                    availableCapacity = SizeUnit.GIGABYTE.toByte(101)
                }

                l2NoVlanNetwork {
                    name = "l2"
                    physicalInterface = "eth0"

                    l3Network {
                        name = "l3"

                        ip {
                            startIp = "12.16.10.10"
                            endIp = "12.16.10.100"
                            netmask = "255.255.255.0"
                            gateway = "12.16.10.1"
                        }
                    }
                }

                attachBackupStorage("sftp")
            }
        }
    }

    @Override
    void test() {
        env.create {
            createVmVolumeSizeEqualMultiPsCap()
        }
    }

    @Override
    void clean() {
        env.delete()
    }

    void createVmVolumeSizeEqualMultiPsCap() {
        InstanceOfferingInventory instanceOffering = env.inventoryByName("instanceOffering") as InstanceOfferingInventory
        DiskOfferingInventory diskOffering = env.inventoryByName("diskOffering") as DiskOfferingInventory
        DiskOfferingInventory diskOffering2 = env.inventoryByName("diskOffering2") as DiskOfferingInventory
        ImageInventory image = env.inventoryByName("image") as ImageInventory
        L3NetworkInventory l3 = env.inventoryByName("l3") as L3NetworkInventory

        CreateVmInstanceAction createVmInstanceAction = new CreateVmInstanceAction(
                name: "vm",
                instanceOfferingUuid: diskOffering2.uuid,
                imageUuid: image.uuid,
                l3NetworkUuids: [l3.uuid],
                dataDiskOfferingUuids: [diskOffering2.uuid],
                sessionId: currentEnvSpec.session.uuid
        )
        assert createVmInstanceAction.call().error != null

        VmInstanceInventory vm = createVmInstance {
            name = "newVm"
            instanceOfferingUuid = instanceOffering.uuid
            imageUuid = image.uuid
            l3NetworkUuids = [l3.uuid]
            dataDiskOfferingUuids = [diskOffering.uuid,diskOffering.uuid]
        }
    }

}
