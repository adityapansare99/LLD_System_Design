package filter;

import core.logFilter;
import core.logLevel;
import core.logMessage;

public class SourceFilter implements logFilter {
    private String sourcePattern;
    private final boolean include;
    private logLevel level;

    public SourceFilter(String sourcePattern,boolean include){
        this.sourcePattern=sourcePattern;
        this.include=include;
        this.level=logLevel.DEBUG;
    }

    @Override
    public boolean shouldLog(logMessage message){
        if(message.getSource()==null){
            return !include;
        }

        boolean matches=message.getSource().contains(sourcePattern);
        return include?matches:!matches;
    }

    @Override
    public void setLevel(logLevel level){
        this.level=level;
    }

    @Override
    public logLevel getLevel(){
        return level;
    }

    public String getSourcePattern(){
        return sourcePattern;
    }

    public void setSourcePattern(String sourcePattern){
        this.sourcePattern=sourcePattern;
    }

    public boolean isInclude(){
        return include;
    }
}
