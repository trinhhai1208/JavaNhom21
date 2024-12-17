package backend.utils;

import backend.models.Account;

public class SessionManager {
    //khởi tạo một Account tại thời điểm chương trình khởi động
    private static Account currentUser;
    //phiên làm việc
    public static void login(Account user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
