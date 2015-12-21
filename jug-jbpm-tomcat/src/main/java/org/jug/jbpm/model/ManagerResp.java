package org.jug.jbpm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ManagerResp implements Serializable{
	
	private static final long serialVersionUID = 3423063380192482919L;
	private int id;
	private String manager;
	private ERespondType respond;

	public ManagerResp() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return the respond
	 */
	public ERespondType getRespond() {
		return respond;
	}

	/**
	 * @param respond the respond to set
	 */
	public void setRespond(ERespondType respond) {
		this.respond = respond;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManagerResp [id=" + id + ", manager=" + manager + ", respond=" + respond + "]";
	}

	
}
