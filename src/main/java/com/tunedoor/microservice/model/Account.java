package com.tunedoor.microservice.model;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@Data
@Entity
@Table(name = "T_ACCOUNT")
public class Account implements Serializable{

	private static final long serialVersionUID = -3335431292667721811L;
	
	@Id
	private long id;
	@Column(name="USER_ID")
	private long userId;
	@Column(name="BALANCE")
	private int balance;
}
