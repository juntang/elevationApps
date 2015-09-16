package com.elevations;

import com.elevations.dao.RoadDAO;
import org.skife.jdbi.v2.DBI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;


@SpringBootApplication
public class Application
{
    public static void main( String[] args )
    {
        SpringApplication.run( Application.class, args );
        ApplicationContext context = new ClassPathXmlApplicationContext( "context.xml" );
        DataSource dataSource = ( DataSource ) context.getBean( "dataSource" );
        DBI dbi = new DBI( dataSource );
        RoadDAO dao = dbi.onDemand( RoadDAO.class );
        System.out.println( dao.getRoadsNearPoint( 16839484.09, -3997003.76, 10 ) );
    }
}
