package com.seabury.web.vo.dose;

import java.util.HashMap;


public class ReturnParam extends HashMap<String, Object> {
    public ReturnParam() {
    }

    public String getStatus() {
        return (String)this.get("status");
    }

    public void setStatus(String status) {
        this.put("status", status);
    }

    public void setMessage(String message) {
        this.put("message", message);
    }

    public void setFail(String message) {
        this.setStatus("FAIL");
        this.setMessage(message);
    }

    public void setSuccess(String message) {
        this.setStatus("OK");
        this.setMessage(message);
    }
}