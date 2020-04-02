package ru.sharipov.podcaster.i_network.service;

import io.reactivex.Observable;

import ru.sharipov.podcaster.i_network.error.server.BaseServerException;
import ru.sharipov.podcaster.i_network.error.handler.BaseErrorHandler;
import ru.sharipov.podcaster.i_network.network.error.HttpCodes;

/**
 * Бзовый класс для работы с api
 */
public class BaseNetworkService {

    protected <T> Observable<T> call(Observable<T> observable, BaseErrorHandler errorHandler) {
        return observable.onErrorResumeNext((Throwable throwable) -> handleError(throwable, errorHandler));
    }

    private <T> Observable<T> handleError(Throwable throwable, BaseErrorHandler errorHandler) {
        if (throwable instanceof BaseServerException) {
            BaseServerException httpException = (BaseServerException) throwable;
            int httpCode = httpException.getServerCode();

            if (httpCode == HttpCodes.CODE_400) {
                errorHandler.handle(httpException);
            }
        }

        return Observable.error(throwable);
    }
}