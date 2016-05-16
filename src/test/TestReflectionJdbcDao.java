package test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import main.DAOFactory;
import main.ReflectionJdbcDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;


public class TestReflectionJdbcDao {
    private ReflectionJdbcDao<Movie> reflectionJdbcDao;
    private DataSource dataSource = null;

    /**
     * Method that returns {@link DataSource} of the database that specified in db.properties file.
     * To create database and table you may run test.sql script of MySQL.
     *
     * @return {@link DataSource} of the database
     */
    private DataSource getDataSource() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        MysqlDataSource mysqlDataSource = null;
        try {
            inputStream = new FileInputStream("db.properties");
            properties.load(inputStream);
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDataSource;
    }

    @Before
    public void configure() {
        dataSource = getDataSource();
        DAOFactory.createFactory(this.dataSource);
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.reflectionJdbcDao = daoFactory.getReflectionJdbcDao(Movie.class);
        Connection connection;
        try {
            connection = dataSource.getConnection();
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Movies")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        ArrayList<Movie> args = new ArrayList<>(Arrays.asList(
                new Movie(2, "Terminator", new Date(1000), "I'll be back"),
                new Movie(1, "Titanic", new Date(2000), "Jack"),
                new Movie(4, "Iron man", new Date(3000), "Back in black")
        ));
        args.forEach(m -> reflectionJdbcDao.insert(m));
        args.forEach(m -> Assert.assertEquals(m, reflectionJdbcDao.selectByKey(m)));
    }

    @Test
    public void testUpdate() {
        ArrayList<Movie> args = new ArrayList<>(Arrays.asList(
                new Movie(2, "Terminator 2", new Date(1000), "I'll be back"),
                new Movie(1, "Titanic 2", new Date(2000), "Jack"),
                new Movie(4, "Iron man 2", new Date(3000), "Back in black")
        ));
        args.forEach(m -> reflectionJdbcDao.update(m));
        args.forEach(m -> Assert.assertEquals(m, reflectionJdbcDao.selectByKey(m)));
    }

    @Test
    public void testDeleteByKey() {
        ArrayList<Movie> args = new ArrayList<>(Arrays.asList(
                new Movie(2, "Terminator 2", new Date(1000), "I'll be back"),
                new Movie(1, "Titanic 2", new Date(2000), "Jack"),
                new Movie(4, "Iron man 2", new Date(3000), "Back in black")
        ));
        args.forEach(m -> reflectionJdbcDao.insert(m));
        reflectionJdbcDao.deleteByKey(args.get(0));
        args.remove(0);
        Assert.assertEquals(args, reflectionJdbcDao.selectAll());
    }

    @Test
    public void testSelectByKey() {
        ArrayList<Movie> args = new ArrayList<>(Arrays.asList(
                new Movie(1, "Titanic", new Date(2000), "Jack"),
                new Movie(2, "Terminator", new Date(1000), "I'll be back"),
                new Movie(4, "Iron man", new Date(3000), "Back in black")
        ));
        args.forEach(m -> reflectionJdbcDao.insert(m));
        args.forEach(m -> Assert.assertEquals(m, reflectionJdbcDao.selectByKey(m)));
    }

    @Test
    public void testSelectAll() {
        ArrayList<Movie> args = new ArrayList<>(Arrays.asList(
                new Movie(1, "Titanic", new Date(2000), "Jack"),
                new Movie(2, "Terminator", new Date(1000), "I'll be back"),
                new Movie(4, "Iron man", new Date(3000), "Back in black")
        ));
        args.forEach(m -> reflectionJdbcDao.insert(m));
        Assert.assertEquals(args, reflectionJdbcDao.selectAll());
    }

    @After
    public void clean() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Movies")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
