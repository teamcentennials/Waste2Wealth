package app.waste2wealth.com.tasksDetail

import app.waste2wealth.com.R

data class TaskItem(
    val locationNumber: String,
    val address: String,
    val noOfKMs: Int,
    val image: Int
)

data class ExpandedTaskItem(
    val orderId: String,
    val invoiceId: String,
    val amount: Float,
    val noOfAttempts: Int
)

val list = MutableList(size = 19, init = {
    TaskItem(
        locationNumber = "Mukesh Patil",
        address = "Shree Sai MediCare | Shop Number 2/14 Opposite Colgate Maidan, AMrut Nagar," +
                " Kherwadi, Bandra East, Mumbai",
        noOfKMs = 5,
        image = R.drawable.appicon
    )
})



val expandedList = MutableList(size = 50, init = {
    ExpandedTaskItem(
        orderId = "KXVFZ",
        invoiceId = "ABCDEFGH",
        amount = 500.0f,
        noOfAttempts = 2
    )
})

