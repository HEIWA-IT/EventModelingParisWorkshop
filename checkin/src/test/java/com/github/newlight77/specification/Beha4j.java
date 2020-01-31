package com.github.newlight77.specification;

public class Beha4j {

  private boolean success = true;

  public static Beha4j scenario(final String name) {
    System.out.println("Scenario : " + name);
    return new Beha4j();
  }
  
  public Beha4j given(final String name, Given given) {
    System.out.println("Given : " + name);
    try {
      given.execute(name);
    } catch (RuntimeException e) {
      success = false;
      throw e;
    }
    return this;
  }

  public Beha4j when(final String name, final When when) {
    System.out.println("When " + name);
    try {
      when.execute(name);
    } catch (RuntimeException e) {
      success = false;
      throw e;
    }
    return this;
  }

  public Beha4j then(final String name, final Then then) {
    System.out.println("Then " + name);
    try {
      then.execute(name);
    } catch (RuntimeException e) {
      success = false;
      throw e;
    }
    return this;
  }

  public void print() {
    System.out.println("Test " + (success ? "passed" : "failed"));
  }
}
