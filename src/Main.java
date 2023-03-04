import java.util.Random;

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
            counter1++;
            //System.out.println(counter1);
        } else if (word.length() == 4)
            counter2++;
        else counter3++;
    }


    public static volatile long counter1 = 0;
    public static volatile long counter2 = 0;
    public static volatile long counter3 = 0;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            System.out.println(texts[i]);
        }

        Thread checkPalindrom = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean isWordPalindrom = false;
                int j = 0;
                while (j < (int) (texts[j].length() / 2)) {
                    if (texts[i].charAt(j) == texts[i].charAt(texts[i].length() - 1 - j))
                        isWordPalindrom = true;
                    else {
                        isWordPalindrom = false;
                        break;
                    }
                    j++;
                }
                if (isWordPalindrom) {
                    countNiceWords(texts[i]);
                    System.out.println("Красивое слово по критерию 1  " + texts[i]);
                }
            }
        });
        checkPalindrom.start();

        Thread checkIncreasing = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean isIncreasing = false;
                int j = 0;
                while (j < texts[i].length() - 1) {
                    if ((texts[i].charAt(j) < texts[i].charAt(j + 1))||(texts[i].charAt(j) == texts[i].charAt(j + 1)))
                        isIncreasing = true;
                    else {
                        isIncreasing = false;
                        break;
                    }
                    j++;
                }
                if (isIncreasing) {
                    countNiceWords(texts[i]);
                    System.out.println("Красивое слово по критерию 3 " + texts[i]);
                }
            }
        });
        checkIncreasing.start();

        checkPalindrom.join();
        checkIncreasing.join();


        System.out.println("Красивых слов с длиной 3: " + counter1 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counter2 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counter3 + " шт.");
    }
}