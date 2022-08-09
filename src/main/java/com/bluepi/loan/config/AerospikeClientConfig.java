package com.bluepi.loan.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.CommitLevel;
import com.aerospike.client.policy.WritePolicy;
import com.bluepi.loan.base.IntegratorConstants;

import java.io.IOException;


public class AerospikeClientConfig {

	private static volatile AerospikeClientConfig instance;

	private AerospikeClient aerospikeClientWrite;

	/**
	 * private constructor
	 */
	private AerospikeClientConfig() {

		aerospikeClientWrite = getAeroSpikeClient(getRWPolicy(), IntegratorConstants.AEROSPIKE_HOST);
	}

	/**
	 * @return getInstance and Make singleton from serialize and deserialize
	 * operation.
	 */
	public static AerospikeClientConfig getInstance() throws IOException {

		if (instance == null) {
			synchronized (AerospikeClientConfig.class) {
				if (instance == null) {

					instance = new AerospikeClientConfig();
				}
			}
		}
		return instance;
	}

	private static AerospikeClient getAeroSpikeClient(ClientPolicy policy, Host[] host) {
		return new AerospikeClient(policy, host);
	}


	public AerospikeClient getAerospikeClientWrite() {
		return aerospikeClientWrite;
	}

	public static ClientPolicy getRWPolicy() {

		ClientPolicy rwPolicy = new ClientPolicy();
		WritePolicy writePolicy = new WritePolicy();
//		writePolicy.expiration =IntegratorConstants.getFromYml.AEROSPIKE_EXPIRATION_TTL;
		rwPolicy.writePolicyDefault = writePolicy;
		rwPolicy.writePolicyDefault.sendKey =true;
		rwPolicy.readPolicyDefault.totalTimeout = IntegratorConstants.getFromYml.AEROSPIKE_RW_R_POLICY_TOTAL_TIME;
		rwPolicy.writePolicyDefault.commitLevel = CommitLevel.COMMIT_MASTER;
		rwPolicy.writePolicyDefault.totalTimeout = IntegratorConstants.getFromYml.AEROSPIKE_RW_W_POLICY_TOTAL_TIME;
		rwPolicy.minConnsPerNode = IntegratorConstants.getFromYml.AEROSPIKE_RW_MIN_CONS_PER_NODE;
		rwPolicy.maxSocketIdle = IntegratorConstants.getFromYml.AEROSPIKE_RW_MAX_SOCKET_IDLE;		//maybe we will increase maxConnections to 300
		if(IntegratorConstants.getFromYml.FLAG_FOR_DEV==1){
		// if(!IntegratorConstants.getFromYml.AEROSPIKE_DEV_USERNAME.equalsIgnoreCase("null") && !IntegratorConstants.getFromYml.AEROSPIKE_DEV_PASSWORD.equalsIgnoreCase("null") ) {
			rwPolicy.user=IntegratorConstants.getFromYml.AEROSPIKE_DEV_USERNAME;
			rwPolicy.password=IntegratorConstants.getFromYml.AEROSPIKE_DEV_PASSWORD;
		}


		return rwPolicy;
	}

}
