package com.cnut.schedule.integration;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import java.util.Map;

@Client("${schedule-api.base-url}")
public interface LoginApi {

  @Post("/login")
  Maybe<Map<String, String>> login(@Body Map<String, String> parameters);
}
