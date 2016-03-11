package autofill;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;

import java.util.List;

public class AutoFillTextBoxBehavior<T> extends BehaviorBase<AutoFillTextBox<T>> {

    public AutoFillTextBoxBehavior(AutoFillTextBox<T> textBox, List<KeyBinding> keys) {
        super(textBox, keys);
    }
}
