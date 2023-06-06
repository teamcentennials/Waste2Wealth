package app.waste2wealth.com.firebase.firestore

data class Challenges(
    val date: String,
    val time: String,
    val location: String,
    val title: String,
    val orgName: String,
    val image: String,
    val points: Int,
    val type: String? = null,
    val icon: String? = null
)

val challengesList = listOf(
    Challenges(
        date = "18th June 2023",
        time = "8am to 9am",
        location = "Juhu beach , Mumbai",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%207Beach%20Warriors%20Description.png?alt=media&token=da213fc9-b01e-4e23-b55c-df158148afee&_gl=1*9a195i*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MDY2OC4wLjAuMA..",
        orgName = "Beach Warriors",
        title = "Beach Cleanup",
        icon = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2062Beach%20warriors%20icon%20.png?alt=media&token=76bdf9bb-d8c3-468f-8920-c247c2b91609&_gl=1*10rpo87*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MDU1MC4wLjAuMA.."

    ),

    Challenges(
        date = "28th June 2023",
        time = "7am to 10am",
        location = "Sagar Vihar, Sector 8, Vashi",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2051Mangrove%20Foundation%20Description.png?alt=media&token=77da4d19-dd3a-4fd1-b0e9-e90980943e2c&_gl=1*q0q04s*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTAzMy4wLjAuMA..",
        title = "Mangrove cleanup",
        orgName = "Mangrove Foundation",
        icon = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2061Mangrove%20Foundation%20icon%20.png?alt=media&token=3c94ecab-ee61-4c63-8b51-a15dc99b44af&_gl=1*18647na*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTA3OS4wLjAuMA.."


    ),

    Challenges(
        date = "29th June 2023",
        time = "8am to 10am",
        location = "462, Senapati Bapat Marg, Lower Parel",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2065Swechha%20%20Description%20Green%20Creeps.png?alt=media&token=069a2c69-2286-4d44-93f8-a9ed04a3f611&_gl=1*18nzhqd*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTE0Ny4wLjAuMA..",
        title = "GREEN CREEPS",
        orgName = "Swechha",
        icon =
        "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2063Swechha%20%20Icon.png?alt=media&token=1ab6fbb6-3e8b-4d85-8129-5aecbacf6300&_gl=1*hj6xgy*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTE2OS4wLjAuMA.."


    ),

    Challenges(
        date = "30th June 2023",
        time = "8am to 10am",
        location = "Sahakar Nagar,  Andheri West, ",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2059Swechha%20%20Description%20Eco%20walks.png?alt=media&token=f860a08b-c093-43cc-8a9b-9e2932211247&_gl=1*x1y9aw*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTI4Ny4wLjAuMA..",
        title = "ECO WALKS",
        orgName = "Swechha",
        icon = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2063Swechha%20%20Icon.png?alt=media&token=1ab6fbb6-3e8b-4d85-8129-5aecbacf6300&_gl=1*hj6xgy*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTE2OS4wLjAuMA.."


    ),

    Challenges(
        date = "19th June 2023",
        time = "6am to 11am",
        location = "Chanakya View Point, Navi Mumbai ",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2072future%20india%20Description%20Mangroves%20Cleanup%20.png?alt=media&token=803baa20-6412-483d-a22e-ea0b4a42d70a&_gl=1*1okhl0d*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTQzMy4wLjAuMA..",
        title = "Mangroves Cleanup",
        orgName = "for future india",
        icon =
        "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2075for%20future%20india%20icon.png?alt=media&token=15536292-3ca9-4773-ae62-4e16510772e9&_gl=1*1gr41fz*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTQ2MS4wLjAuMA.."


    ),

    Challenges(
        date = "25th June 2023",
        time = "8am to 10am",
        location = "CENTRAL PARK, Kharghar ",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2073for%20future%20India%20Description%20Tree%20Plantation.png?alt=media&token=563c2e22-b92d-476e-ac69-a68d7cc96a78&_gl=1*1abuxx3*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTM4Ny4wLjAuMA..",
        title = "Tree Plantation",
        orgName = "for future india",
        icon =
        "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2075for%20future%20india%20icon.png?alt=media&token=15536292-3ca9-4773-ae62-4e16510772e9&_gl=1*1gr41fz*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTQ2MS4wLjAuMA.."


    ),

    Challenges(
        date = "25th June 2023",
        time = "8am to 10am",
        location = "CENTRAL PARK, Kharghar ",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2073for%20future%20India%20Description%20Tree%20Plantation.png?alt=media&token=563c2e22-b92d-476e-ac69-a68d7cc96a78&_gl=1*1abuxx3*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTM4Ny4wLjAuMA..",
        title = "Tree Plantation",
        orgName = "for future india",
        icon =
        "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2075for%20future%20india%20icon.png?alt=media&token=15536292-3ca9-4773-ae62-4e16510772e9&_gl=1*1gr41fz*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTQ2MS4wLjAuMA.."


    ),

    Challenges(
        date = "10th June 2023",
        time = "10pm to 12am",
        location = "Panvel Cluster , Sector 19A ",
        points = 100,
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2081robin%20hood%20army%20Food%20.png?alt=media&token=54d837db-79db-4e41-a429-14490cb3381a&_gl=1*pbd4fe*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTY5NS4wLjAuMA..",
        title = "Food Distribution",
        orgName = "robin hood army",
        icon =
        "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2080Icon%20.png?alt=media&token=f062e461-45a9-4607-b5d8-68a214dd2050&_gl=1*1y3g80i*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MTY3MS4wLjAuMA.."


    )
)

data class Organizations(
    val title: String,
    val image: String,
    val location: String,
    val about: String
)

val organisationList = listOf(
    Organizations(
        title = "Beach Warriors",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2043Beach%20Warriors%20Main%20.png?alt=media&token=60777c5e-bdfc-4cfd-83c8-45311b070941&_gl=1*1q2ixqb*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MjE5OS4wLjAuMA..",
        location = "Mumbai",
        about = "An Initiative taken by Khushiyaan foundation to clean the beach every weekends and public holidays.",
    ),
    Organizations(
        title = "Mangrove Foundation",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2045Mangrove%20Foundation%20main.png?alt=media&token=66a7cda8-8c25-40f8-aba3-518089230c31&_gl=1*1551bkk*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MjIxNS4wLjAuMA..",
        location = "Mumbai",
        about = "Working towards conservation of coastal & marine biodiversity & improving the livelihoods of the coastal community in Maharashtra",
    ),
    Organizations(
        title = "Swechha ",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2046Swechha%20%20Main.png?alt=media&token=5564f76d-b705-4829-89f3-1c6348150485&_gl=1*khoul4*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MjIyNy4wLjAuMA..",
        location = "Mumbai",
        about = "Swechha is an environmental and social organization that promotes sustainable lifestyles, raises awareness of environmental issues, and empowers communities.",
    ),
    Organizations(
        title = "for future india",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2047for%20future%20india%20main.png?alt=media&token=3ce05ab1-b59d-41b2-93a7-0789004dfcf4&_gl=1*1vpvwuu*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MjIzNy4wLjAuMA..",
        location = "Mumbai",
        about = "Conducts “Beach Cleanups” every week since January 2020, raising awareness about the plastic pollution harming aquatic life and cleaning up tons of plastic waste from the beach.",
    ),
    Organizations(
        title = "robin hood army",
        image = "https://firebasestorage.googleapis.com/v0/b/waste2wealth-225f8.appspot.com/o/Challenges%2Fimage%2077Robin.png?alt=media&token=d01d985c-0f41-477c-a4df-bcf49bc1ad0e&_gl=1*rwjlom*_ga*NTE4MDg5MzA2LjE2ODUwNDMxMzQ.*_ga_CW55HF8NVT*MTY4NTg0OTMzNS4yLjEuMTY4NTg1MjI1NC4wLjAuMA..",
        location = "Mumbai",
        about = "The Robin Hood Army is a zero-funds volunteer organization that works to get surplus food from restaurants and communities to serve the less fortunate.",
    )
)
