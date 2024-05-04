import java.util.*;
import java.util.stream.*;
public class Stream1 {
    
public static void main(String[] args) {
    List<Integer> num = Arrays.asList(2,3,4,5,6);
    List<Integer> square = num.stream().map(x->x*x).collect(Collectors.toList());
    System.out.println(square);
    
    List<String> names = Arrays.asList("Reflection","Collection","Stream");
    List<String> result = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());

    System.out.println(result);

}
    
}