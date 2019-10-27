import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class NumberBox {

    private int index = -1;
    private Optional<Integer> solidNumber = Optional.empty();
    private List<Integer> pencilMarks = new ArrayList<>();

    public NumberBox(int solidNumber) {
        if (solidNumber == 0) {
            return;
        }
        this.solidNumber = Optional.of(solidNumber);
    }

    public NumberBox(int index, List<Integer> pencilMarks) {
        this.index = index;
        this.pencilMarks = pencilMarks;
    }

    public List<Integer> getPencilMarks() {
        return this.pencilMarks;
    }

    public NumberBox removePencilMark(Integer mark) {
        if (this.solidNumber.isPresent()) {
            if (Main.debug) System.out.println("Unable to remove pencil marks");
            return this;
        }
        if (!this.pencilMarks.contains(mark)) {
            if (Main.debug) System.out.println("Pencil mark does not exist");
            return this;
        }
        List<Integer> ret = new ArrayList<>(this.pencilMarks);
        ret.remove((Object) mark);
        Collections.sort(ret);
        if (Main.debug) System.out.println("Removed pencil mark: " + mark);
        if (ret.size() == 1) {
            return new NumberBox(ret.get(0));
        }
        return new NumberBox(this.index, ret);
    }

    public NumberBox addPencilMarks(List<Integer> marks) {
        if (this.solidNumber.isPresent()) {
            if (Main.debug) System.out.println("Unable to place pencil marks");
            return this;
        }
        List<Integer> ret = new ArrayList<>(marks);
        Collections.sort(ret);
        return new NumberBox(this.index, ret);
    }

    public NumberBox solidify() {
        if (this.pencilMarks.size() != 1) {
            if (Main.debug) System.out.println("Unable to solidify, > 1 pencil mark");
            return this;
        }
        return new NumberBox(this.pencilMarks.get(0));
    }

    public void printDetails() {
        if (this.solidNumber.isPresent()) {
            System.out.println("Solid number: " + this.solidNumber.get());
        } else {
            System.out.println("Pencil marks: " + this.pencilMarks);
        }
    }

    public boolean hasOnlyOnePencilMark() {
        return this.pencilMarks.size() == 1;
    }

    public boolean isEmpty() {
        return !this.solidNumber.isPresent();
    }

    public int getSolidNumber() {
        if (this.solidNumber.isPresent()) {
            return this.solidNumber.get();
        }
        return 0;
    }

    public int getIndex() {
        return this.index;
    }

    public NumberBox applyIndex(int index) {
        NumberBox ret = this.clone();
        ret.index = index;
        return ret;
    }

    public NumberBox clone() {
        if (this.solidNumber.isPresent()) {
            return new NumberBox(this.solidNumber.get());
        }
        List<Integer> marks = new ArrayList<>();
        for (int i = 0; i < this.pencilMarks.size(); i++) {
            marks.add(this.pencilMarks.get(i));
        }
        return new NumberBox(this.index, marks);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NumberBox)) {
            return false;
        }
        NumberBox nb = (NumberBox) o;
        return nb.solidNumber.equals(this.solidNumber);
    }

    @Override
    public String toString() {
        return this.solidNumber.isPresent() ? " " + this.solidNumber.get() + " " : "   ";
    }
}
