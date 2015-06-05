/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.apache.airavata.registry.core.experiment.catalog;

import org.apache.airavata.registry.core.experiment.catalog.model.*;
import org.apache.airavata.registry.core.experiment.catalog.resources.*;
import org.apache.airavata.registry.core.experiment.catalog.utils.QueryGenerator;
import org.apache.airavata.registry.cpi.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpCatResourceUtils {
    private final static Logger logger = LoggerFactory.getLogger(ExpCatResourceUtils.class);
    private static final String PERSISTENCE_UNIT_NAME = "experiment_data";
    @PersistenceContext(unitName="experiment_data")
    protected static EntityManagerFactory factory;

    public static void reset(){
    	factory=null;
    }
    
    public static EntityManager getEntityManager(){
        if (factory == null) {
            String connectionProperties = "DriverClassName=" + Utils.getJDBCDriver() + "," + "Url=" + Utils.getJDBCURL() + "?autoReconnect=true,," +
                    "Username=" + Utils.getJDBCUser() + "," + "Password=" + Utils.getJDBCPassword() + ",validationQuery=" +
            Utils.getValidationQuery();
            System.out.println(connectionProperties);
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("openjpa.ConnectionDriverName", "org.apache.commons.dbcp.BasicDataSource");
            properties.put("openjpa.ConnectionProperties", connectionProperties);
            properties.put("openjpa.DynamicEnhancementAgent", "true");
            properties.put("openjpa.RuntimeUnenhancedClasses", "unsupported");
            properties.put("openjpa.DataCache","" + Utils.isCachingEnabled() + "(CacheSize=" + Utils.getJPACacheSize() + ", SoftReferenceSize=0)");
            properties.put("openjpa.QueryCache","" + Utils.isCachingEnabled() + "(CacheSize=" + Utils.getJPACacheSize() + ", SoftReferenceSize=0)");
            properties.put("openjpa.RemoteCommitProvider","sjvm");
            properties.put("openjpa.Log","DefaultLevel=INFO, Runtime=INFO, Tool=INFO, SQL=INFO");
            properties.put("openjpa.jdbc.DBDictionary","SupportsMultipleNontransactionalResultSets=false");
//            properties.put("openjpa.ReadLockLevel", "none");
//            properties.put("openjpa.WriteLockLevel", "none");
//            properties.put("openjpa.LockTimeout", "30000");
//            properties.put("openjpa.LockManager", "none");
            properties.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
            properties.put("openjpa.ConnectionFactoryProperties", "PrettyPrint=true, PrettyPrintLineLength=72, PrintParameters=true, MaxActive=10, MaxIdle=5, MinIdle=2, MaxWait=31536000,  autoReconnect=true");
			properties.put("openjpa.jdbc.QuerySQLCache", "false");
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        }
		return factory.createEntityManager();
    }

    /**
     * @param gatewayId
     * @return
     */
    public static ExperimentCatResource createGateway(String gatewayId) throws RegistryException {
        if (!isGatewayExist(gatewayId)) {
            GatewayResource gatewayResource = new GatewayResource();
            gatewayResource.setGatewayId(gatewayId);
            return gatewayResource;
        }else {
            return getGateway(gatewayId);
        }
    }

    public static UserResource createUser(String username, String password) throws RegistryException {
        if (!isUserExist(username)) {
            UserResource userResource = new UserResource();
            userResource.setUserName(username);
            userResource.setPassword(password);
            return userResource;
        }else {
            return (UserResource)getUser(username);
        }

    }

    public static ExperimentCatResource getGateway(String gatewayId) throws RegistryException{
        EntityManager em = null;
        try {
            if (isGatewayExist(gatewayId)) {
                em = getEntityManager();
                Gateway gateway = em.find(Gateway.class, gatewayId);
                GatewayResource gatewayResource = (GatewayResource)Utils.getResource(ResourceType.GATEWAY, gateway);
                em.close();
                return gatewayResource;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return null;
    }

    public static void addUser (String userName, String password) throws RegistryException{
        UserResource resource = new UserResource();
        resource.setUserName(userName);
        resource.setPassword(password);
        resource.save();
    }

    public static boolean isUserExist (String username) throws RegistryException{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.USERS);
            generator.setParameter(AbstractExpCatResource.UserConstants.USERNAME, username);
            Query q = generator.selectQuery(em);
            int size = q.getResultList().size();
            em.getTransaction().commit();
            em.close();
            return size>0;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }


    public static ExperimentCatResource getUser(String userName) throws RegistryException{
        EntityManager em = null;
        try {
            if (isUserExist(userName)) {
                em = getEntityManager();
                Users user =  em.find(Users.class, userName);
                UserResource userResource = (UserResource)Utils.getResource(ResourceType.USER, user);
                em.close();
                return userResource;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return null;

    }

    public static ExperimentCatResource getWorker(String gatewayId, String userName) throws RegistryException{
        EntityManager em = null;
        try {
            em = getEntityManager();
            Gateway_Worker gatewayWorker = em.find(Gateway_Worker.class, new Gateway_Worker_PK(gatewayId, userName));
            WorkerResource workerResource = (WorkerResource) Utils.getResource(ResourceType.GATEWAY_WORKER, gatewayWorker);
            em.close();
            return workerResource;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }


    }


    /**
     * @param gatewayId
     * @return
     */
    public static boolean isGatewayExist(String gatewayId) throws RegistryException{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.GATEWAY);
            generator.setParameter(AbstractExpCatResource.GatewayConstants.GATEWAY_ID, gatewayId);
            Query q = generator.selectQuery(em);
            int size = q.getResultList().size();
            em.getTransaction().commit();
            em.close();
            return size>0;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

    }

    public static List<ExperimentCatResource> getAllGateways() throws RegistryException{
        List<ExperimentCatResource> resourceList = new ArrayList<ExperimentCatResource>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.GATEWAY);
            Query q = generator.selectQuery(em);
            List results = q.getResultList();
            if (results.size() != 0) {
                for (Object result : results) {
                    Gateway gateway = (Gateway) result;
                    GatewayResource gatewayResource =
                            (GatewayResource) Utils.getResource(ResourceType.GATEWAY, gateway);
                    resourceList.add(gatewayResource);
                }
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return resourceList;
    }

    /**
     * @param gatewayId
     * @return
     */
    public static boolean removeGateway(String gatewayId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.GATEWAY);
            generator.setParameter(AbstractExpCatResource.GatewayConstants.GATEWAY_ID, gatewayId);
            Query q = generator.deleteQuery(em);
            q.executeUpdate();
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    /**
     * @param gatewayResource
     * @param userResource
     */
    public static WorkerResource addGatewayWorker(GatewayResource gatewayResource, UserResource userResource) throws RegistryException{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            if (!isGatewayExist(gatewayResource.getGatewayName())){
                gatewayResource.save();
            }
            if (!isUserExist(userResource.getUserName())){
                userResource.save();
            }
            Gateway gateway = em.find(Gateway.class, gatewayResource.getGatewayId());
            Users user = em.find(Users.class, userResource.getUserName());
            Gateway_Worker gatewayWorker = new Gateway_Worker();
            gatewayWorker.setGateway(gateway);
            gatewayWorker.setUser(user);
            em.persist(gatewayWorker);
            em.getTransaction().commit();
            em.close();
            return (WorkerResource)Utils.getResource(ResourceType.GATEWAY_WORKER, gatewayWorker);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    /**
     * @param gatewayResource
     * @param userResource
     * @return
     */
    public static boolean removeGatewayWorker(GatewayResource gatewayResource, UserResource userResource) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.GATEWAY_WORKER);
            generator.setParameter(AbstractExpCatResource.GatewayWorkerConstants.GATEWAY_ID,
                    gatewayResource.getGatewayName());
            generator.setParameter(AbstractExpCatResource.UserConstants.USERNAME, userResource.getUserName());
            Query q = generator.deleteQuery(em);
            q.executeUpdate();
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

    }

    /**
     * @param configKey
     * @return
     */
    public static List<ConfigurationResource> getConfigurations(String configKey){
        List<ConfigurationResource> list = new ArrayList<ConfigurationResource>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QueryGenerator generator = new QueryGenerator(AbstractExpCatResource.CONFIGURATION);
            generator.setParameter(AbstractExpCatResource.ConfigurationConstants.CONFIG_KEY, configKey);
            Query q = generator.selectQuery(em);
            List<?> resultList = q.getResultList();
            if (resultList.size() != 0) {
                for (Object result : resultList) {
                    ConfigurationResource configurationResource = createConfigurationResourceObject(result);
                    list.add(configurationResource);
                }
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return list;
    }

    /**
     * @param configKey
     * @return
     */
    public static ConfigurationResource getConfiguration(String configKey){
        List<ConfigurationResource> configurations = getConfigurations(configKey);
        return (configurations != null && configurations.size() > 0) ? configurations.get(0) : null;
    }

    /**
     * @param configKey
     * @return
     */
    public static boolean isConfigurationExist(String configKey){
        List<ConfigurationResource> configurations = getConfigurations(configKey);
        return (configurations != null && configurations.size() > 0);
    }

    /**
     * @param configKey
     * @return
     */
    public static ConfigurationResource createConfiguration(String configKey) {
        ConfigurationResource config = new ConfigurationResource();
        config.setConfigKey(configKey);
        return config;
    }

    /**
     * @param result
     * @return
     */
    private static ConfigurationResource createConfigurationResourceObject(
            Object result) {
        Configuration configuration = (Configuration) result;
        ConfigurationResource configurationResource = new ConfigurationResource(configuration.getConfig_key(), configuration.getConfig_val());
        configurationResource.setExpireDate(configuration.getExpire_date());
        return configurationResource;
    }

    /**
     * @param configkey
     * @param configValue
     */
    public static void removeConfiguration(String configkey, String configValue) throws RegistryException{
        QueryGenerator queryGenerator = new QueryGenerator(AbstractExpCatResource.CONFIGURATION);
        queryGenerator.setParameter(AbstractExpCatResource.ConfigurationConstants.CONFIG_KEY, configkey);
        queryGenerator.setParameter(AbstractExpCatResource.ConfigurationConstants.CONFIG_VAL, configValue);
        EntityManager em = null;
        try {
            if(isConfigurationExists(configkey, configValue)){
                em = getEntityManager();
                em.getTransaction().begin();
                Query q = queryGenerator.deleteQuery(em);
                q.executeUpdate();
                em.getTransaction().commit();
                em.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    /**
     * @param configkey
     */
    public static void removeConfiguration(String configkey) throws RegistryException{
        QueryGenerator queryGenerator = new QueryGenerator(AbstractExpCatResource.CONFIGURATION);
        queryGenerator.setParameter(AbstractExpCatResource.ConfigurationConstants.CONFIG_KEY, configkey);
        EntityManager em = null;
        try {
            if(isConfigurationExist(configkey)){
                em = getEntityManager();
                em.getTransaction().begin();
                Query q = queryGenerator.deleteQuery(em);
                q.executeUpdate();
                em.getTransaction().commit();
                em.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public static boolean isConfigurationExists(String configKey, String configVal) throws RegistryException{
        EntityManager em = null;
        try{
            //Currently categoryID is hardcoded value
            em = ExpCatResourceUtils.getEntityManager();
            Configuration existing = em.find(Configuration.class, new Configuration_PK(configKey, configVal, AbstractExpCatResource.ConfigurationConstants.CATEGORY_ID_DEFAULT_VALUE));
            em.close();
            return existing!= null;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }
}
