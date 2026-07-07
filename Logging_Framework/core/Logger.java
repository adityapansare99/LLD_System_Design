package core;

import java.util.List;

public interface Logger{
    void debug(String message);
    void info(String message);
    void warning(String message);
    void error(String message);
    void fatal(String message);

    void log(logLevel level,String message);

    void setLevel(logLevel level);
    void addAppender(logAppender appender);
    void addFilter(logFilter filter);
    void removeFilter(logFilter filter);

    List<logAppender> getAppenders();
    List<logFilter> getFilters();
}