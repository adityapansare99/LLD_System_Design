package core;

public interface logFormatter {
    String format(logMessage message);
    void setPattern(String pattern);
    String getPattern();
    void setDateFormat(String dateFormat);
}
