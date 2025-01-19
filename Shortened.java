public class Shortened {
    public static void main(String[] args) {
        // input
        String input = args[0];
        
        // the result we want via a function
        String result = shorten(input);
        
        // output the result
        System.out.println(result);
    }
    
    // shorten function
    public static String shorten(String input) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            // only want uppercase in result
            if (Character.isUpperCase(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }
}



