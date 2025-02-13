package logging;

public enum ConsoleColor {
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    RESET("\u001B[0m");

    private final String color;

    ConsoleColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
