import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger countLength3Char = new AtomicInteger(0);
    private static final AtomicInteger countLength4Char = new AtomicInteger(0);
    private static final AtomicInteger countLength5Char = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[1000];//100_000
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //Is Palindrom
        Thread isPalindormThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean isPalindrome = checkIsPalindrome(texts[i]);
                if (isPalindrome) {
                    countLength4Char.incrementAndGet();
                }
            }
        });
        isPalindormThread.start();

        // Is Same Letters
        Thread isSameLettersThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean isChecked = checkIsSameLetters(texts[i]);
                if (isChecked) {
                    switch (texts[i].length()) {
                        case 3: countLength3Char.incrementAndGet();
                        case 4: countLength4Char.incrementAndGet();
                        case 5: countLength5Char.incrementAndGet();
                    }
                }
            }
        });
        isSameLettersThread.start();

        // Is OrderAsc
        Thread isAscOrderThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean isChecked = checkIsOrderAsc(texts[i]);
                if (isChecked) {
                    System.out.println(texts[i]);
                    switch (texts[i].length()) {
                        case 3: countLength3Char.incrementAndGet();
                        case 4: countLength4Char.incrementAndGet();
                        case 5: countLength5Char.incrementAndGet();
                    }
                }
            }
        });
        isAscOrderThread.start();


        isPalindormThread.join();
        isSameLettersThread.join();
        isAscOrderThread.join();

        System.out.println("Красивых слов с длиной 3: " + countLength3Char + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4Char + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5Char + " шт");

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean checkIsPalindrome(String word) {
        if (word.length() % 2 == 0) {
            int leftPosition = 0;
            for (int i = word.length() - 1; i >= word.length()/2; i--) {
                if (word.charAt(leftPosition) != word.charAt(i)) {
                    return false;
                }
                leftPosition++;
            }
            return true;
        }
        return false;
    }

    public static boolean checkIsSameLetters(String word) {

        char prevChar = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (prevChar != word.charAt(i)) {
                return false;
            }
            prevChar = word.charAt(i);
        }

        return true;
    }

    public static boolean checkIsOrderAsc(String word) {

        int prevCode = (int) word.charAt(0);

        for (int i = 1; i < word.length(); i++) {
            if (prevCode > (int) word.charAt(i)) {
                return false;
            }
            prevCode = (int) word.charAt(i);
        }

        return true;
    }

}
