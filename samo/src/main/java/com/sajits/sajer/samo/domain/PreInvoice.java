package com.sajits.sajer.samo.domain;

public class PreInvoice {
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getInno() {
        return inno;
    }

    public void setInno(String inno) {
        this.inno = inno;
    }

    public String getTins() {
        return tins;
    }

    public void setTins(String tins) {
        this.tins = tins;
    }

    public Long getTadis() {
        return tadis;
    }

    public void setTadis(Long tadis) {
        this.tadis = tadis;
    }

    public Long getTvam() {
        return tvam;
    }

    public void setTvam(Long tvam) {
        this.tvam = tvam;
    }

    public String getSstid() {
        return sstid;
    }

    public void setSstid(String sstid) {
        this.sstid = sstid;
    }

    public String getSstt() {
        return sstt;
    }

    public void setSstt(String sstt) {
        this.sstt = sstt;
    }

    private String inno;
    private String tins;
    private Long tadis;
    private Long tvam;
    private String sstid;
    private String sstt;
}
