import java.util.ArrayList;
import java.util.function.Consumer;
public class lambdaExp {
public static void main(String[] args) {
    
    ArrayList<Integer> num = new ArrayList<>();
    num.add(3);
    num.add(4);
    num.add(5);
    num.add(63);
    num.add(33);
    num.forEach((n) -> {System.out.println(n);});
    //Using Consumer interface
    System.out.println("Multiplying with 2");
    Consumer<Integer> method = (n) -> {System.out.println(n*2);};
    num.forEach(method);

}

    
}