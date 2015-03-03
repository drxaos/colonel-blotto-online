package blotto.utils.mail;

public class MailMessageContent {
    String from
    String to
    String plainText
    String html
    String subject

    Map<String, List<String>> headers
    byte[] messageData
}
