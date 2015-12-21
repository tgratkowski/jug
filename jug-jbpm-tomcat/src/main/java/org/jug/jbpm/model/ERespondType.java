package org.jug.jbpm.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum ERespondType {
	@XmlEnumValue("YES")
	YES(1), @XmlEnumValue("NO")
	NO(2), @XmlEnumValue("CANCEL")
	CANCEL(4), @XmlEnumValue("ERROR")
	ERROR(8);

	private int value;

	/**
	 * @param value
	 */
	private ERespondType(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
