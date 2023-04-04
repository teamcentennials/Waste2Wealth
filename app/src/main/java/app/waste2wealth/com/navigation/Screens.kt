package app.waste2wealth.com.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login")
    object Dashboard : Screens("dashboard")
    object Profile : Screens("profile")
    object PickupTasks : Screens("pickupTasks")
    object TasksLists : Screens("tasksLists")
    object StartTrip : Screens("startTrip")
    object TaskDetail : Screens("taskDetails")
    object SuccessTask : Screens("successTask")
    object FailureTask : Screens("failureTask")
    object QrCodeScanner : Screens("qrCodeScanner")
    object EndTrip : Screens("endTrip")
    object Splash : Screens("splash")

}

sealed class TaskDetailsConstants(val value: String) {
    object taskCode : TaskDetailsConstants("taskCode")
    object taskPrice : TaskDetailsConstants("taskPrice")
    object noOfAttempts : TaskDetailsConstants("noOfAttempts")
    object address : TaskDetailsConstants("address")
    object phoneNumber : TaskDetailsConstants("phoneNumber")
}

