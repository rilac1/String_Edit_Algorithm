import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

public class StringEdit {
    int n, m;
    String x, y;
    String[][] t;
    int[][] cost;
    Character[][] edit;
    ArrayList<Character> order;

    // 생성자
    public StringEdit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n두 문자열의 길이를 입력: ");
        n = sc.nextInt();
        m = sc.nextInt();
        sc.nextLine();

        System.out.print("x: ") ;
        x = sc.nextLine();
        System.out.print("y: ");
        y = sc.nextLine();
        sc.close();

        t = new String[n+1][m+1];
        cost = new int[n+1][m+1];
        edit = new Character[n+1][m+1];
        order = new ArrayList<>();

        System.out.println();
        makeTable();
        makeCost();
    }

    public int C(int i, int j) {
        if (t[i][j].equals(t[i-1][j-1])) return 0; 
        else return 2; 
    }

    public void makeTable() {
        for (int i=0; i<=n; i++)
            for (int j=0; j<=m; j++)
                t[i][j] = y.substring(0, j) + x.substring(i);
    }

    public void makeCost() {
        for (int j=0; j<=m; j++) {
            cost[0][j] = j;
            edit[0][j] = 'I';
        }
        for (int i=0; i<=n; i++) {
            cost[i][0] = i;
            edit[i][0] = 'D';
        }
        edit[0][0] = null;

        for (int i=1; i<=n; i++)
            for (int j=1; j<=m; j++) {
                HashMap<Integer, Character> map = new HashMap<Integer, Character>();
                map.put(cost[i][j-1]+1, 'I');
                map.put(cost[i-1][j-1] + C(i,j), 'C');
                map.put(cost[i-1][j]+1, 'D');
                
                Integer minKey = Collections.min(map.keySet());
                cost[i][j] = minKey;
                edit[i][j] = map.get(minKey);
            }
    }
    
    public void backTracking() {
        int i = n;
        int j = m;
        while (i+j>0)
            switch(edit[i][j]) {
                case 'I': order.add('I'); j--;
                    break;
                case 'D': order.add('D'); i--;                
                    break;
                default: order.add('C'); i--; j--;
                    break;
            }
        Collections.reverse(order);
    }
    
    // 출력 함수
    public void printTable() {
        System.out.println("【All cases】");
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=m; j++) {
                if (t[i][j].length() == 0)
                    System.out.printf("%-20s", "");
                else
                    System.out.printf("%-20s", t[i][j]);
            }
            System.out.println();
        }
    }

    public void printCost() {
        System.out.println("【Cost】");
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=m; j++)
                    System.out.printf("%-5d", cost[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    public void printEdit() {
        System.out.println("【Edit】");
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=m; j++)
                    System.out.printf("%-5c", edit[i][j]);
            System.out.println();
        }
        System.out.println();
    }

    public void printResult() {
        System.out.println("【Result】");
        int i = 0;
        int j = 0;
        System.out.println("> Edit order: " + order);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("0 (default): " + t[i][j]);
        for (int n = 0; n<order.size(); n++) {
            switch(order.get(n)) {
                case 'I': j++;
                    break;
                case 'D': i++;                
                    break;
                default: i++; j++;
                    break;
            }
            System.out.printf("%d (%c): %s\n", n+1, order.get(n), t[i][j]);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("> Final cost: " + cost[n][m]);
    }

    public static void main(String args[]) {
        StringEdit edit = new StringEdit();
        edit.printTable();
        edit.printCost();
        edit.printEdit();
        edit.backTracking();
        edit.printResult();

        System.out.println();
    }
}
