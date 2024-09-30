public class WordCount {
        String word;
        int count;
        WordCount(String a, int b) {
            word = a;
            count = b;
        }
        public String getWord() {return word;}
        public int getCount() {return count;}
        public void addCount() {count++;}
        public String toString () {return "\"" + word + "\"" + " with " + count + " occurences.";}
}
