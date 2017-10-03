package app.banking.com.demobankingapp;

import android.icu.text.DecimalFormat;

import java.sql.Date;
import java.util.List;

/**
 * Created by Sumit on 9/15/2017.
 */

public class UserModelResponse {


    /**
     * $id : 1
     * Cheque : [{"$id":"2","User":{"$ref":"1"},"Id":8,"UserId":9,"Number":null,"Amount":null,"Status":null,"DateCreated":null,"CreatedBy":null,"DateModified":null,"ModifiedBy":null,"IsDeleted":false,"ImageUrl":"https://bankingapp.blob.core.windows.net/cheques/636421786444288250.jpg"}]
     * Id : 9
     * FirstName : null
     * LastName : null
     * FacebookUserId : null
     * Email : null
     * Contact : 7529859001
     * Gender : null
     * Address : null
     * DateCreated : null
     * CreatedBy : null
     * DateModified : null
     * ModifiedBy : null
     * IsDeleted : false
     * IsApproved : true
     */

    private String $id;
    private int Id;
    private String FirstName;
    private String LastName;
    private String FacebookUserId;
    private String Email;
    private String Contact;
    private Boolean Gender;
    private String Address;
    private Date DateCreated;
    private String CreatedBy;
    private Date DateModified;
    private String ModifiedBy;
    private boolean IsDeleted;
    private boolean IsApproved;
    private List<ChequeBean> Cheque;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public Object getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public Object getFacebookUserId() {
        return FacebookUserId;
    }

    public void setFacebookUserId(String FacebookUserId) {
        this.FacebookUserId = FacebookUserId;
    }

    public Object getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public Object getGender() {
        return Gender;
    }

    public void setGender(Boolean Gender) {
        this.Gender = Gender;
    }

    public Object getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public Object getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date DateCreated) {
        this.DateCreated = DateCreated;
    }

    public Object getCreatedBy() {
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

    public String  getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String  ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public boolean isIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(boolean IsApproved) {
        this.IsApproved = IsApproved;
    }

    public List<ChequeBean> getCheque() {
        return Cheque;
    }

    public void setCheque(List<ChequeBean> Cheque) {
        this.Cheque = Cheque;
    }

    public static class ChequeBean {
        /**
         * $id : 2
         * User : {"$ref":"1"}
         * Id : 8
         * UserId : 9
         * Number : null
         * Amount : null
         * Status : null
         * DateCreated : null
         * CreatedBy : null
         * DateModified : null
         * ModifiedBy : null
         * IsDeleted : false
         * ImageUrl : https://bankingapp.blob.core.windows.net/cheques/636421786444288250.jpg
         */

        private String $id;
        private UserBean User;
        private int Id;
        private int UserId;
        private String Number;
        private Double Amount;
        private String Status;
        private Date DateCreated;
        private String CreatedBy;
        private Date DateModified;
        private String ModifiedBy;
        private boolean IsDeleted;
        private String ImageUrl;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public UserBean getUser() {
            return User;
        }

        public void setUser(UserBean User) {
            this.User = User;
        }

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

        public String  getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public Double  getAmount() {
            return Amount;
        }

        public void setAmount(Double Amount) {
            this.Amount = Amount;
        }

        public String  getStatus() {
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

        public String  getCreatedBy() {
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

        public void setModifiedBy(String  ModifiedBy) {
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

        public static class UserBean {
            /**
             * $ref : 1
             */

            private String $ref;

            public String get$ref() {
                return $ref;
            }

            public void set$ref(String $ref) {
                this.$ref = $ref;
            }
        }
    }
}
