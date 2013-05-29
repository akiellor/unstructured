package unstructured;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

class ImmutableMaps {
    public static ImmutableMap<Object, Object> merge(
            ImmutableMap<? extends Object, ? extends Object> one,
            ImmutableMap<? extends Object, ? extends Object> two) {

        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
        for(Object key : Sets.difference(two.keySet(), one.keySet())){
            builder.put(key, two.get(key));
        }
        for(Object key : Sets.difference(one.keySet(), two.keySet())){
            builder.put(key, one.get(key));
        }
        for(Object key : Sets.intersection(one.keySet(), two.keySet())){
            Object oneValue = one.get(key);
            Object twoValue = two.get(key);
            if(oneValue instanceof ImmutableMap && twoValue instanceof ImmutableMap){
                builder.put(key, merge(
                        (ImmutableMap<Object, Object>) oneValue,
                        (ImmutableMap<Object, Object>) twoValue));
            }else{
                builder.put(key, twoValue);
            }
        }
        return builder.build();
    }
}
