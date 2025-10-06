package com.demuelle.fake_football.utils;

import com.demuelle.fake_football.dto.Team;

import java.util.Map;
import java.util.Set;

public class MapUtils {
    public static Team partialKeyLookup(Map<String, Team> map, String partialKey) {
        Set<String> keys = map.keySet();
        Team returnVal = null;
        for (String key: keys) {
            if (key.toLowerCase().contains(partialKey.toLowerCase())) {
                returnVal = map.get(key);
            }
        }
        return returnVal;
    }
}
