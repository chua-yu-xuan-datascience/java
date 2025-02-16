import java.util.*;

public class RawrCards {
    // Define a Card class to store card type, buy price, sell price, original count and its opportunity cost (to be calculated based on original count)
    static class Card {
        int type;           
        int buyPrice;       
        int sellPrice;      
        int originalCount;  
        int opportunityCost;

        // Constructor to initialise the items
        public Card(int type, int buyPrice, int sellPrice, int originalCount) {
            this.type = type;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
            this.originalCount = originalCount;
            calculateOpportunityCost();
        }

        // Calculate opportunity cost based on the original count
        public void calculateOpportunityCost() {
            if (originalCount == 2) {
                opportunityCost = 2 * sellPrice;
            } else if (originalCount == 1) {
                opportunityCost = buyPrice + sellPrice; 
            } else if (originalCount == 0) {
                opportunityCost = 2 * buyPrice;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read NTK
        int N = scanner.nextInt(); // Number of cards we have
        int T = scanner.nextInt(); // Number of card types
        int K = scanner.nextInt(); // Number of combos we want to get at the end
        
        // Read the deck in second line of input
        // for indexing purposes later, we dont use card 1, 2, 3, use 0, 1, 2 instead
        int[] deck = new int[N];
        int[] count = new int[T];
        for (int i = 0; i < N; i++) {
            deck[i] = scanner.nextInt()-1;
            count[deck[i]]++;  // Count the number of cards of i-th type in the deck
        }
        
        // Buy and Sell prices for each card type
        int[] buyPrice = new int[T];
        int[] sellPrice = new int[T];
        for (int i = 0; i < T; i++) {
            buyPrice[i] = scanner.nextInt();
            sellPrice[i] = scanner.nextInt();
        }
        
        // List to hold the card types, input information gathered above, into Card class already constructed above
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            cards.add(new Card(i, buyPrice[i], sellPrice[i], count[i]));
        }

        // Sort cards based on opportunity cost in ascending order
        cards.sort(Comparator.comparingInt(c -> c.opportunityCost));

        long totalProfit = 0;

        Set<Integer> InCombo = new HashSet<>(); // To record which card types chosen to be in combo
        
        // Choose the K card types with the lowest opportunity cost to add into hash
        for (int i = 0; i < K; i++) {
            Card card = cards.get(i);
            InCombo.add(card.type);

            // Need to buy if their original count is not 2
            if (card.originalCount == 0) {
                totalProfit -= 2 * card.buyPrice; 

            } else if (card.originalCount == 1) {
                totalProfit -= card.buyPrice;
            }
        }
        
        // Sell the remaining cards that are not part of the combo
        for (Card card : cards) {
            if (!InCombo.contains(card.type)) {
                totalProfit += card.originalCount * card.sellPrice;
            }
        }
        
        // Output total profit after buying and selling
        System.out.println(totalProfit);
        
        scanner.close();
    }
}