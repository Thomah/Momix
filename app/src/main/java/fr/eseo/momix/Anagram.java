package fr.eseo.momix;

public class Anagram {

    private String answer;
    private String generated = "";

    public Anagram(String answer) {
        this.answer = answer;

        String[] words = answer.split(" ");
        for(String word : words) {
            this.generated+= makeAnagram(word) + " ";
        }
        this.generated = this.generated.substring(0, this.generated.length()-1);
    }

    public String makeAnagram(String word) {

        char cTemp;
        char[] input = stringToChar(word);
        int sizeChar = input.length;
        String s = "";

         do {
            s = "";
            for (int i = 0; i < sizeChar; i++) {

                int indexToMove = (int) (Math.random() * (sizeChar - i));
                cTemp = input[indexToMove];
                s += cTemp;

                for (int k = indexToMove; k < sizeChar - 1; k++) {
                    input[k] = input[k + 1];
                }
            }
        } while(compare(s, input) && input.length > 1);

        return s;
    }

    private char[] stringToChar(String s) {
        char[] text = new char[s.length()];
        for (int i=0; i<s.length(); i++) {
            text[i] = s.charAt(i);
        }
        return text;
    }

    private boolean compare(String s, char[] c) {
        boolean equal = s.length() == c.length;

        int k = 0;
        while(equal && k < s.length()) {
            equal = s.charAt(k) == c[k];
            k++;
        }

        return equal;
    }

    public String getAnswer() {
        return answer;
    }

    public String getGenerated() {
        return generated;
    }
}
