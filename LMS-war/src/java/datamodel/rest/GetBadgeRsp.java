/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Badge;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetBadgeRsp {
    private List<Badge> badgeList;

    public GetBadgeRsp() {
    }

    public GetBadgeRsp(List<Badge> badgeList) {
        this.badgeList = badgeList;
    }

    public List<Badge> getBadgeList() {
        return badgeList;
    }

    public void setBadgeList(List<Badge> badgeList) {
        this.badgeList = badgeList;
    }
}
