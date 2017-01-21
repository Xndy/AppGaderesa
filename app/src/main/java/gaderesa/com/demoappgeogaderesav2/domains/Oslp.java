/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaderesa.com.demoappgeogaderesav2.domains;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author Xndy
 */

public class Oslp implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer slpCode;
    private String slpName;
    private String memo;
    private BigDecimal commission;
    private Short groupCode;
    private Character locked;
    private Character dataSource;
    private Short userSign;
    private Integer empID;
    private boolean active;
    private String password;
    private Integer userId;
    
    public Oslp() {
    }

    public Oslp(Integer slpCode) {
        this.slpCode = slpCode;
    }


    public Oslp(Integer slpCode, String slpName, String memo, Integer userId) {
		super();
		this.slpCode = slpCode;
		this.slpName = slpName;
		this.memo = memo;
		this.userId = userId;
	}
    
    public Oslp(Integer slpCode, String slpName, String memo) {
		super();
		this.slpCode = slpCode;
		this.slpName = slpName;
		this.memo = memo;
	}

	public Integer getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Integer slpCode) {
        this.slpCode = slpCode;
    }

    public String getSlpName() {
        return slpName;
    }

    public void setSlpName(String slpName) {
        this.slpName = slpName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Short getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Short groupCode) {
        this.groupCode = groupCode;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }
    

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (slpCode != null ? slpCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oslp)) {
            return false;
        }
        Oslp other = (Oslp) object;
        if ((this.slpCode == null && other.slpCode != null) || (this.slpCode != null && !this.slpCode.equals(other.slpCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return slpName;
    }
    
}
