package exceptions;

/**
 *
 * @author Mkhansen;
 */
public class PersonNotFoundException extends Exception{
    
    public PersonNotFoundException(String message){
        super(message);
    }
}
