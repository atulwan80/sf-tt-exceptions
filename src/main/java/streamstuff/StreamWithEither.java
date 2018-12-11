package streamstuff;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

interface ExceptionFunction<T, U> {
    U apply(T t) throws Throwable;
}

class Either<T> {
    private T value;
    private Throwable problem;

    private Either() {
    }

    public static <T> Either<T> success(T val) {
        Either<T> rv = new Either<>();
        rv.value = val;
        return rv;
    }

    public static <T> Either<T> failure(Throwable t) {
        Either<T> rv = new Either<>();
        rv.problem = t;
        return rv;
    }

    public boolean isSuccess() {
        return value != null;
    }

    public T getSuccess() {
        if (value == null)
            throw new RuntimeException("Attempted to get value from failed Either");
        return value;
    }

    // Monadic behaviors, map, flatMap, forEach -- for success and for failure
    public Either<T> ifError(Consumer<Throwable> op) {
        if (problem != null) {
            op.accept(problem);
        }
        return this;
    }

    public static <T, U> Function<T, Either<U>> wrap(ExceptionFunction<T, U> op) {
        return a -> {
            try {
                return Either.success(op.apply(a));
            } catch (Throwable t) {
                return Either.failure(t);
            }
        };
    }
}

public class StreamWithEither {

    public static void main(String[] args) throws Throwable {
        Stream<String> fNames = Stream.of("one.txt", "two.txt", "three.txt");

        fNames
                .map(Either.wrap(n -> Files.lines(Paths.get(n))))
                .map(e -> e.ifError(x -> System.err.println("PROBLEM " + x.getMessage())))
                .filter(e -> e.isSuccess())
                .flatMap(e -> e.getSuccess())
                .forEach(s -> System.out.println(s));
    }
}
