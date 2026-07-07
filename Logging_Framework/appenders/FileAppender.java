package appenders;

import core.logAppender;
import core.logFormatter;
import core.logLevel;
import core.logMessage;
import formatters.SimpleFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileAppender implements logAppender {
    private logLevel level;
    private logFormatter formatter;
    private String filePath;
    private PrintWriter writer;

    public FileAppender(String filePath){
        this(filePath,logLevel.DEBUG);
    }

    public FileAppender(String filePath,logLevel level){
        this.level=level;
        this.formatter=new SimpleFormatter();
        this.filePath=filePath;
        initializeWriter();
    }

    public void initializeWriter(){
        try {
            this.writer=new PrintWriter(new FileWriter(filePath,true),true);
        } catch (IOException e) {
            System.err.println("Failed to initialize FileAppender for " + filePath + ": " + e.getMessage());
        }
    }

    @Override
    public void append(logMessage message){
        if(!isEnabled(message.getLevel())||writer==null){
            return;
        }
        
        try {
            String formattedString=formatter.format(message);
            writer.println(formattedString);
            writer.flush();
        } catch (Exception e) {
            System.err.println("Failed to write to file " + filePath + ": " + e.getMessage());
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

    public String getFilePath(){
        return filePath;
    }

    public void close(){
        if(writer!=null){
            writer.close();
        }
    }
}
