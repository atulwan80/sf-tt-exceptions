package streamstuff;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;



public class MoreStream {
    public static Stream<String> getLines(String name) {
        try {
            return Files.lines(Paths.get(name));
        } catch (Throwable t) {
//            throw new RuntimeException(t);
            System.err.println(t.getMessage());
            return Stream.empty();
        }
    }

    public static void main(String[] args) throws Throwable {
//        Files.lines(Paths.get("one.txt")).forEach(s -> System.out.println(s));

        Stream<String> fNames = Stream.of("one.txt", "two.txt", "three.txt");

        fNames
              .flatMap(n -> getLines(n))
              .forEach(s -> System.out.println(s));

    }
}
