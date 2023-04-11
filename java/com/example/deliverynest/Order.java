package com.example.deliverynest;

public class Order {
    public String SenderName;
    public String SenderPhone;
    public String PickUpAddress;
    public String ReceiverName;
    public String ReceiverPhone;
    public String ReceiverAddress;
    public String ItemNameToSend;
    public String ParcelValue;
    public String SenderLandmark;
    public String ReceiverLandmark;
    public String BookOption;

    public String PreferBagOption;
    public String NotifyPersonOption;

    public String OrderWeight;
    public int price;
    public String OrderDate;
    public String LoggedUsername;
    public String Status;
    public String Assigned_to;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(String status,String assigned_to,String senderName, String senderPhone, String pickUpAddress, String receiverName, String receiverPhone, String receiverAddress, String itemNameToSend, String parcelValue, String senderLandmark, String receiverLandmark, String bookOption, String preferBagOption, String notifyPersonOption,  String orderWeight, int price, String orderDate, String loggedUsername) {
        this.SenderName = senderName;
        this.Status = status;
        this.Assigned_to = assigned_to;
        this.SenderPhone = senderPhone;
        this.PickUpAddress = pickUpAddress;
        this.ReceiverName = receiverName;
        this.ReceiverPhone = receiverPhone;
        this.ReceiverAddress = receiverAddress;
        this.ItemNameToSend = itemNameToSend;
        this.ParcelValue = parcelValue;
        this.SenderLandmark = senderLandmark;
        this.ReceiverLandmark = receiverLandmark;
        this.BookOption = bookOption;

        this.PreferBagOption = preferBagOption;
        this.NotifyPersonOption = notifyPersonOption;

        this.OrderWeight = orderWeight;
        this.price = price;
        this.OrderDate = orderDate;
        this.LoggedUsername = loggedUsername;
    }
}
