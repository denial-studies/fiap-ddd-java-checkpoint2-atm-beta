package model.exceptions;

public class AcessoBloqueadoException extends RuntimeException {
    public AcessoBloqueadoException(String msg) {
        super(msg);
    }
}
