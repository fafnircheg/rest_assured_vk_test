package tests;

public final class EndPoints {
    private static final String ACCESS_TOKEN = ""; // Paste valid access_token here
    private static String USER_ID = ""; // current userId
    private static final String VK_API_VERSION = "5.131";

    public static String getConstructedPath(String category, String function, String params) {
        return getConstructedPath(category, function, params, USER_ID);
    }

    public static String getConstructedPath(String category, String function, String params, String userIds) {
        return category + "." + function + "?user_ids=" + userIds + "&access_token=" + ACCESS_TOKEN + "&v=" + VK_API_VERSION + "&" + params;
    }
}
