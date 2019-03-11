package com.seven.web.example;

import com.seven.web.core.Application;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 19:09
 */
public class Main {

  public static void main(String[] args) throws NoSuchMethodException {
    Application.init();
    Application.start(args);
  }
}
