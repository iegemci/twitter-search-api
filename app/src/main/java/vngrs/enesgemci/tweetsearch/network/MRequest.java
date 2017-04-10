package vngrs.enesgemci.tweetsearch.network;

import java.util.List;

import rx.Observable;

/**
 * Created by enesgemci on 08/04/2017.
 */

public final class MRequest<T> {

    private final Observable<T> observable;
    private final ServiceConstant serviceConstant;
    private final List<String> headers;

    public MRequest(Observable<T> observable, ServiceConstant serviceConstant) {
        this(observable, serviceConstant, null);
    }

    public MRequest(Observable<T> observable, ServiceConstant serviceConstant, List<String> headers) {
        this.observable = observable;
        this.serviceConstant = serviceConstant;
        this.headers = headers;
    }

    public Observable<T> getObservable() {
        return observable;
    }

    public ServiceConstant getServiceConstant() {
        return serviceConstant;
    }

    public List<String> getHeaders() {
        return headers;
    }
}