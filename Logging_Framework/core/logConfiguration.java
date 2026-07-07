package core;

public class logConfiguration {
    private logLevel rootLevel;

    public logConfiguration(){
        rootLevel=logLevel.INFO;
    }

    public logConfiguration(logLevel rootLevel){
        this.rootLevel=rootLevel;
    }

    public void setRootLevel(logLevel rootLevel){
        this.rootLevel=rootLevel;
    }

    public logLevel getRootLevel(){
        return rootLevel;
    }

    @Override
    public String toString() {
        return String.format("LogConfiguration{rootLevel=%s}", rootLevel);
    }
}
