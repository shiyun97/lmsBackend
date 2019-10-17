package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.BiddingRoundEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(BiddingRound.class)
public class BiddingRound_ { 

    public static volatile SingularAttribute<BiddingRound, Long> biddingRoundId;
    public static volatile SingularAttribute<BiddingRound, Date> startTime;
    public static volatile SingularAttribute<BiddingRound, BiddingRoundEnum> roundType;
    public static volatile SingularAttribute<BiddingRound, Date> endTime;

}