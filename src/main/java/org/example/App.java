package org.example;


import org.example.model.City;
import org.example.data.CityDaoImpl;

public class App
{
    public static void main( String[] args )
    {
        CityDaoImpl cityDao = new CityDaoImpl();
//        System.out.println(cityDao.findById(939));
//        System.out.println(cityDao.findByCode("ind"));
//        System.out.println(cityDao.findAll());
        City vaxjo = new City(4083,"Helsingborg","SWE","Skåne län",108334);
        System.out.println(cityDao.delete(vaxjo));

    }
}
