package com.bluepi.loan.base;

import com.aerospike.client.Host;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class IntegratedConstantYml {

    @Value("${oracle.classname}")
    public String ORACLE_COMMON_CLASSNAME;

    @Value("${oracle.url}")
    public String ORACLE_COMMON_URL;

    @Value("${oracle.username}")
    public String ORACLE_COMMON_USERNAME;

    @Value("${oracle.password}")
    public String ORACLE_COMMON_PASSWORD;

    @Value("${oracle.initialsize}")
    public int ORACLE_COMMON_INITIAL_SIZE;

    @Value("${oracle.maxactive}")
    public int ORACLE_COMMON_MAX_ACTIVE;

    @Value("${oracle.minidle}")
    public int ORACLE_COMMON_MIN_IDLE;

    @Value("${default.service.retry.counter}")
    public int RETRY_COUNTER;

    //lams-prefix
    @Value("${lams.id.prefix}")
    public String LAMS_PREFIX_FOR_ID;

    //flag
    @Value("${aerospike.user.authentication.enabled}")
    public int FLAG_FOR_DEV;

    //Kafka
    @Value("${kafka.server.key}")
    public String KAFKA_COMMON_SERVER_KEY;

    @Value("${kafka.server.value}")
    public String KAFKA_COMMON_SERVER_VALUE;

    @Value("${kafka.topic.internal-update}")
    public String INTERNAL_UPDATE_KAFKA_TOPIC;

    //Aerospike
    @Value("${aerospike.namespace}")
    public String AEROSPIKE_COMMON_NAMESPACE;

    @Value("${aerospike.host}")
    public String AEROSPIKE_COMMON_CLIENT_HOST;

    @Value("${aerospike.port}")
    public String AEROSPIKE_COMMON_CLIENT_PORT;

    @Value("${aerospike.username:null}")
    public String AEROSPIKE_DEV_USERNAME;

    @Value("${aerospike.password:null}")
    public String AEROSPIKE_DEV_PASSWORD;

    @Value("${aerospike.rwPolicy.readPolicyDefault.totalTimeout}")
    public int AEROSPIKE_RW_R_POLICY_TOTAL_TIME;

    @Value("${aerospike.rwPolicy.writePolicyDefault.totalTimeout}")
    public int AEROSPIKE_RW_W_POLICY_TOTAL_TIME;

    @Value("${aerospike.rwPolicy.minConnsPerNode}")
    public int AEROSPIKE_RW_MIN_CONS_PER_NODE;

    @Value("${aerospike.rwPolicy.maxSocketIdle}")
    public int AEROSPIKE_RW_MAX_SOCKET_IDLE;

    @Value("${aerospike.readPolicy.readPolicyDefault.totalTimeout}")
    public int AEROSPIKE_R_R_POLICY_TOTAL_TIME;

    @Value("${aerospike.readPolicy.minConnsPerNode}")
    public int AEROSPIKE_R_MIN_CONS_PER_NODE;

    @Value("${aerospike.readPolicy.maxSocketIdle}")
    public int AEROSPIKE_R_MAX_SOCKET_IDLE;

    @Value("${aerospike.expiration.ttl}")
    public int AEROSPIKE_EXPIRATION_TTL;

    @Value("${iod.keep.alive.time}")
    public int IOD_KEEP_ALIVE_TIME;

    @Value("${iod.min.pool.size}")
    public int IOD_MIN_POOL_SIZE;

    @Value("${iod.max.pool.size}")
    public int IOD_MAX_POOL_SIZE;

    public Host[] aerospikeHostList() {
        String[] ipAddress = AEROSPIKE_COMMON_CLIENT_HOST.split("[,]");
        String[] port = AEROSPIKE_COMMON_CLIENT_PORT.split("[,]");
        if (ipAddress.length != port.length) {
            throw new ExceptionInInitializerError("Number of Aerospike hosts doesn't match with number of ports");
            // throw new Exception("Number of host doen't match with number of ports");
        }
        Host[] host = new Host[port.length];
        for (int i = 0; i < port.length; i++) {
            host[i] = new Host(String.valueOf(ipAddress[i]), Integer.parseInt(port[i]));
        }
        return host;
    }

}
