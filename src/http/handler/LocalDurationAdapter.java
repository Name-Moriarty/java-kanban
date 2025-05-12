package http.handler;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class LocalDurationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(JsonWriter out, Duration value) throws IOException {
        if (value != null) {
            long minutes = value.toMinutes();
            out.value(minutes); // Записываем количество полных минут
        } else {
            out.nullValue(); // Записываем null
        }
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        long minutes = in.nextLong(); // Число минут
        return Duration.ofMinutes(minutes); // Создаем объект Duration
    }
}
