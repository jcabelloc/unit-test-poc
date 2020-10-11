package pe.itana.unitestpoc.utils;

public class ZytrustException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;

  private final String errorCode;
  

  public ZytrustException(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
  
}
