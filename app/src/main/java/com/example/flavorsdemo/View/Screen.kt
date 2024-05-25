package com.example.flavorsdemo.View

sealed class Screen(val route : String) {
    data object GetStarted : Screen("get_started")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object AddCar : Screen("add_car")
    data object  AddImages : Screen("add_images")
    data object FilterPage : Screen("filter_page")
    data object AddOffice : Screen("add_office")
    data object OfficeMap : Screen("office_map")
    data object MoreScreen : Screen("more")
    data object ProfileScreen : Screen("profile")
    data object WhereTo : Screen("where_to")
    data object PickDateAndTime : Screen("pick_date_and_time")
    data object CarInfo : Screen("car_info")
    data object ExtraOptions : Screen("extra_options")
}