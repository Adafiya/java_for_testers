package ru.stqa.mantis.manager;

import java.io.IOException;
import java.net.CookieManager;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpSessionHelper extends HelperBase {

  OkHttpClient client;

  public HttpSessionHelper(ApplicationManager manager) {
    super(manager);
    client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager()))
        .build(); //Запоминаем куки
  }

  public void login(String username, String password) {
    //Подготавливаем тело запроса
    RequestBody formBody = new FormBody.Builder()
        .add("username", username)
        .add("password", password)
        .build();
    //Создаем запрос
    Request request = new Request.Builder()
        .url(String.format("%s/login.php", manager.property("web.baseUrl")))
        .post(formBody)
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new RuntimeException("Unexpected code " + response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isLoggedIn() {
    Request request = new Request.Builder()
        .url(
            manager.property("web.baseUrl")) //Это get-запрос, ему тип get указывать не обязательно
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new RuntimeException("Unexpected code " + response);
      }
      String body = response.body().string(); //Сохраняем ответ запроса в body
      return body.contains("<span class=\"user-info\">");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
