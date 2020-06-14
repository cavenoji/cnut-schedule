package com.cnut.schedule;

import io.micronaut.runtime.Micronaut;

public class BotApplicationRunner implements Runnable {

  public static void main(String[] args) {
    Micronaut.run(BotApplicationRunner.class);
  }

  @Override
  public void run() {}
}
