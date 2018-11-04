package com.contaazul.boleto.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.contaazul.boleto.api.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "BANKSLIP")
@ApiModel(description="All details about the Bankslip")
public class Bankslip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045555073689838576L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator")
	
	@ApiModelProperty(notes="id ", dataType="UUID")
	@Column
	private UUID id;
	
	@ApiModelProperty(notes="Due Date (yyyy-MM-dd) ",required=true,allowEmptyValue=false,dataType="Date")
	@NotNull(message="Due Date: required field!")
	@Column(name = "due_date", nullable=false)
	private Date dueDate;
	
	@ApiModelProperty(notes="Total in Cents ",required=true,allowEmptyValue=false,dataType="BigDecimal")
	@NotNull(message="Total In Cents: required field!")
	@Digits(integer=9, fraction=0)  
	@Column(name = "total_in_cents",nullable=false)
	private BigDecimal totalInCents;
	
	@ApiModelProperty(notes="Constumer",required=true,allowEmptyValue=false,dataType="String")
	@NotBlank(message="Customer: required field!")
	@Column(name = "customer",nullable=false)
	private String customer;
	
	@ApiModelProperty(notes="Status - generated by the system",allowableValues="PENDING,PAID,CANCELED", dataType="String")
	@Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
	
	@ApiModelProperty(notes="Payment Date (yyyy-MM-dd) ",required=false,dataType="Date")
	@Column(name = "payment_date",nullable=true)
	private Date paymentDate;	

	@ApiModelProperty(notes="fine",required=false,dataType="BigDecimal")
	@Digits(integer=9, fraction=0)  
	@Column(name = "fine",nullable=true)
	private BigDecimal fine;	
	

	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public BigDecimal getTotalInCents() {
		return totalInCents;
	}


	public void setTotalInCents(BigDecimal totalInCents) {
		this.totalInCents = totalInCents;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public StatusEnum getStatus() {
		return status;
	}


	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	public Date getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}


	public BigDecimal getFine() {
		return fine;
	}


	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((fine == null) ? 0 : fine.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((totalInCents == null) ? 0 : totalInCents.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bankslip other = (Bankslip) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (fine == null) {
			if (other.fine != null)
				return false;
		} else if (!fine.equals(other.fine))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (status != other.status)
			return false;
		if (totalInCents == null) {
			if (other.totalInCents != null)
				return false;
		} else if (!totalInCents.equals(other.totalInCents))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Bankslip [id=" + id + ", dueDate=" + dueDate + ", totalInCents=" + totalInCents + ", customer="
				+ customer + ", status=" + status + ", paymentDate=" + paymentDate + ", fine=" + fine + "]";
	}


}
