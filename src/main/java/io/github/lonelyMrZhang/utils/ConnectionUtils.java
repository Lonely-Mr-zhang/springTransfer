package io.github.lonelyMrZhang.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 连接的工具类，它用于从数据源中获取一个连接，并实现和线程的绑定
 * @author: lonely.mr.zhang
 * @date: 2020/6/17 11:06 上午
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getThreadConnection(){
        try {
            //1、先从ThreadLocal上获取
            Connection connection = threadLocal.get();
            //2、判断当前线程上是否有连接
            if (connection == null){
                //3、从数据源中获取一个连接，并且存入ThreadLocal中
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        threadLocal.remove();
    }
}
