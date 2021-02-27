package dao.utils;

import java.sql.*;
import java.util.Optional;

public class DaoUtils {
    public static Optional<Long> GetKey(ResultSet rs, String colName) throws SQLException {
        long val = rs.getLong(colName);
        return rs.wasNull() ? null : Optional.ofNullable(val);
    }

    public static Optional<Long> GetKey(ResultSet rs, int colIndex) throws SQLException {
        long val = rs.getLong(colIndex);
        return rs.wasNull() ? null : Optional.ofNullable(val);
    }
}
