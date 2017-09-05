package org.zstack.test.integration.networkservice.provider.flat.eip

import org.zstack.sdk.EipInventory
import org.zstack.sdk.L3NetworkInventory
import org.zstack.sdk.QueryVipResult
import org.zstack.sdk.VipInventory
import org.zstack.test.integration.networkservice.provider.NetworkServiceProviderTest
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.SubCase
import org.zstack.utils.data.SizeUnit

class DeleteEipAndVipCase extends SubCase {

    EnvSpec env

    @Override
    void setup() {
        useSpring(NetworkServiceProviderTest.springSpec)
    }

    @Override
    void environment() {
        env = env {
            instanceOffering {
                name = "instanceOffering"
                memory = SizeUnit.GIGABYTE.toByte(8)
                cpu = 4
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
                    }

                    attachPrimaryStorage("local")
                    attachL2Network("l2")
                }

                localPrimaryStorage {
                    name = "local"
                    url = "/local_ps"
                }

                l2NoVlanNetwork {
                    name = "l2"
                    physicalInterface = "eth0"

                    l3Network {
                        name = "pubL3"

                        ip {
                            startIp = "11.168.100.10"
                            endIp = "11.168.100.100"
                            netmask = "255.255.255.0"
                            gateway = "11.168.100.1"
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
            testDeleteEipAndVip()
        }
    }

    void testDeleteEipAndVip() {
        def pubL3 = env.inventoryByName("pubL3") as L3NetworkInventory
        def vip = createVip {
            name = "vip"
            l3NetworkUuid = pubL3.uuid
        } as VipInventory
        def eip = createEip {
            name = "eip"
            vipUuid = vip.uuid
        } as EipInventory

        def thread1 = Thread.start {
            deleteEip { uuid = eip.uuid }
        }

        def thread2 = Thread.start {
            deleteVip { uuid = vip.uuid }
        }

        [thread1, thread2].each {it.join()}

        retryInSecs(3, 1) {
            assert ( queryVip {} as List<QueryVipResult> ).size() == 0
        }
    }

    @Override
    void clean() {
        env.delete()
    }
}
