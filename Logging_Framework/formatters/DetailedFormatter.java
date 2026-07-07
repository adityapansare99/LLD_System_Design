package formatters;

import core.logFormatter;
import core.logMessage;
import java.time.format.DateTimeFormatter;

public class DetailedFormatter implements logFormatter {
    private String pattern;
    private String dateFormat;
    @SuppressWarnings("unused")
    private DateTimeFormatter dateTimeFormatter;

    public DetailedFormatter() {
        this("[%LEVEL] %TIMESTAMP [%SOURCE] - %MESSAGE");
    }

    public DetailedFormatter(String pattern) {
        this.pattern = pattern;
        this.dateFormat = "yyyy-MM-dd HH:mm:ss";
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public String format(logMessage message){
        if(pattern==null || pattern.isEmpty()){
            String source=message.getSource()!=null?message.getSource():"Unknown";

            return String.format("[%s] %s [%s] - %s", 
                message.getLevel(), 
                message.getTimestamp().toString(), 
                source,
                message.getMessage());
        }

        String formatted=pattern.replace("%LEVEL", message.getLevel().toString())
            .replace("%TIMESTAMP", message.getTimestamp().toString())
            .replace("%MESSAGE", message.getMessage())
            .replace("%SOURCE", message.getSource() != null ? message.getSource() : "Unknown");

        return formatted;
    }

    @Override
    public void setPattern(String pattern){
        this.pattern=pattern;
    }

    @Override
    public String getPattern(){
        return pattern;
    }

    @Override
    public void setDateFormat(String dateFormat){
        this.dateFormat=dateFormat;
        this.dateTimeFormatter=DateTimeFormatter.ofPattern(dateFormat);
    }
}
