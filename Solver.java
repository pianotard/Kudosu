import java.util.List;
import java.util.stream.IntStream;

public class Solver {

    Kudosu kudosu;

    public Solver(Kudosu kudosu) {
        this.kudosu = kudosu;
    }

    public Kudosu solve() {
        while (!this.kudosu.solved()) {
            Kudosu before = this.kudosu;
            IntStream.range(0, 9).forEach(i -> this.fillRow(i, true));
            IntStream.range(0, 9).forEach(i -> this.fillRow(i, false));
            IntStream.range(0, 9).forEach(i -> this.fillGrid(i));
            if (this.kudosu.equals(before)) break;
        }
        if (this.kudosu.assertGrid()) {
            System.out.println("Assertion passed");   
        };
        return this.kudosu;
    }

    private void fillGrid(int index) {
        NumberGrid grid = this.kudosu.getGrid(index);
        NumberGrid replacement = grid;
        List<NumberBox> blanks = grid.getBlanks();
        for (NumberBox blank : blanks) {
            NumberRow row = this.kudosu.getRow(Kudosu.gridRow(index, blank.getIndex()));
            NumberBox replacementBox = row.removePencilMarksFrom(blank);
            if (Main.debug) {
                System.out.println("Before remove");
                blank.printDetails();
                System.out.println("After remove");
                replacementBox.printDetails();
            }
            if (!replacementBox.isEmpty()) {
                replacement = replacement.replaceBox(blank.getIndex(), replacementBox);
                continue;
            }
            NumberRow col = this.kudosu.getCol(Kudosu.gridCol(index, blank.getIndex()));
            replacementBox = col.removePencilMarksFrom(blank);
            if (!replacementBox.isEmpty()) {
                replacement = replacement.replaceBox(blank.getIndex(), replacementBox);
            }
        }
        this.kudosu = this.kudosu.replaceGrid(index, replacement);
        System.out.println("After fill grid " + index + ":");
        System.out.println(this.kudosu);      
    }

    private void fillRow(int index, boolean isRow) {
        String dim = isRow ? "row" : "col";
        NumberRow toFill = isRow ? this.kudosu.getRow(index) : this.kudosu.getCol(index);
        NumberRow replacement = toFill;
        List<NumberBox> blanks = toFill.getBlanks();
        for (NumberBox blank : blanks) {
            NumberRow perpendicular = isRow ? this.kudosu.getCol(blank.getIndex()) : this.kudosu.getRow(blank.getIndex());
            NumberBox replacementBox = perpendicular.removePencilMarksFrom(blank);
            if (!replacementBox.isEmpty()) {
                replacement = replacement.replaceBox(blank.getIndex(), replacementBox);
                continue;
            }
            int r = isRow ? toFill.getIndex() : blank.getIndex();
            int c = isRow ? blank.getIndex() : toFill.getIndex();
            NumberGrid ownGrid = this.kudosu.getGrid(Kudosu.gridNumber(r, c));
            replacementBox = ownGrid.removePencilMarksFrom(blank);
            if (!replacementBox.isEmpty()) {
                replacement = replacement.replaceBox(blank.getIndex(), replacementBox);
            }
            IntStream.iterate(r, r -> r < r + 3, r -> r + 1)
                .limit(3)
                .map(i -> i / 3 == r / 3 ? i : i - 3)
                .filter(i -> i != r)
                .forEach(System.out::println);
            this.kudosu = isRow ? this.kudosu.replaceRow(index, replacement) : this.kudosu.replaceCol(index, replacement);
            System.out.println("After fill " + dim + " " + index + ":");
            System.out.println(this.kudosu);        
        }
    }

    private void fillEasiestCol() {
        if (!this.kudosu.getEasiestColIndex().isPresent()) {
            return;
        }
        int easiestColIndex = this.kudosu.getEasiestColIndex().get();
        this.fillRow(easiestColIndex, false);
    }

    private void fillEasiestRow() {
        if (!this.kudosu.getEasiestRowIndex().isPresent()) {
            return;
        }
        int easiestRowIndex = this.kudosu.getEasiestRowIndex().get();
        this.fillRow(easiestRowIndex, true);
    }
}
