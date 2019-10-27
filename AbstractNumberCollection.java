import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public abstract class AbstractNumberCollection {
    
    protected int index;
    protected List<NumberBox> numberBoxes = new ArrayList<>();

    public AbstractNumberCollection(int index, List<NumberBox> boxes) {
        this.index = index;
        this.numberBoxes = boxes;
    }

    public abstract AbstractNumberCollection replaceBox(int index, NumberBox replacement);

    public NumberBox removePencilMarksFrom(NumberBox target) {
        if (target.hasOnlyOnePencilMark()) {
            return target.solidify();
        }
        List<Integer> solidNumbers = this.numberBoxes.stream()
            .filter(nb -> !nb.isEmpty())
            .map(nb -> nb.getSolidNumber())
            .collect(Collectors.toList());
        NumberBox ret = target;
        for (Integer solidNumber : solidNumbers) {
            ret = ret.removePencilMark(solidNumber);
        }
        return ret;
    }

    public List<NumberBox> getBlanks() {
        List<Integer> notPresent = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        this.numberBoxes.stream()
            .filter(nb -> !nb.isEmpty())
            .map(nb -> nb.getSolidNumber())
            .forEach(i -> notPresent.remove((Object) i));
        return IntStream.range(0, 9)
            .filter(i -> this.numberBoxes.get(i).isEmpty())
            .mapToObj(i -> this.numberBoxes.get(i).applyIndex(i))
            .map(box -> box.addPencilMarks(notPresent))
            .collect(Collectors.toList());
    }

    public boolean assertBoxes() {
        return this.numberBoxes.stream()
            .map(nb -> nb.getSolidNumber())
            .filter(i -> i != 0)
            .distinct()
            .count() == this.numberBoxes.stream()
            .map(nb -> nb.getSolidNumber())
            .filter(i -> i != 0)
            .count();
    }

    public boolean isComplete() {
        return this.countEmptyBoxes() == 0;
    }

    public int countEmptyBoxes() {
        return (int) this.numberBoxes.stream()
            .filter(nb -> nb.isEmpty())
            .count();
    }

    public int getIndex() {
        return this.index;
    }

    public List<NumberBox> toList() {
        return this.numberBoxes;
    }

    @Override
    public String toString() {
        return this.numberBoxes + "";
    }
}
