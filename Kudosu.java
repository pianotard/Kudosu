import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Kudosu {
    
    List<List<NumberBox>> numberBoxes;

    public Kudosu(List<List<NumberBox>> boxes) {
        this.numberBoxes = boxes;
    }

    public Kudosu replaceGrid(int index, NumberGrid replacement) {
        List<List<NumberBox>> ret = new ArrayList<>(this.numberBoxes);
        int row = Kudosu.gridRow(index, 0);
        int col = Kudosu.gridCol(index, 0);
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                ret.get(i).set(j, replacement.toList().get((i - row) * 3 + j - col));
            }
        }
        return new Kudosu(ret);
    }

    public Kudosu replaceCol(int index, NumberRow replacement) {
        List<List<NumberBox>> ret = new ArrayList<>(this.numberBoxes);
        for (int i = 0; i < 9; i++) {
            List<NumberBox> nbl = this.numberBoxes.get(i);
            nbl.set(index, replacement.toList().get(i));
        }
        return new Kudosu(ret);
    }

    public Kudosu replaceRow(int index, NumberRow replacement) {
        List<List<NumberBox>> ret = new ArrayList<>(this.numberBoxes);
        ret.set(index, replacement.toList());
        return new Kudosu(ret);
    }

    public Optional<Integer> getEasiestRowIndex() {
        return IntStream.range(0, 9)
            .mapToObj(i -> this.getRow(i))
            .filter(row -> !row.isComplete())
            .min((x, y) -> x.countEmptyBoxes() - y.countEmptyBoxes())
            .map(row -> row.getIndex());
    }

    public Optional<NumberGrid> getEasiestGrid() {
        return IntStream.range(0, 9)
            .mapToObj(i -> this.getGrid(i))
            .min((x, y) -> x.countEmptyBoxes() - y.countEmptyBoxes());
    }

    public Optional<Integer> getEasiestColIndex() {
        return IntStream.range(0, 9)
            .mapToObj(i -> this.getCol(i))
            .filter(col -> !col.isComplete())
            .min((x, y) -> x.countEmptyBoxes() - y.countEmptyBoxes())
            .map(col -> col.getIndex());
    }

    public NumberGrid getGrid(int index) {
        List<NumberBox> grid = new ArrayList<>();
        int row = index / 3 * 3;
        int col = index % 3 * 3;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                grid.add(this.numberBoxes.get(i).get(j).clone());
            }
        }
        return new NumberGrid(index, grid);
    }

    public NumberRow getRow(int index) {
        List<NumberBox> row = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            row.add(this.numberBoxes.get(index).get(i).clone());
        }
        return new NumberRow(index, row);
    }

    public NumberRow getCol(int index) {
        List<NumberBox> col = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            col.add(this.numberBoxes.get(i).get(index).clone());
        }
        return new NumberRow(index, col);
    }

    public static int gridRow(int gridIndex, int boxIndex) {
        int blockRow = gridIndex / 3;
        int boxRow = boxIndex / 3;
        return blockRow * 3 + boxRow;
    }

    public static int gridCol(int gridIndex, int boxIndex) {
        int blockCol = gridIndex % 3;
        int boxCol = boxIndex % 3;
        return blockCol * 3 + boxCol;
    }

    public static int gridNumber(int rowIndex, int colIndex) {
        int gridRow = rowIndex / 3;
        int gridCol = colIndex / 3;
        return gridRow * 3 + gridCol;
    }

    public boolean solved() {
        for (List<NumberBox> nbl : this.numberBoxes) {
            for (NumberBox nb : nbl) {
                if (nb.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean assertGrid() {
        for (int i = 0; i < 9; i++) {
            if (!this.getRow(i).assertBoxes()) {
                System.out.println("Row " + i + " failed");
                return false;
            }
            if (!this.getCol(i).assertBoxes()) {
                System.out.println("Col " + i + " failed");
                return false;
            }
            if (!this.getGrid(i).assertBoxes()) {
                System.out.println("Grid " + i + " failed");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Kudosu)) {
            return false;
        }
        Kudosu k = (Kudosu) o;
        return k.numberBoxes.equals(this.numberBoxes);
   }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Kudosu board with number-boxes:\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(this.numberBoxes.get(i).get(j) + "");
                if (j == 2 || j == 5) {
                    builder.append("|");
                }
            }
            builder.append("\n");
            if (i == 2 || i == 5) {
                builder.append("---------+---------+---------\n");
            }
        }
        return builder + "";
    }
}
