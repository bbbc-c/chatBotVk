package vkBot.core.commands;

import com.vk.api.sdk.objects.messages.Message;
import vkBot.VkManager;
import vkBot.core.Command;

public class Unknown extends Command {
    public Unknown(String name) {
        super(name);
    }

    @Override
    public void exec(Message message) {
        new VkManager().sendMessage("Для того, чтобы узнать, здорово ли ваше растение, необходимо отправить в сообщении боту фотографию растения и в ответ вы получите название его патологии, либо же сообщение о том, что ваше растение здорово.\n" +
                "Бот умеет обрабатывать только изображения растений, поэтому если вы попробуете отправить что-либо кроме этого, то увидете это сообщение!", message.getPeerId(), message.getRandomId());
    }
}
