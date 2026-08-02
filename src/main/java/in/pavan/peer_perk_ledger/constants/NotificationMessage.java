package in.pavan.peer_perk_ledger.constants;

public class NotificationMessage {
    public static String getTransaferMsg(String userName, int points, String message){
        return String.format("%s sent you %d points: '%s'", userName, points, message);
    }
}
