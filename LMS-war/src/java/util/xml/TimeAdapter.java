/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.xml;

import java.time.LocalTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author AfiqahRashid
 */
public class TimeAdapter extends XmlAdapter<String, LocalTime>
{

    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v);
    }

    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}
