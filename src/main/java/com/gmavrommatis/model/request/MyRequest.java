package com.gmavrommatis.model.request;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record MyRequest(String foo, int bar) {}
