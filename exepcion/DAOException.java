package exepcion;

public class DAOException extends RuntimeException {
  public DAOException(String message) {
    super(message);
  }
}
