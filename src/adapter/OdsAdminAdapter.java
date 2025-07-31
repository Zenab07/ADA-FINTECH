package adapter;

import models.Admin;
import models.UserAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OdsAdminAdapter implements DataReader<Admin> {

    private final OdsSource odsSource;
    private final List<UserAccount> userAccounts;

    public OdsToAdminAdapter(OdsSource odsSource, List<UserAccount> userAccounts) {
        this.odsSource = odsSource;
        this.userAccounts = userAccounts;
    }

    @Override
    public List<Admin> readData() {
        List<Admin> admins = new ArrayList<>();
        List<List<String>> data = odsSource.getOdsData();

        Map<String, UserAccount> accountMap = userAccounts.stream()
                .collect(Collectors.toMap(UserAccount::getLogin, ua -> ua));

        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);
            if (row.size() >= 5) {
                Admin admin = new Admin();
                admin.setFirstName(row.get(0));
                admin.setLastName(row.get(1));
                admin.setEmail(row.get(2));
                admin.setPrivilege(row.get(3));

                String loginKey = row.get(4);
                admin.setUserAccount(accountMap.get(loginKey));

                admins.add(admin);
            }
        }
        return admins;
    }
}
