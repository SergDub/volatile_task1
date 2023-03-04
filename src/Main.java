import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void countNiceWords(String word) {
        if (word.length() == 3) {
            counter1.getAndIncrement();
        } else if (word.length() == 4)
            counter2.getAndIncrement();
        else counter3.getAndIncrement();
    }

    public static boolean isPalindrom(String word) {
        boolean isWordPalindrom = false;
        int j = 0;
        while (j < (int) (word.length() / 2)) {
            if (word.charAt(j) == word.charAt(word.length() - 1 - j))
                isWordPalindrom = true;
            else {
                isWordPalindrom = false;
                break;
            }
            j++;
        }
        return isWordPalindrom;
    }

    public static boolean isSameLetters(String word) {
        boolean sameLetters = false;
        int j = 0;
        while (j < word.length()) {
            if (word.charAt(0) == word.charAt(j))
                sameLetters = true;
            else {
                sameLetters = false;
                break;
            }
            j++;
        }
        return sameLetters;
    }

    public static boolean isIncreasing(String word) {
        boolean isIncreasing = false;
        int j = 0;
        while (j < word.length() - 1) {
            if ((word.charAt(j) < word.charAt(j + 1)) || (word.charAt(j) == word.charAt(j + 1)))
                isIncreasing = true;
            else {
                isIncreasing = false;
                break;
            }
            j++;
        }
        return isIncreasing;
    }


    public static AtomicInteger counter1 = new AtomicInteger();
    public static AtomicInteger counter2 = new AtomicInteger();
    public static AtomicInteger counter3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            System.out.println(texts[i]);
        }

        Thread checkPalindrom = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {

                if ((isPalindrom(texts[i])) && (!isSameLetters(texts[i]))) {
                    countNiceWords(texts[i]);
                    System.out.println("Красивое слово по критерию 1  " + texts[i]);
                }
            }
        });
        checkPalindrom.start();

        Thread checkSameLetters = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {

                if (isSameLetters(texts[i])) {
                    countNiceWords(texts[i]);
                    System.out.println("Красивое слово по критерию 2  " + texts[i]);
                }
            }
        });
        checkSameLetters.start();

        Thread checkIncreasing = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {

                if ((isIncreasing(texts[i])) && (!isSameLetters(texts[i]))) {
                    countNiceWords(texts[i]);
                    System.out.println("Красивое слово по критерию 3 " + texts[i]);
                }
            }
        });
        checkIncreasing.start();

        checkPalindrom.join();
        checkSameLetters.join();
        checkIncreasing.join();


        System.out.println("Красивых слов с длиной 3: " + counter1 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counter2 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counter3 + " шт.");
    }
}