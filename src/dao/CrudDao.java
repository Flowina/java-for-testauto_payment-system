package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T, U> {
    void create(T object) throws SQLException;
    List<T> getAll() throws SQLException;
    T findById(U id) throws SQLException;
    void update(T object) throws SQLException;
    void delete(T object) throws SQLException;
}