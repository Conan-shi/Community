package models;

import java.util.ArrayList;

//班车类
public class Bus {
    private ArrayList<String> passengers;

    private String id;

    private String routId;

    private String routName;

    private String direction;

    private String routType;

    private String serveDate;

    private String servePeriod;

    private String startTime;

    private String ddl;

    private String reservedNumber;

    private String remark;

    public Bus() {
    }

    public Bus(String id, String routId, String routName, String direction, String routType, String serveDate, String servePeriod, String startTime, String ddl, String reservedNumber, String remark) {
        passengers= new ArrayList<String>();
        this.id = id;
        this.routId = routId;
        this.routName = routName;
        this.direction = direction;
        this.routType = routType;
        this.serveDate = serveDate;
        this.servePeriod = servePeriod;
        this.startTime = startTime;
        this.ddl = ddl;
        this.reservedNumber = reservedNumber;
        this.remark = remark;
    }

    public ArrayList<String> getPassengers() {
        return passengers;
    }

    public void addPassengers(String oldManAccount){
        passengers.add(oldManAccount);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoutType() {
        return routType;
    }

    public void setRoutType(String routType) {
        this.routType = routType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoutId() {
        return routId;
    }

    public void setRoutId(String routId) {
        this.routId = routId;
    }

    public String getRoutName() {
        return routName;
    }

    public void setRoutName(String routName) {
        this.routName = routName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getServeDate() {
        return serveDate;
    }

    public void setServeDate(String serveDate) {
        this.serveDate = serveDate;
    }

    public String getServePeriod() {
        return servePeriod;
    }

    public void setServePeriod(String servePeriod) {
        this.servePeriod = servePeriod;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getReservedNumber() {
        return reservedNumber;
    }

    public void setReservedNumber(String reservedNumber) {
        this.reservedNumber = reservedNumber;
    }
}
