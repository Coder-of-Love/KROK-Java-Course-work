package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class AdditionalInfo {
    private int id;
    private String phone;
    private String adress;

    public AdditionalInfo(int id, String phone, String adress) {
        this.id = id;
        this.phone = phone;
        this.adress = adress;
    }

    public static List<AdditionalInfo> listFromResultSet(ResultSet rs) throws SQLException {
        List<AdditionalInfo> tmp = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String phone = rs.getString("phone");
            String adress = rs.getString("adress");
            tmp.add(new AdditionalInfo(id, phone, adress));
        }
        return tmp;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    @Override
    public String toString() {
        return " phone: " + phone + " adress: " + adress;
    }
}
