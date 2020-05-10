package persistence;

// represents data that can be saved to a file
// Reference: TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)

import model.Users;

public interface Savable {

    void save(Users users);
}
