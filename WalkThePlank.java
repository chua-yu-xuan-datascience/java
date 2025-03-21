import java.util.*;

public class WalkThePlank {
    static class Plank {
        int weight;
        int length;

        Plank(int weight, int length) {
            this.weight = weight;
            this.length = length;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Q = sc.nextInt();

        // TreeMap: length -> TreeMap(weight -> occurrence)
        TreeMap<Integer, TreeMap<Integer, Integer>> planks = new TreeMap<>();

        while (Q-- > 0) {
            String operation = sc.next();

            if (operation.equals("a")) {
                int Wp = sc.nextInt();
                int Lp = sc.nextInt();

                // Insert plank into the TreeMap
                planks.putIfAbsent(Lp, new TreeMap<>());
                planks.get(Lp).put(Wp, planks.get(Lp).getOrDefault(Wp, 0) + 1);

            } else if (operation.equals("c")) {
                long X = sc.nextLong();

                // Select plank A using floorKey: max La <= X and smallest weight min Wa
                Plank A = choosePlankA(planks, X);

                // Select plank B using ceilingKey: min Lb >= X and largest weight max Wb
                Plank B = choosePlankB(planks, X);

                int Wa = A.weight;
                int La = A.length;
                int Wb = B.weight;
                int Lb = B.length;

                // Calculate the effort
                long effort = (1 + Wa + Wb) * (1 + Math.abs(La - Lb));
                System.out.println(effort);
            }
        }
        sc.close();
    }

    // Select plank A using floorKey: max La <= X and smallest weight min Wa
    private static Plank choosePlankA(TreeMap<Integer, TreeMap<Integer, Integer>> planks, long X) {
        Map.Entry<Integer, TreeMap<Integer, Integer>> entryA = planks.floorEntry((int) X);

        if (entryA != null) {
            TreeMap<Integer, Integer> weightMap = entryA.getValue();
            Integer weight = weightMap.firstKey(); // Smallest weight for plank A

            // Just remove if it is the last one
            if (weightMap.get(weight) == 1) {
                weightMap.remove(weight);
                // Remove the length entry if no more planks of that length exist
                if (weightMap.isEmpty()) planks.remove(entryA.getKey()); 
            } else {
                weightMap.put(weight, weightMap.get(weight) - 1);
            }

            return new Plank(weight, entryA.getKey());
        }

        return null;
    }

    // Select plank B using ceilingKey: min Lb >= X and largest weight max Wb
    private static Plank choosePlankB(TreeMap<Integer, TreeMap<Integer, Integer>> planks, long X) {
        Map.Entry<Integer, TreeMap<Integer, Integer>> entryB = planks.ceilingEntry((int) X);

        if (entryB != null) {
            TreeMap<Integer, Integer> weightMap = entryB.getValue();
            Integer weight = weightMap.lastKey(); // Largest weight for plank B

            // Just remove if it is the last one
            if (weightMap.get(weight) == 1) {
                weightMap.remove(weight);
                // Remove the length entry if no more planks of that length exist
                if (weightMap.isEmpty()) planks.remove(entryB.getKey()); 
            } else {
                weightMap.put(weight, weightMap.get(weight) - 1);
            }

            return new Plank(weight, entryB.getKey());
        }

        return null;
    }
}