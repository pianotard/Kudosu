import java.util.List;
import java.util.ArrayList;

public class NumberGrid extends AbstractNumberCollection {
    
    public NumberGrid(int index, List<NumberBox> boxes) {
        super(index, boxes);
    }

    @Override
    public NumberGrid replaceBox(int index, NumberBox replacement) {
        List<NumberBox> ret = new ArrayList<>(this.numberBoxes);
        ret.set(index, replacement);
        return new NumberGrid(this.index, ret);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Grid " + this.index + " of number-boxes:\n");
        for (int i = 0; i < 9; i++) {
            builder.append(this.numberBoxes.get(i));
            if (i == 2 || i ==5) {
                builder.append("\n");
            }
        }
        return builder + "";
    }
}
