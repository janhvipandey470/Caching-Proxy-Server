package com.example.cachingproxyserver.cache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheNode<K,V> {
    K key;
    V value;
    int frequency;
    CacheNode<K,V> prev;
    CacheNode<K,V> next;
    public CacheNode(K key, V v){
        this.key=key;
        this.value=v;
        this.frequency=1;
        this.next=this.prev=null;
    }

    
}
