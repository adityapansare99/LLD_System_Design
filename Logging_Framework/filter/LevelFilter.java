package filter;

import core.logFilter;
import core.logLevel;
import core.logMessage;

public class LevelFilter implements logFilter{
    private logLevel level;

    public LevelFilter(){
        this(logLevel.DEBUG);
    }

    public LevelFilter(logLevel level){
        this.level=level;
    }

    @Override
    public boolean shouldLog(logMessage message){
        return message.getLevel().isGreaterOrEqual(level);
    }

    @Override
    public void setLevel(logLevel level){
        this.level=level;
    }

    @Override
    public logLevel getLevel(){
        return level;
    }
}
