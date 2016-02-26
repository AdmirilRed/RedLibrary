/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redlibrarian.organization;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import redlibrarian.login.Password;

/**
 *
 * @author Joseph Manahan
 */
@Entity
public class OrgUser implements Serializable {
    
    @Id
    private final String username;
    private final byte [] passwordHash;
    private final byte [] salt;

    public OrgUser() {
        this.username = null;
        this.salt = Password.getNextSalt();
        String temp = Password.generateRandomPassword(10);
        this.passwordHash = Password.hash(temp.toCharArray(), salt);
        System.out.println(temp);
    }
    
    
    public OrgUser(String username, String password) {
        this.username = username;
        this.salt = Password.getNextSalt();
        this.passwordHash = Password.hash(password.toCharArray(), salt);
    }

    public String getUsername() {
        return username;
    }

    public boolean verifyPassword(String password) {
        return Password.isExpectedPassword(password.toCharArray(), salt, passwordHash);
    }
    
}
