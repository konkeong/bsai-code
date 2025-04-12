package common;

import java.util.List;

import org.apache.commons.text.WordUtils;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;

public final class Util {

    private Util() {
        // intentionally left blank
    }

    public static String getFirstReply(List<Generation> output) {
        return output.getFirst().getOutput().getText();
    }

    public static void display(String query, List<Message> messages, List<Generation> output) {
        System.out.println("========================================================================");
        System.out.println(query);
        System.out.println();
        System.out.println("## Messages/Context");
        int nx = 0;
        for (Message message : messages) {
            nx += 1;
            System.out.println("### Messages[" + nx + "]");
            String[] lines = WordUtils.wrap(message.getText(), 72, "\n", true)
                    .split("\\n");
            for (String line : lines) {
                System.out.println(line);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("## Replies");
        nx = 0;
        for (Generation reply : output) {
            nx += 1;
            System.out.println("### Replies[" + nx + "]");
            String[] lines = WordUtils.wrap(reply.getOutput().getText(), 72, "\n", true)
                    .split("\\n");
            for (String line : lines) {
                System.out.println(line);
            }
            System.out.println();
        }
        System.out.println("========================================================================");
        System.out.println();
    }

}
