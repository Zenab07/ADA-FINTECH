package adapter;

import models.UserAccount;
import models.enumeration.CompteType;

import java.util.ArrayList;
import java.util.List;

public class OdsUserAccountAdapter implements DataReader<UserAccount> {
    private final OdsSource odsSource;

    public OdsToUserAccountAdapter(OdsSource odsSource) {
        this.odsSource = odsSource;
    }

    public OdsUserAccountAdapter(OdsSource userSource) {

    }

    @Override
    public List<UserAccount> readData() {
        List<UserAccount> accounts = new ArrayList<>();
        List<List<String>> data = odsSource.getOdsData();

        for (int i = 1; i < data.size(); i++) { // ligne 0 = entêtes
            List<String> row = data.get(i);
            if (row.size() >= 3) {
                UserAccount ua = new UserAccount();
                ua.setLogin(row.get(0));
                ua.setPassword(row.get(1));
                ua.setCompteType(CompteType.valueOf(row.get(2).toUpperCase()));
                accounts.add(ua);
            }
        }
        return accounts;
    }
}
