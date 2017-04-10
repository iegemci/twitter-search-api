package vngrs.enesgemci.tweetsearch.network.converter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by enesgemci on 24/11/2016.
 */
public class TwitterTypeAdapter extends TypeAdapter<SpannableString> {

    @Override
    public void write(JsonWriter writer, SpannableString value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }

        writer.value(value.toString());
    }

    @Override
    public SpannableString read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        String text = reader.nextString();

        ArrayList<int[]> hashtagSpans = getSpans(text, '#');
        ArrayList<int[]> calloutSpans = getSpans(text, '@');

        SpannableString commentsContent = new SpannableString(text);

        setSpanComment(commentsContent, hashtagSpans);
        setSpanUname(commentsContent, calloutSpans);

        return commentsContent;
    }

    private ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return spans;
    }

    private void setSpanComment(SpannableString commentsContent, ArrayList<int[]> hashtagSpans) {
        hashtagSpans.parallelStream().forEach(span -> {
            int hashTagStart = span[0];
            int hashTagEnd = span[1];

            commentsContent.setSpan(new Hashtag(),
                    hashTagStart,
                    hashTagEnd, 0);
        });
    }

    private void setSpanUname(SpannableString commentsUname, ArrayList<int[]> calloutSpans) {
        calloutSpans.parallelStream().forEach(span -> {
            int calloutStart = span[0];
            int calloutEnd = span[1];
            commentsUname.setSpan(new CalloutLink(),
                    calloutStart,
                    calloutEnd, 0);
        });
    }

    class Hashtag extends CharacterStyle {

        TextPaint textPaint;

        @Override
        public void updateDrawState(TextPaint ds) {
            textPaint = ds;
            ds.setColor(ds.linkColor);
            ds.setARGB(255, 30, 144, 255);
        }
    }

    class CalloutLink extends CharacterStyle {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setARGB(255, 51, 51, 51);
            ds.setColor(Color.RED);
        }
    }
}