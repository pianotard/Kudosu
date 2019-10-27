import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class NumberRow extends AbstractNumberCollection {
    
    public NumberRow(int index, List<NumberBox> boxes) {
        super(index, boxes);
    }

    @Override
    public NumberRow replaceBox(int index, NumberBox replacement) {
        List<NumberBox> ret = new ArrayList<>(this.numberBoxes);
        ret.set(index, replacement);
        return new NumberRow(this.index, ret);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Row " + this.index + " of number-boxes:\n");
        for (NumberBox nb : this.numberBoxes) {
            builder.append(nb + "");
        }
        return builder + "";
    }
}
