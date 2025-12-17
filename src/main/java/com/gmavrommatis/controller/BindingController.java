package com.gmavrommatis.controller;

import com.gmavrommatis.dto.FilterBean;
import com.gmavrommatis.model.request.MyRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.multipart.CompletedFileUpload;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BindingController {

  @Post("/body")
  public HttpResponse<String> withBody(@Body MyRequest req) {
    return HttpResponse.ok("Received body foo=" + req.foo() + ", bar=" + req.bar());
  }

  @Get("/cookie")
  public HttpResponse<String> getCookieValueDemo(
      @CookieValue("sessionId") String sessionId, HttpRequest request) {
    log.info("" + request.getCookies().findCookie("sessionId").get().getName());
    return HttpResponse.ok("Cookie sessionId=" + sessionId);
  }

  @Get("/get-cookie")
  public HttpResponse<String> getCookie() {
    Cookie cookie =
        Cookie.of("sessionId", "abc123")
            .path("/") // send on all paths
            .maxAge(Duration.ofHours(2)) // expire in 2 hours
            .httpOnly(true) // inaccessible to JS
            .secure(true); // sent only over HTTPS

    return HttpResponse.ok("Cookie has been set").cookie(cookie);
  }

  @Get("/header")
  public HttpResponse<String> headerDemo(@Header("X-Custom-Header") Optional<String> header) {
    return header
        .map(h -> HttpResponse.ok("Header X-Custom-Header=" + h))
        .orElse(HttpResponse.status(HttpStatus.BAD_REQUEST, "Missing X‑Custom‑Header"));
  }

  @Get("/query")
  public HttpResponse<String> queryDemo(@QueryValue("param") String param) {
    return HttpResponse.ok("Query param=" + param);
  }

  @Get("/path/{id}")
  public HttpResponse<String> pathDemo(@PathVariable Long id) {
    return HttpResponse.ok("Path id=" + id);
  }

  @Post(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA)
  public HttpResponse<String> uploadDemo(@Part("file") CompletedFileUpload file)
      throws IOException {
    return HttpResponse.ok(
        "Uploaded file: " + file.getFilename() + " (" + file.getBytes().length + " bytes)");
  }

  @Get("/bean")
  public HttpResponse<String> beanDemo(@RequestBean FilterBean bean) {
    StringBuilder sb = new StringBuilder("FilterBean => ");
    sb.append("search=")
        .append(bean.getSearch())
        .append(", filterType=")
        .append(bean.getFilterType().orElse("[none]"))
        .append(", locale=")
        .append(bean.getLocale().orElse("[none]"));
    return HttpResponse.ok(sb.toString());
  }
}
