package org.example;


import model.City;
import data.CityDaoImpl;

public class App
{
    public static void main( String[] args )
    {
        CityDaoImpl cityDao = new CityDaoImpl();
//        System.out.println(cityDao.findById(939));
//        System.out.println(cityDao.findByCode("ind"));
//        System.out.println(cityDao.findAll());
        City vaxjo = new City(4082,"Växjö","SWE","Kronobergs län",92567);
        System.out.println(cityDao.update(vaxjo));

    }
}
