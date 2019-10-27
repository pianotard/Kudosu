import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static boolean debug = false;

    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        List<List<NumberBox>> boxes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<NumberBox> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(new NumberBox(sc.nextInt()));
            }
            boxes.add(row);
        }
        Kudosu kudosu = new Kudosu(boxes);
        System.out.println(kudosu);
        Solver solver = new Solver(kudosu);

        Kudosu solved = solver.solve();
    }
}
