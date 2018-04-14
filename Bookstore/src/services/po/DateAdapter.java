package services.po;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/*
 * This class is used to format the Timestamp in the Product Order class when outputting to XML
 */

public class DateAdapter extends XmlAdapter<String, Timestamp>{

	@Override
	public String marshal(Timestamp t) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd").format(t);	//returning the date of the purchase
	}

	@Override
	public Timestamp unmarshal(String s) throws Exception {
		return new Timestamp(Timestamp.parse(s));			//deprecated but won't be used in this project anyway
	}

}
