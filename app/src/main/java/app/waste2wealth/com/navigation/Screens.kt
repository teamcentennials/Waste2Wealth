package app.waste2wealth.com.navigation

sealed class Screens(val route: String) {

    object Onboarding : Screens("onboarding")
    object CompleteProfile : Screens("completeProfile")
    object Community : Screens("community")
    object SettingUp : Screens("settingUp")
    object ReportWaste : Screens("reportWaste")
    object CollectWasteLists : Screens("collectWasteLists")
    object CollectWasteInfo : Screens("collectWasteInfo")
    object CollectedWasteSuccess : Screens("collectWasteSuccess")
    object AllActivities : Screens("allActivities")
    object MyRecordings : Screens("myRecordings")
    object StopRecording : Screens("stopRecording")
    object Rewards : Screens("rewards")
    object LoginScreen : Screens("login")
    object Dashboard : Screens("dashboard")
    object Profile : Screens("profile")
    object TasksLists : Screens("tasksLists")
    object TaskDetail : Screens("taskDetails")
    object SuccessTask : Screens("successTask")
    object FailureTask : Screens("failureTask")
    object QrCodeScanner : Screens("qrCodeScanner")
    object Splash : Screens("splash")

}

sealed class TaskDetailsConstants(val value: String) {
    object taskCode : TaskDetailsConstants("taskCode")
    object taskPrice : TaskDetailsConstants("taskPrice")
    object noOfAttempts : TaskDetailsConstants("noOfAttempts")
    object address : TaskDetailsConstants("address")
    object phoneNumber : TaskDetailsConstants("phoneNumber")
}

