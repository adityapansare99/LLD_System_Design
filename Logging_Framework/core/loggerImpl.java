package core;

import appenders.ConsoleAppender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class loggerImpl implements Logger{
    private final String name;
    private logLevel level;
    private final List<logAppender> appenders;
    private final List<logFilter> filters;

    public loggerImpl(){
        this("DefaultLogger");
    }

    public loggerImpl(String name){
        this(name,true);
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public loggerImpl(String name,boolean addDefaultAppender){
        this.name=name;
        this.level=logLevel.DEBUG;
        this.appenders=Collections.synchronizedList(new ArrayList<>());
        this.filters=Collections.synchronizedList(new ArrayList<>());

        if(addDefaultAppender){
            addAppender(new ConsoleAppender());
        }
    }

    public loggerImpl(String name, logConfiguration config) {
        this(name);
        this.level = config.getRootLevel();
    }

    @Override
    public synchronized void debug(String message){
        log(logLevel.DEBUG, message);
    }

    @Override
    public synchronized void info(String message){
        log(logLevel.INFO, message);
    }

    @Override
    public synchronized void warning(String message){
        log(logLevel.WARNING, message);
    }

    @Override
    public synchronized void error(String message){
        log(logLevel.ERROR, message);
    }

    @Override
    public synchronized void fatal(String message){
        log(logLevel.FATAL, message);
    }

    @Override
    public synchronized void log(logLevel level,String message){
        if(!level.isGreaterOrEqual(this.level)){
            return;
        }

        logMessage logMessage=new logMessage.Builder().level(level).message(message).source(getCallingClass()).build();

        for(logFilter filter:filters){
            if(!filter.shouldLog(logMessage)){
                return;
            }
        }

        for(logAppender appender:appenders){
            if(appender.isEnabled(level)){
                appender.append(logMessage);
            }
        }
    }
    
    @Override
    public void setLevel(logLevel level){
        this.level=level;
    }

    @Override
    public void addAppender(logAppender appender){
        appenders.add(appender);
    }

    @Override
    public void addFilter(logFilter filter){
        filters.add(filter);
    }

    @Override
    public void removeFilter(logFilter filter){
        filters.remove(filter);
    }

    public String getName(){
        return name;
    }

    public logLevel getLevel(){
        return level;
    }

    @Override
    public List<logAppender> getAppenders(){
        return new ArrayList<>(appenders);
    }

    @Override
    public List<logFilter> getFilters(){
        return new ArrayList<>(filters);
    }

    private String getCallingClass(){
        try {
            StackTraceElement[] stackTrace=Thread.currentThread().getStackTrace();

            if(stackTrace.length>3){
                String className=stackTrace[3].getClassName();
                String methodName=stackTrace[3].getMethodName();

                return className+"."+methodName;
            }
        } catch (Exception e) {
        }

        return "Unknown";
    }
}
