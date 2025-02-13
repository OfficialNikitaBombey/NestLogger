package logging;

public record Log(ConsoleColor color, LoggerLevel level, String message) {
    public static Log of(ConsoleColor color, LoggerLevel level, String message) {
        return new Log(color, level, message);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(TimeUtils.getFormattedDate())
                .append(" | ")
                .append(TimeUtils.getFormattedTime())
                .append(" | ")
                .append(level)
                .append(" | ")
                .append(message)
                .toString();
    }

    public String toColoredString() {
        return color + toString() + ConsoleColor.RESET;
    }
}