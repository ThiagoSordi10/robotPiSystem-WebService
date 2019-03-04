package br.com.thiago.robotPi.converter;


import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<Date, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(Date date) {
		return (date == null ? null : new Timestamp(date.getTime()));
	}

	@Override
	public Date convertToEntityAttribute(Timestamp sqlTimestamp) {
		return (sqlTimestamp == null ? null
				: new Date(sqlTimestamp.getTime()));
	}
}