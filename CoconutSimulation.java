/*
size of linkedlist: n
have a class for Player Hand: player id, state of hand (folded, fist, palmdown)
create linkedlist with 1 players hand lined up by their player id, state is folded
each time a syllable lands on a hand, remove the hand from front (poll) and add to back (addLast)
when we reach the last syllable, continue to remove hand BUT
if it lands on a folded hand, dont add anything to the back, add 2 fist hand belonging to that player id in front of linkedlist (addFirst)
then when the last syllable lands on fist hand, dont add anything to the front, add a palm down hand to the back of queue (addLast)
then when the last syllable lands on palm_down hand, do not add anything back of queue 
then repeat another round of syllable until size of deque = 1, return the player id of that hand
*/

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class CoconutSimulation {

    // Define the PlayerHand class to store the player's id and the state of their hand
    static class PlayerHand {
        int player_id;
        HandState state;

        public PlayerHand(int player_id, HandState state) {
            this.player_id = player_id;
            this.state = state;
        }
    }

    // Define all possible states of a hand
    enum HandState {
        FOLDED, FIST, PALM_DOWN
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        int s = scanner.nextInt();  // number of syllables
        int n = scanner.nextInt();   // number of players

        // deque (interface) with the operations we need, to a linkedlist (class) to help implement deque
        Deque<PlayerHand> deque = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            deque.add(new PlayerHand(i, HandState.FOLDED));
        }

        // Stopping condition is when only 1 hand is left
        while (deque.size() > 1) {
            // Process each syllable
            for (int i = 1; i <= s; i++) {
                // Remove a hand from the front
                PlayerHand hand = deque.poll();

                // If the hand is on the last syllable of the round
                if (i == s) {
                    if (hand.state == HandState.FOLDED) {
                        // Add 2 fists to the front (split the coconut)
                        deque.addFirst(new PlayerHand(hand.player_id, HandState.FIST));  
                        deque.addFirst(new PlayerHand(hand.player_id, HandState.FIST)); 
                    } else if (hand.state == HandState.FIST) {
                        // Add 1 palm_down to the back (milk split)
                        deque.addLast(new PlayerHand(hand.player_id, HandState.PALM_DOWN));
                    } else if (hand.state == HandState.PALM_DOWN) {
                        // Add nothing to represent hand being out of game
                    }
                } else {
                    
                    // Otherwise add same hand to the back when the last syllable is not on it
                    deque.addLast(hand);
                }
            }
        }

        // while loop breaks when only 1 hand left, retrieve the hand (peek) and return the player_id
        PlayerHand lastHand = deque.peek();
        System.out.println(lastHand.player_id);  // Print the winning player's ID

        scanner.close();
    }
}


