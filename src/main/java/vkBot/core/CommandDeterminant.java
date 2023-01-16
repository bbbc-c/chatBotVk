package vkBot.core;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import vkBot.core.commands.Photo;
import vkBot.core.commands.Unknown;

import java.util.Collection;
import java.util.List;

public class CommandDeterminant {
    public static Command getCommand(Collection<Command> commands, Message message) {
        List<MessageAttachment> messageAttachments = message.getAttachments();
        if (messageAttachments.size() != 0 && messageAttachments.get(0).getPhoto() != null){
            return new Photo("photo");
        }
        else {
            String body = message.getText();
            for (Command command : commands
            ) {
                if (command.name.equals(body.split(" ")[0])) {
                    return command;
                }
            }
            return new Unknown("unknown");
        }
    }
}
