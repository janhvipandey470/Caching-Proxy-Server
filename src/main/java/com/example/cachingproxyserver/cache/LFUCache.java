package com.example.cachingproxyserver.cache;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class LFUCache<K,V> {
    private static final Logger logger =
            LoggerFactory.getLogger(LFUCache.class);
    private final int capacity;
    private int minFrequency;
    private int currentSize;
    private final ConcurrentHashMap<K,CacheNode<K,V>>cache= new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer,FrequencyList<K,V>>freqMap= new ConcurrentHashMap<>();


    public boolean isContain(K key){
        return cache.containsKey(key);
    }
    private void updateFreq( CacheNode<K,V>node){
        int nodeFreq= node.frequency;
        FrequencyList<K,V>oldList=freqMap.get(nodeFreq);
        oldList.remove(node);
        if(minFrequency==nodeFreq&&oldList.isEmpty()){
            minFrequency++;
        }
        if(oldList.isEmpty()){
            freqMap.remove(nodeFreq);
        }
        node.frequency++;
        if(freqMap.containsKey(node.frequency)){
            freqMap.get(node.frequency).addFirst(node);
        }else{
            FrequencyList<K,V>list=new FrequencyList<>();
            list.addFirst(node);
            freqMap.put(node.frequency,list);
        }

    }
    public V get(K key){
        if(!isContain(key))return null;
        CacheNode<K,V>node=cache.get(key);
        updateFreq(node);
        return node.value;

    }
    private void evict(){
        FrequencyList<K,V>list=freqMap.get(minFrequency);
        CacheNode<K,V>node=list.removeLast();
        cache.remove(node.key);
        currentSize--;
        if(list.isEmpty()){
            freqMap.remove(minFrequency);
        }logger.info("Evicting key: {} with frequency: {}", node.getKey(), node.getFrequency());
    }
    public void put(K key,V value){
        if(capacity==0)return;
        if(cache.containsKey(key)){
            CacheNode<K,V>node=cache.get(key);
            node.value=value;
            updateFreq(node);
            return;
        }
        if(currentSize==capacity){
            evict();
        }
        CacheNode<K,V>node=new CacheNode<>(key,value);
        if(freqMap.containsKey(1)){
            freqMap.get(1).addFirst(node);
        }
        else {
            FrequencyList<K,V>list=new FrequencyList<>();
            list.addFirst(node);
            freqMap.put(1,list);
        }
        cache.put(key,node);
        currentSize++;
        minFrequency=1;
        return ;

    }


    public V remove(K key) {
        CacheNode<K,V>node=cache.get(key);
        if(node==null){
            return null;
        }
        int nodeFreq= node.frequency;
        FrequencyList<K,V>oldList=freqMap.get(nodeFreq);
        oldList.remove(node);
        if(minFrequency==nodeFreq&&oldList.isEmpty()){
            minFrequency++;
        }
        if(oldList.isEmpty()){
            freqMap.remove(nodeFreq);
            if(currentSize==1){
                minFrequency=0;
            }
        }
        cache.remove(key);
        currentSize--;

        return node.value;
    }
}
