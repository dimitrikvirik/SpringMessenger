package git.dimitrikvirik.springmiddleexam.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class MessageServiceImp implements MessageService {
    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    String JSON_DATA = "storage/messages.json";

    @Override
    public void write(MessageView messageView) {
        List<MessageView> messageViewList = read();
        int size = messageViewList.size();
        messageView.setMessageId(size + 1);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("Asia/Tbilisi"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String formattedString = zonedDateTimeNow.format(formatter);
        messageView.setCreateDate(formattedString);
        messageViewList.add(messageView);
        write(messageViewList);
    }

    @Override
    public void write(List<MessageView> messageViewList) {
        try (Writer writer = new FileWriter(JSON_DATA)) {
            gson.toJson(messageViewList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MessageView> read() {
        List<MessageView> messageViews = null;
        try {
            messageViews = gson.fromJson(new FileReader(JSON_DATA), new TypeToken<List<MessageView>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return messageViews == null ? new ArrayList<>() : messageViews;
    }

    @Override
    public void edit(int messageId, String text) throws RecordNotFoundException {
        var messageViews = read();

        for (MessageView messageView : messageViews) {
            if (messageView.getMessageId() == messageId) {
                messageView.setText(text);
                write(messageViews);
                return;
            }
        }
        throw new RecordNotFoundException(String.format("Message with id %s", messageId));
    }

    @Override
    public void delete(int messageId) {
        var messageViews = read();

        Iterator<MessageView> messageViewIterator = messageViews.iterator();
        while (messageViewIterator.hasNext()) {
            var messageView = messageViewIterator.next();
            if (messageView.getMessageId() == messageId) {
                messageViewIterator.remove();
                write(messageViews);
                return;
            }
        }

        throw new RecordNotFoundException(String.format("Message with id %s", messageId));
    }
}
