package com.elevations.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class Dao
{
    private DataSource m_dataSource;

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        m_dataSource = dataSource;
    }
}
