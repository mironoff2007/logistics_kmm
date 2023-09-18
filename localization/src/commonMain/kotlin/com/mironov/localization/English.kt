package com.mironov.localization

object English {
    fun getString(stringRes: StringRes): String {
        return when (stringRes) {
            StringRes.Logistics -> "Logistics"
            StringRes.Login -> "Login"
            StringRes.Logout -> "Logout"
            StringRes.Settings -> "Settings"
            StringRes.ParcelData -> "Parcel data"
            StringRes.RegisterParcel -> "Register Parcel"
            StringRes.Warehouse -> "Warehouse"
            StringRes.Back -> "Back"
            StringRes.RequestPermissions -> "Please, grant permissions"
            //Login
            StringRes.UserName -> "User name"
            StringRes.Password -> "Password"
            StringRes.SignIn -> "Sign in"
            //Parcel data
            StringRes.Recipient -> "Recipient"
            StringRes.Sender -> "Sender"
            StringRes.ParcelId -> "Parcel id"
            StringRes.FirstName -> "First name"
            StringRes.SecondName -> "Second name"
            StringRes.Address -> "Delivery address"
            StringRes.SenderAddress -> "Sender address"
            StringRes.SenderName -> "Sender name"
            StringRes.SenderSecondName -> "Sender second name"
            StringRes.ParcelRegTime -> "Registration time"
            StringRes.DestinationCity -> "Destination city"
            StringRes.SenderCity -> "Sender city"
            StringRes.CurrentCity -> "Location city"
            //Register parcel screen
            StringRes.EmptyDataHint -> "Fill all fields"
            StringRes.AddParcel -> "Register parcel"
            StringRes.ParcelIsAdded -> "Parcel is added"
            StringRes.SelectCity -> "Select city"
            //Common
            StringRes.Search -> "Search"
            StringRes.Next -> "Next"
            StringRes.Any -> "Any"
            StringRes.None -> ""
            else -> ""
        }
    }
}