package com.nytimes.android.external.store.util;

import android.support.annotation.NonNull;

import com.nytimes.android.external.store.base.Persister;
import com.nytimes.android.external.store.base.impl.BarCode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Observable;

/**
 * Pass-through diskdao for stores that don't want to use persister
 */
public class NoopPersister<Raw> implements Persister<Raw> {
    private final ConcurrentMap<BarCode, Raw> networkResponses = new ConcurrentHashMap<>();

    @NonNull
    @Override
    public Observable<Raw> read(BarCode barCode) {
        Raw raw = networkResponses.get(barCode);
        return raw == null ? Observable.<Raw>empty() : Observable.just(raw);
    }

    @NonNull
    @Override
    public Observable<Boolean> write(BarCode barCode, Raw raw) {
        networkResponses.put(barCode, raw);
        return Observable.just(true);
    }
}
