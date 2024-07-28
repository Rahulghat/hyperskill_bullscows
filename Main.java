package bullscows;

import java.util.*;

public class Main {

    static String range_op = "";

    public static void main(String[] args) {
        String randomString = "", temp1 = "";
        int digits = 0;
        int range = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        temp1 = scanner.nextLine();
        if (isNumeric(temp1)) {
            digits = Integer.parseInt(temp1);
        } else {
            System.out.println("Error: " + temp1 + " isn't a valid number.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");
        range = Integer.parseInt(scanner.nextLine());
        if (digits <= 10 && range <= 36 && digits > 0) {
            //System.out.println(range_op);
            try {
                randomString = randomGenerator(digits, range);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: it's not possible to generate a code with a length of " + digits + " with " + range + " unique symbols.");
                System.exit(1);
            }

            System.out.println("Okay, let's start a game!");

            // System.out.println("Secret:"+randomString);
            int turn = 1;
            while (turn > 0) {
                String answer = scanner.nextLine();
                if (comparecoreorbull(randomString, answer)) {
                    System.out.print("Congratulations! You guessed the secret code.");
                    turn = 0;
                } else {
                    turn += 1;
                }

            }
        }
        else if (range>36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else {
            System.out.print("Error: can't generate a secret number with a length of " + digits + " because there aren't enough unique digits.");
        }
    }


    public static String randomGenerator(int length, int range) {
        String mybucket = "0123456789abcdefghijklmnopqrstuvwxyz";
        range_op = "The secret is prepared: ****** (";
        if (range > 9) {
            range_op += "(0-9,";
            range_op += mybucket.charAt(10) + "," + mybucket.charAt(range - 1) + ")";
        }
        System.out.println(range_op);

        char[] mybucketchar = mybucket.substring(0, range - 1).toCharArray();
        List<Character> randomList = new ArrayList<>();
        for (char c : mybucketchar) {
            randomList.add(c);
        }


        Collections.shuffle(randomList);
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, length)) {
            result.append(ch);
        }
        return result.toString();
    }


    public static Boolean comparecoreorbull(String original, String answer) {
        char[] orgNum = original.toCharArray();
        char[] aswerNum = answer.toCharArray();
        int bull = 0;
        int cow = 0;
        if (original.equals(answer)) {
            bull = original.length();
            grade(bull, cow, original);
            return true;
        } else {
            for (int i = 0; i < orgNum.length; i++) {
                if (orgNum[i] == aswerNum[i]) {
                    bull += 1;
                } else {
                    for (char k : aswerNum) {
                        if (orgNum[i] == k) {
                            cow += 1;
                            continue;
                        }
                    }
                }
            }

        }
        grade(bull, cow, original);
        return false;
    }

    private static void grade(int bull, int cow, String original) {
        if (bull > 0 && cow > 0) {
            System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s).");
        } else if (bull > 0 && cow == 0) {
            System.out.println("Grade: " + bull + " bull(s).");
        } else if (bull == 0 && cow > 0) {
            System.out.println("Grade: " + cow + " cows(s).");
        } else if (bull == 0 && cow == 0) {
            System.out.println("Grade: None.");
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}