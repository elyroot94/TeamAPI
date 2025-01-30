package com.team.Exceptions;


public class RessourceNotFound  extends RuntimeException{

    final   private  String message;
    final  private   String Status;

    public  RessourceNotFound(String message, String Status) {
        this.message = message;
        this.Status = Status;
    }

}
