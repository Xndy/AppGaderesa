package gaderesa.com.demoappgeogaderesav2.domains;

import java.io.Serializable;
import java.util.Date;


public class Oprj implements Serializable {

    private static final long serialVersionUID = 1L;
    private String prjCode;
    private String prjName;
    private Character locked;
    private Character dataSource;
    private Short userSign;
    private Date validFrom;
    private Date validTo;
    private Character active;
    private Integer logInstanc;
    private Short userSign2;
    private Date updateDate;

    public Oprj() {
    }
    


    public Oprj(String prjCode, String prjName) {
		super();
		this.prjCode = prjCode;
		this.prjName = prjName;
	}



	public Oprj(String prjCode) {
        this.prjCode = prjCode;
    }

    public String getPrjCode() {
        return prjCode;
    }

    public void setPrjCode(String prjCode) {
        this.prjCode = prjCode;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
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

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
   

    @Override
    public String toString() {
        return this.prjName;
    }
    
}
