import java.util.*;

public class RawrCards {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read the inputs
        int N = sc.nextInt();  // Number of cards
        int T = sc.nextInt();  // Total different card types
        int K = sc.nextInt();  // Number of combos required

        int[] cardCount = new int[T + 1];  // Array to store the count of each card type (index 1 to T)
        for (int i = 0; i < N; i++) {
            int cardType = sc.nextInt();  // Read card types
            cardCount[cardType]++;  // Increment the count of that card type
        }

        // A list to store Card objects that hold information about the cards
        ArrayList<Card> cardList = new ArrayList<>();

        // Read buy and sell prices for each card type and store the necessary data
        for (int i = 1; i <= T; i++) {
            int buyPrice = sc.nextInt();  // Buy price for card type i
            int sellPrice = sc.nextInt();  // Sell price for card type i

            // Calculate the opportunity cost (-buy + sell)
            int opportunityCost = -buyPrice + sellPrice;
            int totalValue = ((2 - cardCount[i]) * buyPrice) + (cardCount[i] * sellPrice);

            // Create and add the Card object to the list
            cardList.add(new Card(i, cardCount[i], buyPrice, sellPrice, opportunityCost, totalValue));
        }

        // Sort the card list based on the opportunity cost (lowest to highest)
        cardList.sort(new SortByOpportunityCost());

        long profit = 0;

        // For the first K cards, subtract the cost to complete the combos (buying cards if necessary)
        for (int i = 0; i < K; i++) {
            Card card = cardList.get(i);
            profit -= card.costToCombo;  // Subtract the cost to make the combo
        }

        // For the remaining T-K cards, add the profit from selling the cards
        for (int i = K; i < T; i++) {
            Card card = cardList.get(i);
            profit += card.costToSell;  // Add the profit from selling the cards
        }

        // Print the final profit or loss
        System.out.println(profit);
    }
}

// Card class to represent each card type's information
class Card {
    int cardNum;       // The card type number
    int total;         // The number of cards of this type in the deck
    int buyPrice;      // Buy price for this card type
    int sellPrice;     // Sell price for this card type
    int opportunityCost; // The opportunity cost (-buy + sell)
    int costToCombo;   // The cost to buy enough cards for the combo (if needed)
    int costToSell;    // The profit from selling all cards of this type

    public Card(int cardNum, int total, int buyPrice, int sellPrice, int opportunityCost, int totalValue) {
        this.cardNum = cardNum;
        this.total = total;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.opportunityCost = opportunityCost;

        // Calculate costToCombo (buy enough cards for combo, if needed)
        this.costToCombo = (2 - total) * buyPrice;

        // Calculate costToSell (profit from selling the cards)
        this.costToSell = total * sellPrice;
    }
}

// Comparator to sort cards by opportunity cost (ascending)
class SortByOpportunityCost implements Comparator<Card> {
    public int compare(Card a, Card b) {
        return Integer.compare(a.opportunityCost, b.opportunityCost);
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

