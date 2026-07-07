package core;

import java.time.Instant;

public class logMessage {
    private final Instant timestamp;
    private final String message;
    private final String source;
    private final logLevel Level;

    private logMessage(Builder builder){
        timestamp=builder.timestamp;
        message=builder.message;
        source=builder.source;
        Level=builder.Level;
    }

    public Instant getTimestamp(){
        return this.timestamp;
    }

    public String getMessage(){
        return this.message;
    }

    public String getSource(){
        return this.source;
    }

    public logLevel getLevel(){
        return this.Level;
    }

    @Override
    public String toString() {
        return String.format("LogMessage{timestamp=%s, level=%s, message='%s', source='%s'}", 
            timestamp, Level, message, source);
    }

    public static class Builder{
        private Instant timestamp;
        private String message;
        private String source;
        private logLevel Level;

        public Builder level(logLevel Level) {
            this.Level=Level;
            return this;
        }

        public Builder timestamp(){
            this.timestamp=Instant.now();
            return this;
        }
        
        public Builder message(String message){
            this.message=message;
            return this;
        }

        public Builder source(String source){
            this.source=source;
            return this;
        }

        public logMessage build(){
            if (Level == null) {
                throw new IllegalStateException("LogLevel is required");
            }
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalStateException("Message is required");
            }

            return new logMessage(this);
        }
    }
}
