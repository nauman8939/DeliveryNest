package com.example.deliverynest;

public class Order_POJO {
    String orderid,assignedto,bookoption,itemnametosend,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,
            preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price;
    int symbol;

    public Order_POJO(int symbol,String orderid,String assignedto, String bookoption, String itemnametosend, String notifypersonoption, String orderdate, String orderweight, String parcelvalue, String pickupaddress, String preferbagoption, String receiveraddress, String receiverlandmark, String receivername, String receiverphone, String senderlandmark, String sendername, String senderphone, String status, String price) {
        this.symbol=symbol;
        this.orderid=orderid;
        this.assignedto = assignedto;
        this.bookoption = bookoption;
        this.itemnametosend = itemnametosend;
        this.notifypersonoption = notifypersonoption;
        this.orderdate = orderdate;
        this.orderweight = orderweight;
        this.parcelvalue = parcelvalue;
        this.pickupaddress = pickupaddress;
        this.preferbagoption = preferbagoption;
        this.receiveraddress = receiveraddress;
        this.receiverlandmark = receiverlandmark;
        this.receivername = receivername;
        this.receiverphone = receiverphone;
        this.senderlandmark = senderlandmark;
        this.sendername = sendername;
        this.senderphone = senderphone;
        this.status = status;
        this.price = price;
    }
    public int getSymbol(){
        return symbol;
    }
    public String getOrderid(){
        return orderid;
    }
    public String getAssignedto() {
        return assignedto;
    }
    public String getBookoption() {
        return bookoption;
    }
    public String getItemnametosend() {
        return itemnametosend;
    }
    public String getNotifypersonoption() {
        return notifypersonoption;
    }
    public String getOrderdate() {
        return orderdate;
    }
    public String getOrderweight() {
        return orderweight;
    }
    public String getParcelvalue() {
        return parcelvalue;
    }
    public String getPickupaddress() {
        return pickupaddress;
    }
    public String getPreferbagoption() {
        return preferbagoption;
    }
    public String getReceiveraddress() {
        return receiveraddress;
    }
    public String getReceiverlandmark() {
        return receiverlandmark;
    }
    public String getReceivername() {
        return receivername;
    }
    public String getReceiverphone() {
        return receiverphone;
    }
    public String getSenderlandmark() {
        return senderlandmark;
    }
    public String getSendername() {
        return sendername;
    }
    public String getSenderphone() {
        return senderphone;
    }
    public String getStatus() {
        return status;
    }
    public String getPrice() {
        return price;
    }
}
