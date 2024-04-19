package Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    public void create(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    public List <T> read () throws SQLException;
    public void update(T t) throws SQLException;
}