package javafx.bind;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Test1 {

    public static void main(String[] args) {

        Bill electricBill = new Bill();

        electricBill.amountDueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                System.out.println("Electric bill has changed!");
            }
        });

        electricBill.setAmountDue(100.00);

    }
}
