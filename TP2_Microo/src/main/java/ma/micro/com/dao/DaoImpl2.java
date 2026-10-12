package ma.micro.com.dao;


import org.springframework.stereotype.Component;

@Component("dao2")
public class DaoImpl2 implements IDao{

    @Override
    public double getValue() {
        // Cette implémentation alternative retourne 150.0
        return 150.0;
    }


}
