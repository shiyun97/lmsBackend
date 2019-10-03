/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.BiddingRound;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveBiddingRoundsRsp {
    List<BiddingRound> rounds;

    public RetrieveBiddingRoundsRsp(List<BiddingRound> rounds) {
        this.rounds = rounds;
    }

    public RetrieveBiddingRoundsRsp() {
    }

    public List<BiddingRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<BiddingRound> rounds) {
        this.rounds = rounds;
    }
    
}
