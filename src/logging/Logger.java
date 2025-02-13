package logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class Logger {
    private Consumer<Log> output;
    private int minimalOrdinal;
    private ExecutorService executor;

    public Logger(Builder builder) {
        this.output = builder.output;
        this.minimalOrdinal = builder.minimalOrdinal;
        this.executor = builder.executor;
    }

    public static Logger of(Consumer<Log> output, int minimalOrdinal, ExecutorService executor) {
        return new Builder().output(output).minimalOrdinal(minimalOrdinal).executor(executor).build();
    }

    public static Logger createConsoleLogger(int minimalOrdinal, ExecutorService executor) {
        return new Builder()
                .output(log -> System.out.println(log.toColoredString()))
                .minimalOrdinal(minimalOrdinal)
                .executor(executor)
                .build();
    }

    public static Logger createFileLogger(int minimalOrdinal, ExecutorService executor, String filePath) {
        return new Builder()
                .output(log -> {
                    try {
                        Files.write(Path.of(filePath), (log + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .minimalOrdinal(minimalOrdinal)
                .executor(executor)
                .build();
    }

    public Logger debug(String message) {
        return log(ConsoleColor.BLUE, LoggerLevel.DEBUG, message);
    }

    public Logger info(String message) {
        return log(ConsoleColor.GREEN, LoggerLevel.INFO, message);
    }

    public Logger warn(String message) {
        return log(ConsoleColor.YELLOW, LoggerLevel.WARN, message);
    }

    public Logger error(String message) {
        return log(ConsoleColor.RED, LoggerLevel.ERROR, message);
    }

    public Consumer<Log> getOutput() {
        return output;
    }

    public int getMinimalOrdinal() {
        return minimalOrdinal;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public Logger setOutput(Consumer<Log> output) {
        this.output = output;
        return this;
    }

    public Logger setMinimalOrdinal(int minimalOrdinal) {
        this.minimalOrdinal = minimalOrdinal;
        return this;
    }

    public Logger setExecutor(ExecutorService executor){
        this.executor = executor;
        return this;
    }

    public Logger shutdown() {
        executor.shutdown();
        return this;
    }

    public Logger log(ConsoleColor color, LoggerLevel level, String message) {
        if (level.getOrdinal() >= minimalOrdinal) {
            executor.submit(() -> output.accept(Log.of(color, level, message)));
        }
        return this;
    }

    public static class Builder {
        private Consumer<Log> output;
        private int minimalOrdinal = LoggerLevel.DEBUG.getOrdinal();
        private ExecutorService executor;

        public Builder output(Consumer<Log> output) {
            this.output = output;
            return this;
        }

        public Builder minimalOrdinal(int minimalOrdinal) {
            this.minimalOrdinal = minimalOrdinal;
            return this;
        }

        public Builder executor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public Logger build() {
            if (output == null || executor == null) {
                throw new IllegalArgumentException("Output and executor must be provided");
            }

            return new Logger(this);
        }
    }
}
