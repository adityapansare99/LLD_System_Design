package core;

public enum logLevel{
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4),
    FATAL(5);

    private final int priority;

    logLevel(int priority){
        this.priority=priority;
    }

    public int getPriority(){
        return priority;
    }

    public boolean isGreaterOrEqual(logLevel other){
        return this.priority>=other.priority;
    }
}