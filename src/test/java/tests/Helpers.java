package tests;

public class Helpers {

    public static String getMethodName() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[ste.length - 1].getMethodName();
    }

    public static boolean containsAllWords(String word, String ...keywords) {
        for (String k : keywords) {
            if (!word.contains(k)) return false;
        }
        return true;
    }

    public static void printTestNameWithState(String testName, String state) {
        System.out.println(state + ": " + testName);
    }
}
