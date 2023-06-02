package dao;

import entity.Number;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E obj);
    List<E> findAll();
    boolean delete(K id);
    void update(E obj);
    Optional<E> findByid(K id);
}
