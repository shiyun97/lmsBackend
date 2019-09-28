/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Appeal;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveAppealsRsp {
    List<Appeal> appeals;

    public RetrieveAppealsRsp(List<Appeal> appeals) {
        this.appeals = appeals;
    }

    public RetrieveAppealsRsp() {
    }

    public List<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(List<Appeal> appeals) {
        this.appeals = appeals;
    }
}
