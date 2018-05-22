package com.gemalto.idgo800.testtool.common;

public class GetCertResponse {
    private int Status;
    private String StatusDescription;
    private String Value;

    public int getStatus() {
        return this.Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getStatusDescription() {
        return this.StatusDescription;
    }

    public void setStatusDescription(String StatusDescription) {
        this.StatusDescription = StatusDescription;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }
}
