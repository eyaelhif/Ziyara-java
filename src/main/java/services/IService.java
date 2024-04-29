package services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    //4 methodes de CRUD
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(T t) throws SQLException;
    List<T> afficher() throws SQLException;

}
