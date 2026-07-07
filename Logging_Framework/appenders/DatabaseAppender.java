package appenders;

import core.logAppender;
import core.logFormatter;
import core.logLevel;
import core.logMessage;
import formatters.SimpleFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseAppender implements logAppender {
    private logLevel level;
    private logFormatter formatter;
    private Connection connection;
    private String tableName;
    private PreparedStatement insertStatement;

    public DatabaseAppender(String tableName,logLevel level){
        this.level=level;
        this.connection=null;
        this.tableName=tableName;
        this.formatter=new SimpleFormatter();
    }

    public DatabaseAppender(String tableName){
        this(tableName, logLevel.DEBUG);
    }

    public void setConnection(Connection connection){
        this.connection=connection;
        initializeStatement();
    }

    private void initializeStatement(){
        if(connection!=null){
            try {
                String sql = "INSERT INTO " + tableName + " (timestamp, level, message, source) VALUES (?, ?, ?, ?)";
                insertStatement=connection.prepareStatement(sql);
            } catch (SQLException e) {
                System.err.println("Failed to initialize DatabaseAppender: " + e.getMessage());
            }
        }
    }

    @Override
    public void append(logMessage message){
        if(!isEnabled(message.getLevel()) || connection==null || insertStatement==null){
            return;
        }

        try {
            insertStatement.setTimestamp(1, java.sql.Timestamp.from(message.getTimestamp()));
            insertStatement.setString(2, message.getLevel().toString());
            insertStatement.setString(3,message.getMessage());
            insertStatement.setString(4, message.getSource());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to write to database: " + e.getMessage());
        }
    }

    @Override
    public void setLevel(logLevel level){
        this.level=level;
    }

    @Override
    public logLevel getLevel(){
        return level;
    }

    @Override
    public boolean isEnabled(logLevel level){
        return level.isGreaterOrEqual(this.level);
    }

    @Override
    public void setFormatter(logFormatter formatter){
        this.formatter=formatter;
    }

    @Override
    public logFormatter getFormatter(){
        return formatter; 
    }

    public String getTableName(){
        return tableName;
    }

    public void close(){
        if(insertStatement!=null){
            try {
                insertStatement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close statement: " + e.getMessage());
            }
        }
    }
}
