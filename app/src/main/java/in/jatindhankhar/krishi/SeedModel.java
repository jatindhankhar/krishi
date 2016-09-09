package in.jatindhankhar.krishi;


import com.google.gson.annotations.SerializedName;

/**
 * Created by jatin on 6/9/16.
 */
public class SeedModel {


    public class AddressDetails {
        @SerializedName("address")
        private String address;
        @SerializedName("block")
        private String block;
        @SerializedName("district")
        private String district;
        @SerializedName("state")
        private String state;

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBlock() {
            return block;
        }


        public void setBlock(String block) {
            this.block = block;
        }


        public String getDistrict() {
            return district;
        }


        public void setDistrict(String district) {
            this.district = district;
        }


        public String getState() {
            return state;
        }


        public void setState(String state) {
            this.state = state;
        }
    }

    public class CompanyDetails {

        @SerializedName("name")

            private String name;
            @SerializedName("code")

            private String code;
            @SerializedName("license_no")

            private String licenseNo;

            public String getName() {
                return name;
            }


            public void setName(String name) {
                this.name = name;
            }


            public String getCode() {
                return code;
            }


            public void setCode(String code) {
                this.code = code;
            }


            public String getLicenseNo() {
                return licenseNo;
            }


            public void setLicenseNo(String licenseNo) {
                this.licenseNo = licenseNo;
            }

        }


    public class ContactDetails {

        @SerializedName("name")

        private String name;
        @SerializedName("number")

        private String number;

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public String getNumber() {
            return number;
        }


        public void setNumber(String number) {
            this.number = number;
        }

    }

    public class CropDetails {

        @SerializedName("crop_names")

        private String cropNames;

        public String getCropNames() {
            return cropNames;
        }

        public void setCropNames(String cropNames) {
            this.cropNames = cropNames;
        }
    }

    public class LicenseDetails {
        @SerializedName("license_authority")
        private String licenseAuthority;
        @SerializedName("membership")
        private String membership;
        public String getLicenseAuthority() { return licenseAuthority;}
        public  void setLicenseAuthority(String licenseAuthority) { this.licenseAuthority = licenseAuthority ;}
        public String getMembership() { return  membership;}
        public void setMembership(String membership){this.membership = membership;}
    }

    @SerializedName("crop_details")
    CropDetails cropDetails;


    @SerializedName("contact_details")
    ContactDetails contactDetails;

    @SerializedName("company_details")
    CompanyDetails companyDetails;

    @SerializedName("address_details")
    AddressDetails addressDetails;

    @SerializedName("license_details")
    LicenseDetails licenseDetails;

}


