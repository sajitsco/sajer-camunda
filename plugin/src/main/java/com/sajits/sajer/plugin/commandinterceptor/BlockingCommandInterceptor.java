package com.sajits.sajer.plugin.commandinterceptor;

import org.camunda.bpm.engine.impl.cmd.SaveTaskCmd;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;

public class BlockingCommandInterceptor extends CommandInterceptor {


  public <T> T execute(Command<T> command) {
      if(command instanceof SaveTaskCmd){
        SaveTaskCmd cmd = (SaveTaskCmd)command;
        System.out.println("\n ============================= \n");
        System.out.println("-----BlockingCommandInterceptor.execute-----");
        System.out.println(cmd.toString());
        System.out.println("\n ============================= \n");
      }

    return next.execute(command);
  }

}