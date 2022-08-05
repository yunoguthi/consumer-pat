package br.com.alelo.consumer.consumerpat.exception;

public class PurchaseNotAllowedException extends Exception {

    public PurchaseNotAllowedException() {
    }

    public PurchaseNotAllowedException(String message) {
        super(message);
    }

    public PurchaseNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurchaseNotAllowedException(Throwable cause) {
        super(cause);
    }

}
