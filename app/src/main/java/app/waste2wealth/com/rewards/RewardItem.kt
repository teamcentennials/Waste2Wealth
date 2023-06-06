package app.waste2wealth.com.rewards

data class RewardItem(
    val name: String,
    val expiry: String,
    val points: String,
) {
    constructor() : this("", "", "")
}

val allRewards = listOf(
    RewardItem(
        "Free Coffee",
        "Expires in 2 days",
        "100"
    ),
    RewardItem(
        "Donation to NGO",
        "Expires in 2 days",
        "100"
    ),
    RewardItem(
        "Caramel Capachino",
        "Expires in 2 days",
        "100"
    ),
    RewardItem(
        "Free Coffee",
        "Expires in 2 days",
        "100"
    ),
)
