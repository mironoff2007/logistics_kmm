package com.mironov.localization

object Russian {
    fun getString(stringRes: StringRes): String {
        return when (stringRes) {
            StringRes.Logistics -> "Логистика"
            StringRes.Login -> "Вход в профиль"
            StringRes.Logout -> "Выход из профиля"
            StringRes.Settings -> "Настройки"
            StringRes.RegisterParcel -> "Регистрация посылки"
            StringRes.ParcelData -> "Данные посылки"
            StringRes.Backpack -> "Рюкзак"
            StringRes.Warehouse -> "Склад"
            StringRes.GlobalSearch -> "Поиск посылок"
            StringRes.Back -> "Назад"
            StringRes.RequestPermissions -> "Пожалуйста, предоставьте разрешения"
            //Login
            StringRes.UserName -> "Имя пользователя"
            StringRes.Password -> "Пароль"
            StringRes.SignIn -> "Войти"
            //Parcel data
            StringRes.Recipient -> "Получатель"
            StringRes.Sender -> "Отправитель"
            StringRes.ParcelId -> "Номер посылки"
            StringRes.FirstName -> "Имя"
            StringRes.SecondName -> "Фамилия"
            StringRes.Address -> "Адрес доставки"
            StringRes.SenderAddress -> "Адрес отправителя"
            StringRes.SenderName -> "Имя отправителя"
            StringRes.SenderSecondName -> "Фамилия отправителя"
            StringRes.ParcelRegTime -> "Время регистрации"
            StringRes.DestinationCity -> "Город назначения"
            StringRes.SenderCity -> "Город отправителя"
            StringRes.CurrentCity -> "Город нахождения"
            //Register parcel screen
            StringRes.EmptyDataHint -> "Заполните все поля"
            StringRes.AddParcel -> "Зарегистрировать посылку"
            StringRes.ParcelIsAdded -> "Посылка добавлена"
            StringRes.SelectCity -> "Выберите город"
            //Common
            StringRes.Search -> "Поиск"
            StringRes.Next -> "Далее"
            StringRes.Any -> "Любой"
            StringRes.None -> ""
            else -> English.getString(stringRes)
        }
    }
}