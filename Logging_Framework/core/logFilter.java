package core;

public interface logFilter {
    boolean shouldLog(logMessage message);
    void setLevel(logLevel level);
    logLevel getLevel();
}
