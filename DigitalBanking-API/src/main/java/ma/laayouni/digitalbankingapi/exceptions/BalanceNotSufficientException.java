package ma.laayouni.digitalbankingapi.exceptions;

public class BalanceNotSufficientException extends Exception{
    public BalanceNotSufficientException(String message){
        super(message);
    }
}
