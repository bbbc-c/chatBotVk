package vkBot.core.commands;

import com.vk.api.sdk.actions.Photos;
import com.vk.api.sdk.objects.messages.Message;
import vkBot.VkManager;
import vkBot.core.Command;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Photo extends Command {
    Properties properties = new Properties();
    public Photo(String name) {
        super(name);
        try {
            properties.load(new FileInputStream("src/main/resources/recognizeconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exec(Message message) {

        String answer = "";
        try {
            InputStream inputStream = message.getAttachments().get(0).getPhoto().getSizes().get(message.getAttachments().get(0).getPhoto().getSizes().size()-1).getUrl().toURL().openStream();
            int idImage = message.getAttachments().get(0).getPhoto().getId();
            String savePathImage = properties.getProperty("save.image");
            Files.copy(inputStream, Paths.get(savePathImage + "/" + idImage + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            File tempImage = new File(savePathImage + "/" + idImage + ".jpg");
            String cnnComm = "py " + properties.getProperty("recognize.script") + " --model " +
                    properties.getProperty("recognize.model") + " --image " + tempImage.getPath();
            Process process = Runtime.getRuntime().exec(cnnComm);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String[] accuracies = in.readLine().replace("[", "")
                    .replace("]", "")
                    .split(", ");
            double healthyAccuracy = Double.parseDouble(accuracies[0]);
            double powderyAccuracy = Double.parseDouble(accuracies[1]);
            double rustAccuracy = Double.parseDouble(accuracies[2]);
            if (healthyAccuracy > powderyAccuracy && healthyAccuracy > rustAccuracy && healthyAccuracy >= 0.8) {
                answer = "Ваше растение здоровое!";
            } else if (powderyAccuracy > healthyAccuracy && powderyAccuracy > rustAccuracy && powderyAccuracy >= 0.8) {
                answer = "У вашего растения мучнистая роса";
            } else if (rustAccuracy > powderyAccuracy && rustAccuracy > healthyAccuracy && rustAccuracy >= 0.8) {
                answer = "У вашего растения ржавчина";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new VkManager().sendMessage(answer, message.getPeerId(), message.getRandomId());
    }

}
