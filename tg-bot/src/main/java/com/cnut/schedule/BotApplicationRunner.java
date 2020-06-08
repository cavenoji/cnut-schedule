package com.cnut.schedule;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import java.util.Arrays;

public class BotApplicationRunner implements Runnable {

  public static void main(String[] args) {
    final ApplicationContext run = Micronaut.run(BotApplicationRunner.class);
    System.out.println(Arrays.toString(args));
  }

  @Override
  public void run() {

  }
}
