package com.gmavrommatis.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@Serdeable
@Data
@NoArgsConstructor
public class FilterBean {
  @QueryValue("search")
  String search;

  @Header("X-Filter-Type")
  Optional<String> filterType;

  @CookieValue("locale")
  Optional<String> locale;
}
