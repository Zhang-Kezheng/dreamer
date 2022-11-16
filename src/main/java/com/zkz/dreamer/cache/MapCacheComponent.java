package com.zkz.dreamer.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Component
@EnableScheduling
@Slf4j
public class MapCacheComponent<K,V> {
    private final Map<K,MapCacheValue<V>> cacheMap=new ConcurrentHashMap<>();

    public void put(K k,V v){
        put(k,v,-1);
    }
    public void put(K k,V v,long timeout){
        put(k,v,timeout,TimeUnit.MILLISECONDS);
    }
    public void put(K k,V v,long timeout, TimeUnit timeUnit){
        MapCacheValue<V> mapCacheValue=new MapCacheValue<>();
        mapCacheValue.birthTime=System.currentTimeMillis();
        mapCacheValue.value=v;
        mapCacheValue.timeout=timeUnit.toMillis(timeout);
        cacheMap.put(k,mapCacheValue);
    }

    public V get(K k){
        MapCacheValue<V> mapCacheValue = cacheMap.get(k);
        if (mapCacheValue==null){
            return null;
        }
        if (mapCacheValue.timeout!=-1&&System.currentTimeMillis()> mapCacheValue.timeout+mapCacheValue.birthTime){
            cacheMap.remove(k);
            return null;
        }
        return mapCacheValue.value;
    }



    @Scheduled(fixedRate = 100)
    private void delete(){
        cacheMap.values().removeIf(new Predicate<MapCacheValue<V>>() {
            @Override
            public boolean test(MapCacheValue<V> vMapCacheValue) {
                return vMapCacheValue.timeout!=-1&&System.currentTimeMillis()> vMapCacheValue.timeout+vMapCacheValue.birthTime;
            }
        });
    }
    @Data
    public static class MapCacheValue<V>{
        private transient volatile  V value;
        private long timeout;
        private long birthTime;
    }

}
