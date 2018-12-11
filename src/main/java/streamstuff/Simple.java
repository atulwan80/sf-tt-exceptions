package streamstuff;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Simple {
    public static void main(String[] args) {
//        Stream<String> ss = Stream.of("Fred", "Jim", "Sheila");
        Stream<String> ss = Stream.of();

        ss
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));

//        Optional<String> os = Optional.of("Fred");
        Optional<String> os = Optional.empty();

        os
                .map(s -> s.toUpperCase())
                .ifPresent(s -> System.out.println(s));

        Map<String, String> mss = new HashMap<>();
        mss.put("Fred", "Jones");
        mss.put("Jim", "Smith");
        mss.put("Sheila", "Donahue");

        String lastName = mss.get("Freddy");
        if (lastName != null) {
            String address = "Mx. " + lastName;
            System.out.println(address);
        }

        Optional<Map<String, String>> opt = Optional.of(mss);
        opt
                .map(m -> m.get("Freddy"))
                .map(s -> "Mx. " + s)
                .ifPresent(s -> System.out.println(s));
    }
}
