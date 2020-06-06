package cnut.schedule.proxy.web;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;

@Controller("/user")
public class UserResource {

  @Get("/my-info")
  @Secured(SecurityRule.IS_ANONYMOUS)
  public Single<Map> myInfo(@Nullable Principal principal) {
    if (principal == null) {
      return Single.just(Collections.singletonMap("isLoggedIn", false));
    }
    return Single.just(CollectionUtils.mapOf("isLoggedIn", true, "username", principal.getName()));
  }
}
