package data;

import data.CityDao;
import model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static data.Data.getConnection;

public class CityDaoImpl implements CityDao {

    private static final String FIND_BY_ID = "SELECT*FROM city WHERE city.ID = ?";
    private static final String FIND_BY_COUNTRY_CODE = "SELECT*FROM city WHERE city.CountryCode = ?";
    private static final String FIND_BY_NAME = "SELECT*FROM city WHERE city.Name = ?";
    private static final String FIND_ALL = "SELECT*FROM city";
    private static final String INSERT_CITY = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?)";
    private static final String UPDATE_CITY = "UPDATE city SET Name=?,CountryCode=?,District=?,Population=? WHERE city.ID=?";
    private static final String DELETE_CITY = "DELETE FROM city WHERE city.ID = ?";

    @Override
    public City findById(int id) {
        City city = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = createFindById(connection,id);
            ResultSet rs = statement.executeQuery()
        ){
            while(rs.next()){
                city = createCityFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    private PreparedStatement createFindById (Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
        return statement;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = createFindByCode(connection,code);
            ResultSet rs = statement.executeQuery()
        ){
           while(rs.next()){
               cityList.add(createCityFromResultSet(rs));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    private PreparedStatement createFindByCode (Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_COUNTRY_CODE);
        statement.setString(1,code);
        return statement;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = createFindByName(connection,name);
            ResultSet rs = statement.executeQuery()
        ){
            while(rs.next()){
                cityList.add(createCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    private PreparedStatement createFindByName (Connection connection, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name);
        return statement;
    }

    @Override
    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = createFindAll(connection);
            ResultSet rs = statement.executeQuery()
        ){
            while(rs.next()){
                cityList.add(createCityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }

    private PreparedStatement createFindAll (Connection connection) throws SQLException {
        return connection.prepareStatement(FIND_ALL);
    }

    @Override
    public City update(City city) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CITY)
        ){
            statement.setString(1,city.getName());
            statement.setString(2,city.getCountryCode());
            statement.setString(3,city.getDistrict());
            statement.setInt(4,city.getPopulation());
            statement.setInt(5,city.getCityId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CITY)
        ){
            statement.setInt(1,city.getCityId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private City createCityFromResultSet(ResultSet rs) throws SQLException {
        return new City(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5));
    }

    @Override
    public City add(City city) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement(INSERT_CITY,statement.RETURN_GENERATED_KEYS);
            statement.setString(1,city.getName());
            statement.setString(2,city.getCountryCode());
            statement.setString(3,city.getDistrict());
            statement.setInt(4,city.getPopulation());
            statement.execute();
            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                city = new City(
                    keySet.getInt(1),
                    city.getName(),
                    city.getCountryCode(),
                    city.getDistrict(),
                    city.getPopulation()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if (keySet != null){
                    keySet.close();
                }
                if (statement != null){
                    statement.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return city;
    }
}
