package ma.laayouni.digitalbankingapi.exceptions;

public class TransferToTheSameAccountException extends Exception {
    public TransferToTheSameAccountException(String message) {
        super(message);
    }
}