package test;

import models.Person;
import services.ServicePerson;
import utils.MyDatabase;

import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) {
        MyDatabase cn1 = MyDatabase.getInstance();

        Person p = new Person("Ben Daoued","Yosra", 22);

        ServicePerson sp = new ServicePerson();

        try {
            //sp.insertOne(p);
            System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }


    }
}
