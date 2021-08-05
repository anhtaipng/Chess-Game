package vn.gihot.chess.admin.core;

import java.util.List;

public interface IService<T> {

    T create(T entity);

    T findById(String id);

    List<T> findAll();

    boolean update(T entity);

    void delete(String id);
}
