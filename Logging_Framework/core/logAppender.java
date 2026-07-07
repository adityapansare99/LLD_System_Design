package core;

public interface logAppender {
    void append(logMessage message);
    void setLevel(logLevel level);
    logLevel getLevel();
    boolean isEnabled(logLevel level);
    void setFormatter(logFormatter formatter);
    logFormatter getFormatter();
}
