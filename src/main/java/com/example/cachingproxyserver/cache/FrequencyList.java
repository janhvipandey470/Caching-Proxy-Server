package com.example.cachingproxyserver.cache;

public class FrequencyList<K,V> {
    CacheNode<K,V>head=new CacheNode<>(null,null);
    CacheNode<K,V>tail=new CacheNode<>(null,null);
    FrequencyList(){
        head.next=tail;
        tail.prev=head;
    }
    public void addFirst(CacheNode<K,V>node){
        CacheNode<K,V>temp=head.next;
        head.next=node;
        node.next=temp;
        node.prev=head;
        temp.prev=node;
    }
    public void remove(CacheNode<K,V>node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
        node.prev=null;
        node.next=null;
    }
    public CacheNode<K, V> removeLast() {
        if (isEmpty()) {
            return null;
        }
        CacheNode<K, V> lastNode = tail.prev;
        remove(lastNode);
        return lastNode;
    }
    public boolean isEmpty(){
        return head.next == tail;
    }

}
