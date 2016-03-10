/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.core;

import java.util.Collection;

import io.netty.util.concurrent.Future;
/**
 * Base asynchronous MultiMap interface. A collection that maps multiple values per one key.
 *
 * @author Nikita Koksharov
 *
 * @param <K> key
 * @param <V> value
 */

public interface RMultimapAsync<K, V> extends RExpirableAsync {

    /**
     * Returns the number of key-value pairs in this multimap.
     *
     * @return
     */
    Future<Integer> sizeAsync();

    /**
     * Returns {@code true} if this multimap contains at least one key-value pair
     * with the key {@code key}.
     */
    Future<Boolean> containsKeyAsync(Object key);

    /**
     * Returns {@code true} if this multimap contains at least one key-value pair
     * with the value {@code value}.
     */
    Future<Boolean> containsValueAsync(Object value);

    /**
     * Returns {@code true} if this multimap contains at least one key-value pair
     * with the key {@code key} and the value {@code value}.
     */
    Future<Boolean> containsEntryAsync(Object key, Object value);

    /**
     * Stores a key-value pair in this multimap.
     *
     * <p>Some multimap implementations allow duplicate key-value pairs, in which
     * case {@code put} always adds a new key-value pair and increases the
     * multimap size by 1. Other implementations prohibit duplicates, and storing
     * a key-value pair that's already in the multimap has no effect.
     *
     * @return {@code true} if the method increased the size of the multimap, or
     *     {@code false} if the multimap already contained the key-value pair and
     *     doesn't allow duplicates
     */
    Future<Boolean> putAsync(K key, V value);

    /**
     * Removes a single key-value pair with the key {@code key} and the value
     * {@code value} from this multimap, if such exists. If multiple key-value
     * pairs in the multimap fit this description, which one is removed is
     * unspecified.
     *
     * @return {@code true} if the multimap changed
     */
    Future<Boolean> removeAsync(Object key, Object value);

    // Bulk Operations

    /**
     * Stores a key-value pair in this multimap for each of {@code values}, all
     * using the same key, {@code key}. Equivalent to (but expected to be more
     * efficient than): <pre>   {@code
     *
     *   for (V value : values) {
     *     put(key, value);
     *   }}</pre>
     *
     * <p>In particular, this is a no-op if {@code values} is empty.
     *
     * @return {@code true} if the multimap changed
     */
    Future<Boolean> putAllAsync(K key, Iterable<? extends V> values);

    /**
     * Stores a collection of values with the same key, replacing any existing
     * values for that key.
     *
     * <p>If {@code values} is empty, this is equivalent to
     * {@link #removeAll(Object) removeAll(key)}.
     *
     * @return the collection of replaced values, or an empty collection if no
     *     values were previously associated with the key. The collection
     *     <i>may</i> be modifiable, but updating it will have no effect on the
     *     multimap.
     */
    Future<Collection<V>> replaceValuesAsync(K key, Iterable<? extends V> values);

    /**
     * Removes all values associated with the key {@code key}.
     *
     * <p>Once this method returns, {@code key} will not be mapped to any values,
     * so it will not appear in {@link #keySet()}, {@link #asMap()}, or any other
     * views.
     *
     * @return the values that were removed (possibly empty). The returned
     *     collection <i>may</i> be modifiable, but updating it will have no
     *     effect on the multimap.
     */
    Future<Collection<V>> removeAllAsync(Object key);

    Future<Collection<V>> getAllAsync(K key);

    /**
     * Removes <code>keys</code> from map by one operation
     *
     * Works faster than <code>removeAll</code> but not returning
     * the value associated with <code>key</code>
     *
     * @param keys
     * @return the number of keys that were removed from the hash, not including specified but non existing keys
     */
    Future<Long> fastRemoveAsync(K ... keys);


}
