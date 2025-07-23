package com.stilevo.store.back.stilevo.project.api.service.cache.structure;

import com.stilevo.store.back.stilevo.project.api.service.cache.contract.CacheKeyPairContract;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CacheKeyPairImpl<Key, Value> implements CacheKeyPairContract<Key, Value> {

    private final static int LIMITE_CACHE = 5;
    private Map<Key, Map.Entry<Value, Integer>> structure = new HashMap<>();

    @Override
    public boolean containsKey(Key key) {
        return structure.containsKey(key);
    }

    @Override
    public void incrementFreq(Key key) {
        Map.Entry<Value, Integer> pair = structure.get(key);

        pair.setValue(Integer.sum(pair.getValue(), 1)); // soma a frequencia.
    }

    @Override
    public Value getValueOfKey(Key key) {
        incrementFreq(key);
        return structure.get(key).getKey();
    }

    @Override
    public Integer getFreqOfKey(Key key) {
        return structure.get(key).getValue();
    }

    @Override
    public void add(Key key, Value value) {
        if (containsKey(key)) {
            incrementFreq(key);
            return;
        }

        if (structure.size() > LIMITE_CACHE)
            structure.remove(lastFreq());

        structure.put(key, new AbstractMap.SimpleEntry<Value, Integer> (value, 1) );
    }

    @Override
    public Key lastFreq() {
        Key less = null;
        Integer freq = null;

        for (Key key : structure.keySet()) {
            Integer freqCurrent = getFreqOfKey(key);

            if (freq == null || freq > freqCurrent) {
                freq = freqCurrent;
                less = key;
            }

        }

        return less;
    }

    @Override
    public void remove(Key key) {
        structure.remove(key);
    }

    @Override
    public boolean update(Key key, Value value) {
        if (!containsKey(key))
            return false;

        structure.put(key, new AbstractMap.SimpleEntry<Value, Integer> (value, getFreqOfKey(key)));
        return true;
    }

}
