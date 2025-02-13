package logging;

public enum LoggerLevel {
    DEBUG("DEBUG", 0),
    INFO("INFO", 1),
    WARN("WARN", 2),
    ERROR("ERROR", 3);

    private final String level;
    private final int ordinal;

    LoggerLevel(String level, int ordinal) {
        this.level = level;
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return level;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
