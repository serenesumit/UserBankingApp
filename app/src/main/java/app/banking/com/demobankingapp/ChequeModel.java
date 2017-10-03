package app.banking.com.demobankingapp;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 * Created by Sumit on 9/22/2017.
 */

public class ChequeModel {

    private int Id;
    private int UserId;
    private String Number;
    private String Amount;
    private String Status;
    private Date DateCreated;
    private String CreatedBy;
    private Date DateModified;
    private String ModifiedBy;
    private boolean IsDeleted;
    private String ImageUrl;
    private String ImageBase64String;
    private Object User;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public Object getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date DateCreated) {
        this.DateCreated = DateCreated;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public Date getDateModified() {
        return DateModified;
    }

    public void setDateModified(Date DateModified) {
        this.DateModified = DateModified;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getImageBase64String() {
        return Number;
    }

    public void setImageBase64String(String ImageBase64String) {
        this.ImageBase64String = ImageBase64String;
    }

    public Object getUser() {
        return User;
    }

    public void setUser(Object User) {
        this.User = User;
    }
}
