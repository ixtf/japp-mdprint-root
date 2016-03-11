package javafx.bind;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Test3 {

    public static void main(String[] args) {
        IntegerProperty num1 = new SimpleIntegerProperty(1);
        IntegerProperty num2 = new SimpleIntegerProperty(2);
        NumberBinding sum = Bindings.add(num1, num2);
        System.out.println(sum.getValue());
        num1.setValue(2);
        System.err.println(sum.getValue());
    }
}
