package utils;

public class IdGenerator {
    private Integer id;

    public IdGenerator() {
        id = 0;
    }

    public Integer id() {
        return id;
    }

    public Integer nextId() {
        id++;
        return id;
    }
}
