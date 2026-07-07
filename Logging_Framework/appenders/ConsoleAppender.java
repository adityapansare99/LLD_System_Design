package appenders;

import core.logAppender;
import core.logFormatter;
import core.logLevel;
import core.logMessage;
import formatters.SimpleFormatter;
import java.io.PrintStream;

public class ConsoleAppender implements logAppender {
    private logLevel level;
    private logFormatter formatter;
    private PrintStream outputStream;

    public ConsoleAppender(){
        this(logLevel.DEBUG);
    }

    public ConsoleAppender(logLevel level){
        this.level=level;
        this.formatter=new SimpleFormatter();
        this.outputStream=System.out;
    }

    @Override
    public void append(logMessage message){
        if(!isEnabled(message.getLevel())){
            return;
        }

        String formattedMessage=formatter.format(message);

        if(message.getLevel()==logLevel.ERROR || message.getLevel()==logLevel.FATAL){
            System.err.println(formattedMessage);
        }

        else{
            outputStream.println(formattedMessage);
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

    public void setOutputStream(PrintStream outpuStream){
        this.outputStream=outpuStream;
    }
}
