package com.ilot.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class CacheUtils {
/*

    private final static String IDENTIFIER  = "token";
    private final static String NODE_PREFIX = "qoe_node";

    */
/*@Autowired
    private RedisTemplate<String, String>     redisTemplateForCaching;
    @Autowired
    private RedisTemplate<String, Object>     redisTemplateForObjectCaching;
    @Autowired
    private RedisTemplate<String, SystemNode> redisNodeTemplate;*//*

    @Autowired
    private FunctionalError                   functionalError;
    @Autowired
    private Environment environment;

    public void cacheString(String key, String value) {
        try {
            redisTemplateForCaching.opsForValue().set(key, value, 12, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCachedString(String key) {
        String value = null;
        try {
            value = redisTemplateForCaching.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void cacheData(String key, Object value) {
        try {
            redisTemplateForObjectCaching.opsForValue().set(key, value, 12, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cacheData(String key, Object value, long timeInMinut) {
        try {
            redisTemplateForObjectCaching.opsForValue().set(key, value, timeInMinut, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cacheData(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplateForObjectCaching.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getCachedData(String key) {
        Object value = null;
        try {
            value = redisTemplateForObjectCaching.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Map<String, Object> getCachedDatas(String matchKey, String keySeparator) {
        Map<String, Object> map = new HashMap<>();
        try {
            Set<String>      keys     = scan(matchKey + keySeparator);
            for (String key : keys) {
                final Object data = getCachedData(key);
                if (data == null) {
                    continue;
                }
                key = key.contains(keySeparator) ? key.split(keySeparator)[1] : key;
                map.put(key, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void deleteCache(String key) {
        try {
            if (Boolean.TRUE.equals(redisTemplateForCaching.hasKey(key))) {
                //jedis.del(key);
                redisTemplateForCaching.delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // use for cron leader
    public void saveNode(String key, SystemNode value) {
        try {
            redisNodeTemplate.opsForValue().set(generateFullNodeKey(key), value, 12, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveNodes(Map<String, SystemNode> nodes) {
        if (nodes == null)
            return;
        for (Entry<String, SystemNode> entry : nodes.entrySet()) {
            saveNode(entry.getKey(), entry.getValue());
        }
    }

    public SystemNode findNode(String key) {
        SystemNode value = null;
        try {
            value = redisNodeTemplate.opsForValue().get(generateFullNodeKey(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public List<SystemNode> findNodes() {
        List<SystemNode> lstToReturn = new ArrayList<SystemNode>();
        try {
            //Set<String> keys = redisNodeTemplate.keys(NODE_PREFIX + "_*");
            Set<String> keys = scan(generateNodePrefixByEnv().concat("__"));
            lstToReturn = redisNodeTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstToReturn;
    }

    public Set<String> scan(String matchKey) {
        Set<String> keys = redisNodeTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String>    keysTmp = new HashSet<>();
            Cursor<byte[]> cursor  = connection.scan(new ScanOptions.ScanOptionsBuilder().match("*" + matchKey + "*").count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
        return keys;
    }

    private String generateNodePrefixByEnv() {
        return NODE_PREFIX.concat("__").concat(currentEnvProfil());
    }

    private String generateFullNodeKey(String key) {
        return generateNodePrefixByEnv().concat("__").concat(key);
    }


    public boolean save(String key, String value, boolean isDelay) {
        return false;
    }


    public void saveValueWithExpiration(String key, Object cmdsTosave, int expiration) {
        try {
            // set to 6Hours / minutes before
            redisTemplateForObjectCaching.opsForValue().set(key, cmdsTosave, expiration, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

    public void saveValueWithExpirationInSecond(String key, Object cmdsTosave, int expiration) {
        try {
            // set to 6Hours / minutes before
            redisTemplateForObjectCaching.opsForValue().set(key, cmdsTosave, expiration, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

    public void saveValueWithExpirationImmediatly(String key, Object cmdsTosave) {
        try {
            redisTemplateForObjectCaching.opsForValue().set(key, cmdsTosave, 1, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

    public void saveValueWithExpirationImmediatly(String key) {
        try {
            redisTemplateForObjectCaching.opsForValue().set(key, "", 1, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

//	public void saveCache(String key, Object cmdsTosave) {
//		try {
//			redisTemplateForObjectCaching.opsForValue().set(key, cmdsTosave, paramConfig.getCacheInterval(), TimeUnit.HOURS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
//		}
//	}

    public void saveValueWithoutExpiration(String key, Object cmdsTosave) {
        try {

            redisTemplateForObjectCaching.opsForValue().set(key, cmdsTosave);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

    public void delete(String key) {
        redisTemplateForObjectCaching.delete(key);
    }

    public void saveValueWithoutExpirationGetway(String key, List<Object> cmdsTosave) {
        try {

            redisTemplateForObjectCaching.opsForList().rightPushAll(key, cmdsTosave);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("saveValueWithExpiration : " + e.getCause(), e.getMessage());
        }
    }

    public Object getValue(String key) {
        Object value = null;
        try {
            value = redisTemplateForObjectCaching.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("getValue : " + e.getCause(), e.getMessage());
        }
        return value;
    }

    public List<Object> getValues(String key) {
        List<Object> value = null;
        try {
            Long size = redisTemplateForObjectCaching.opsForList().size(key);
            value = redisTemplateForObjectCaching.opsForList().range(key, 0, size);
        } catch (Exception e) {
            log.warn("getValue : " + e.getCause(), e.getMessage());
        }
        return value;
    }

    public String getVarSession() {
        String key = null;
        try {
            boolean continuer = true;
            while (continuer) {
                key = Utilities.combinaisonString();
                Object dto = null;
                dto = getValue(key);
                if (dto == null) {
                    continuer = false;
                }
            }
        } catch (Exception e) {
            log.warn("getVarSession : " + e.getCause(), e.getMessage());
        }
        return key;
    }

    public Map<String, Object> getExistingUser(String key) {
        Object value = null;
        try {
            value = redisTemplateForObjectCaching.opsForValue().get(key);
            //       	System.out.println(" key ====> "+key+" value ==> "+value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Map<String, Object>)value;
    }

    private String currentEnvProfil() {
        String activeProfiles = "";
        if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {

            for (int i = 0; i < environment.getActiveProfiles().length; i++) {
                activeProfiles += environment.getActiveProfiles()[i];
            }
        }
        log.info("active profile: " + activeProfiles);
        return activeProfiles;
    }
*/
}
