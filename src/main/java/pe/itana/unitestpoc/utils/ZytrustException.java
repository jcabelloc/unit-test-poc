package pe.itana.unitestpoc.utils;

public class ZytrustException extends RuntimeException{
  
  private String errorCode;
  
  public ZytrustException() {
    
  }
  
  public ZytrustException(String errorCode) {
    this.errorCode = errorCode;
  }
  
}
