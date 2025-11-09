package ru.stqa.mantis.manager;

import java.io.IOException;
import java.net.CookieManager;
import okhttp3.*;

public class JamesApiHelper extends HelperBase {

  public static final MediaType JSON = MediaType.get("application/json");
  OkHttpClient client;

  public JamesApiHelper(ApplicationManager manager) {
    super(manager);
    client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager()))
        .build(); //Клиент по работе с протоколом http и запоминаем куки
  }

  //Создать пользователя на почтовом сервере
  public void addUser(String email, String password) {
    //Подготовка тела запроса
    RequestBody body = RequestBody.create(
        String.format("{\"password\":\"%s\"}", password), JSON);
    //Создаем запрос
    Request request = new Request.Builder()
        .url(String.format("%s/users/%s", manager.property("james.apiBaseUrl"), email))
        .put(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new RuntimeException("Unexpected code " + response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
