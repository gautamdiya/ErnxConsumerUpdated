package LoginPage;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    public static String getOtpFromEmail(String host, String port, String username, String password, String subjectKeyword, int timeoutInSeconds) {
        String otp = null;
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.gmail.com");
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int retries = timeoutInSeconds / 5;
            Message message = null;

            while (retries-- > 0 && message == null) {
                Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                for (int i = messages.length - 1; i >= 0; i--) {
                    if (messages[i].getSubject().contains(subjectKeyword)) {
                        message = messages[i];
                        break;
                    }
                }
                if (message == null) {
                    Thread.sleep(5000); // Wait before retry
                }
            }

            if (message != null) {
                String content = getTextFromMessage(message);

                // Look for 4-digit OTP
                Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
Matcher matcher = pattern.matcher(content);

if (matcher.find()) {
    otp = matcher.group(); // first 4-digit match
} else {
    System.out.println("No OTP found in email content.");
}
            } else {
                System.out.println("No matching message found.");
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return otp;
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (part.isMimeType("text/plain")) {
                    return part.getContent().toString();
                } else if (part.isMimeType("text/html")) {
                    return part.getContent().toString();
                }
            }
        }
        return "";
    }
}
