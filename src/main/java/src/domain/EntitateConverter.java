package domain;

public abstract class EntitateConverter<T extends Entitate> {
    public abstract String toString(T entitate);
    public abstract T fromString(String string);
}
