package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<Tobj> {
    void create(Tobj object) throws SQLException;
    List<Tobj> getAll() throws SQLException;
    Tobj findById(long id) throws SQLException;
    void update(Tobj object) throws SQLException;
    void delete(Tobj object) throws SQLException;
}