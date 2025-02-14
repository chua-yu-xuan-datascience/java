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

        // Method to calculate opportunity cost based on the original count
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
/*
 * i have a hint from my friend again. i need to make an object class to store buy+sell+sum and need to make a comparator to compare the sums. K combos means we need to choose the K number lowest Opportunity Cost (defined by buy+sell) and then sell the rest of the types of cards. so if any type of cards with 1 card, you have two moves - sell 1 or buy 1 (calculate their respective opportunity costs), if you have 0 cards of the type - must buy 2 (calculate opportunity costs of doing that) and if you have 2 cards of that type - must sell 2(also calculate opportunity costs of doing that). so you calculate the opportunity costs of doing that to all the types of cards so for example, if you have 4 types of cards, and need 2 combos. select the 2 combos with lowest opportunity cost and sell the other 2 types of cards. my friend also said to use 2 arr and 1 hashmap. the hashmap is to count the number of types of cards we originally have
 */

