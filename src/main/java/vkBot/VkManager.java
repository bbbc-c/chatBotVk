package vkBot;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;

public class VkManager {
    public static VkCore vkCore;

    static {
        try {
            vkCore = new VkCore();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg, int peerId,int randomId){
        if (msg == null){
            System.out.println("null");
            return;
        }
        try {
            vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).randomId(randomId).message(msg).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public MessagesSendQuery getSendQuery(){
        return vkCore.getVk().messages().send(vkCore.getActor());
    }

}
