package com.stilevo.store.back.stilevo.project.api.service.cache.contract;

public interface CacheKeyPairContract<Key, Value> {

    boolean containsKey(Key key);

    void incrementFreq(Key key);

    Value getValueOfKey(Key key);

    Integer getFreqOfKey(Key key);

    void add(Key key, Value value);

    Key lastFreq();

    void remove(Key key);

    boolean update(Key key, Value value);
}
