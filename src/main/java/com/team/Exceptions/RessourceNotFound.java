package team.fr.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class RessourceNotFound  extends RuntimeException{

    private final String message;
    private final  String Status;


}
