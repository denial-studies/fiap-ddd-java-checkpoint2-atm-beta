package model.interfaces;

public interface Autorizavel {
    public Boolean autorizar(String senha);

    public Boolean isBloqueado();
}
