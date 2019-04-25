import classes.BusinessLogic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class TestImport {

    @Before
    void start(){
        BusinessLogic.connect();
    }

    @After
    void end(){
        BusinessLogic.disconnect();
    }
}
