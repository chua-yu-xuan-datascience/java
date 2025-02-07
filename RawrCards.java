import java.util.*;

public class RawrCards {
    
    static class Transaction {
        long buyCost;
        long sellProfit;
        
        Transaction(long buyCost, long sellProfit) {
            this.buyCost = buyCost;
            this.sellProfit = sellProfit;
        }
    }

    public static long maxProfit(int N, int T, int K, int[] deck, Transaction[] transactions) {
        // Step 1: Count the number of cards Anthony has for each type
        int[] cardCount = new int[T + 1];  // cardCount[i] is the count of cards of type i
        for (int card : deck) {
            cardCount[card]++;
        }
        
        List<Transaction> buyCosts = new ArrayList<>(); // List of buying options
        List<Transaction> sellProfits = new ArrayList<>(); // List of selling options
        long currentCombos = 0;
        
        // Step 2: Analyze the profit/loss for each card type
        for (int i = 1; i <= T; i++) {
            int count = cardCount[i];
            long ai = transactions[i - 1].buyCost; // Buy cost
            long bi = transactions[i - 1].sellProfit; // Sell profit
            
            if (count == 2) {
                // Already a combo, Anthony can sell both cards
                currentCombos++;
                sellProfits.add(new Transaction(2 * bi, 0));  // Selling both cards
            } else if (count == 1) {
                // Need to complete the combo
                buyCosts.add(new Transaction(ai, 0));  // Buy 1 more card to complete the combo
                sellProfits.add(new Transaction(0, bi));  // Can also sell the one card
            } else if (count == 0) {
                // Need to buy 2 cards to make a combo
                buyCosts.add(new Transaction(2 * ai, 0));  // Buy 2 cards
            }
        }
        
        // We need exactly K combos. Calculate the difference
        int combosNeeded = K - (int)currentCombos;
        
        long totalProfit = 0;
        
        // Step 3: Maximize profit or minimize loss
        
        // If we need more combos (combosNeeded > 0), start by buying the necessary combos
        if (combosNeeded > 0) {
            // Sort the buying costs by the cheapest option first (ascending order)
            buyCosts.sort(Comparator.comparingLong(a -> a.buyCost));
            
            // Buy the necessary combos by selecting the cheapest options
            for (int i = 0; i < combosNeeded; i++) {
                if (i < buyCosts.size()) {
                    totalProfit -= buyCosts.get(i).buyCost;  // Deduct the cost of buying cards
                }
            }
        }
        
        // If we have more combos than needed (combosNeeded < 0), sell excess cards
        if (combosNeeded < 0) {
            // Sort the selling profits by the highest profit first (descending order)
            sellProfits.sort((a, b) -> Long.compare(b.sellProfit, a.sellProfit));  // Sort by sellProfit descending
            
            // Sell the excess combos for maximum profit
            for (int i = 0; i < -combosNeeded; i++) {
                if (i < sellProfits.size()) {
                    totalProfit += sellProfits.get(i).sellProfit;  // Add profit from selling cards
                }
            }
        }
        
        return totalProfit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read input
        int N = sc.nextInt();
        int T = sc.nextInt();
        int K = sc.nextInt();
        
        int[] deck = new int[N];
        for (int i = 0; i < N; i++) {
            deck[i] = sc.nextInt();
        }
        
        Transaction[] transactions = new Transaction[T];
        for (int i = 0; i < T; i++) {
            long ai = sc.nextLong();
            long bi = sc.nextLong();
            transactions[i] = new Transaction(ai, bi);
        }
        
        // Call the function to calculate the optimal profit
        long result = maxProfit(N, T, K, deck, transactions);
        
        // Output the result
        System.out.println(result);
    }
}
/* Anthony and Cora are playing Dominion, their favorite card game. In Dominion, there are 
T different card types, and each player has a set of cards (known as a deck). A deck  D
is said to have C 
combos if C 
is the largest integer such that for 
C different card types in the game,  D
contains at least two cards of that type. Anthony currently has N 
cards and he wants to trade cards with Cora such that he’ll have a deck with exactly 
K combos. 

For each card type i 
(1 <= i <= T), Anthony can choose to perform at most one transaction. There are two types of transaction: 

Buy up to two cards of ith 
type from Cora at ai 
coins each

Sell all his cards of ith 
type for bi 
coins each

Anthony wants to maximize his profit while obtaining a complete deck. Anthony is willing to spend coins in order to obtain a complete deck if necessary, but of course he wants to minimize his spending in that case. Note that he doesn’t care about keeping the rest of his cards which don’t contribute to the complete deck.

Anthony has hired you to help him calculate how much money he can make if he chooses the optimal strategy for obtaining enough combos. If he has to spend money, output a negative number.

Input
The first line of the input contains three integers N 
,T  
, and K 
, 1<=K <= T <= 100000 
, 1 <= N <= 2T 
.

The next line is a list of N 
integers representing the cards in Anthony’s deck. Each integer on this line is between 
1 and T 
inclusive. It is guaranteed no integers appear more than twice.

Finally, each of the next T 
lines of the input contains two integers each. The ith 
line contains ai 
and bi 
, 1 <= ai, bi <=10^9 
, corresponding to the price of buying and selling a card of type 
.

Output
Output a single integer denoting Anthony’s profit assuming he trades optimally. 
sample input: 
4 3 2
1 3 2 1 
1 50
50 20
40 30 
sample output: 10 

no you can still sell the 2 cards in the combo, you dont have to leave them untouched. In the first example, Anthony should sell two of card 1 
 and buy one of card 2 
 and one of card 3 
 for a net profit of 10 
 coins. If he chooses to sell one of card 3 
 and buy one of card 2 
, then he’ll end up spending 20 
 coins.
*/ 

