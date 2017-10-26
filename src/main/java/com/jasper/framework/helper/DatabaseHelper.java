package com.jasper.framework.helper;

import com.jasper.framework.util.CollectionUtil;
import com.jasper.framework.util.PropsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public final class DatabaseHelper {

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final QueryRunner QUERY_RUNNER;
    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        CONNECTION_HOLDER=new ThreadLocal<Connection>();
        QUERY_RUNNER=new QueryRunner();

        String driver=conf.getProperty("jdbc.driver");
        String url=conf.getProperty("jdbc.url");
        String username=conf.getProperty("jdbc.username");
        String password=conf.getProperty("jdbc.password");

        DATA_SOURCE=new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    public static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);

    public static void beginTransaction(){
        Connection connection=getConnection();
        if(connection!=null){
            try{
                connection.setAutoCommit(false);
            }catch (SQLException e){
                LOGGER.error("begin transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void commitTransaction(){
        Connection connection=getConnection();
        if(connection!=null){
            try {
                connection.commit();
                connection.close();
            }catch (SQLException e){
                LOGGER.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void rollbackTransaction(){
        Connection connection=getConnection();
        if(connection!=null){
            try{
                connection.rollback();
                connection.close();
            }catch (SQLException e){
                LOGGER.error("rollback transcation failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static Connection getConnection() {

        Connection connection = CONNECTION_HOLDER.get();
        if (connection == null) {
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object ... params){
        List<T> entityList = null;
        try {
            Connection connection=getConnection();
            entityList =QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e){
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        } finally {

        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass,String sql,Object ... params){
        T entity;
        try{
            Connection connection=getConnection();
            entity=QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        }catch (SQLException e){
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }finally {
        }
        return entity;
    }

    /**
     * 查询语句
     */
    public static List<Map<String,Object>> executeQuery(String sql, Object ... params){
        List<Map<String,Object>> result;
        try{
            Connection connection=getConnection();
            result=QUERY_RUNNER.query(connection,sql,new MapListHandler(),params);
        }catch (Exception e){
            LOGGER.error("execute query failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * update db
     */
    public static int executeUpdate(String sql,Object ... params){
        int rows=0;
        try{
            Connection connection=getConnection();
            rows=QUERY_RUNNER.update(connection,sql,params);
        }catch (SQLException e){
            LOGGER.error("execute update failure",e);
            throw new RuntimeException(e);
        }finally {

        }
        return rows;
    }
    /**
     * insert entity
     */
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can't insert entity cause fieldMap is empty");
            return false;
        }
        String sql="INSERT INTO "+getTableName(entityClass);
        StringBuilder columnsSB=new StringBuilder("(");
        StringBuilder valuesSB=new StringBuilder("(");
        for(String fieldName:fieldMap.keySet()){
            columnsSB.append(fieldName).append(", ");
            valuesSB.append("?, ");
        }
        columnsSB.replace(columnsSB.lastIndexOf(", "),columnsSB.length(),")");
        valuesSB.replace(valuesSB.lastIndexOf(", "),valuesSB.length(),")");
        sql+=columnsSB+" VALUES "+valuesSB;

        Object[] params=fieldMap.values().toArray();
        return executeUpdate(sql,params)==1;
    }

    /**
     * update entity
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(CollectionUtils.isEmpty((Collection<?>) fieldMap)){
            LOGGER.error("can't insert entity:fieldMap is empty");
            return false;
        }
        String sql="UPDATE " + getTableName(entityClass)+" SET ";
        StringBuilder columnsSB=new StringBuilder();
        for(String fieldName:fieldMap.keySet()){
            columnsSB.append(fieldName).append("=?, ");
        }
        sql+=columnsSB.substring(0,columnsSB.lastIndexOf(", "))+" WHERE id=?";
        List<Object> paramList=new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params=paramList.toArray();
        return executeUpdate(sql,params)==1;
    }
    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql="DELETE FROM "+getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql,id)==1;
    }

    /**
     * 读取sql文件
     */
    public static void executeSqlFile(String filePath){
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String sql;
        try {
            while((sql=reader.readLine())!=null){
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (Exception e) {
            LOGGER.error("execute sql file failure",e);
            throw new RuntimeException(e);
        }
    }

}
